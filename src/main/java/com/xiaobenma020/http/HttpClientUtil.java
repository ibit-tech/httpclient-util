package com.xiaobenma020.http;

import com.xiaobenma020.http.exception.MethodNotSupportException;
import com.xiaobenma020.http.request.BaseEntityRequest;
import com.xiaobenma020.http.request.Request;
import com.xiaobenma020.http.response.Response;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class HttpClientUtil {

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_CONNECT_TIMEOUT = 10000;
    private static final int MAX_SOCKET_TIMEOUT = 10000;
    private static final int MAX_CONNECT_REQUEST_TIMEOUT = 10000;
    private static final int MAX_TOTAL = 100;

    static {
        // connecting pools
        connMgr = new PoolingHttpClientConnectionManager();

        //pool size
        connMgr.setMaxTotal(MAX_TOTAL);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();

        // timeout unit : milliseconds
        configBuilder.setConnectTimeout(MAX_CONNECT_TIMEOUT);
        configBuilder.setSocketTimeout(MAX_SOCKET_TIMEOUT);
        configBuilder.setConnectionRequestTimeout(MAX_CONNECT_REQUEST_TIMEOUT);

        // check availability
        //configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * Do http request
     * @param request request object
     * @return response object
     */
    public static Response doRequest(Request request) {
        try {
            HttpRequestBase baseRequest = request.getHttpRequest();

            Header[] headers = request.getAllHeaders();
            if (null != headers && headers.length > 0) {
                baseRequest.setHeaders(headers);
            }
            baseRequest.setConfig(requestConfig);
            if (request instanceof BaseEntityRequest) { //to post entity
                ((HttpEntityEnclosingRequestBase) baseRequest).setEntity(((BaseEntityRequest) request).getEntity());
            }
            return toXHttpResponse(baseRequest, request.getResponseDefaultCharset(), request.isUseSSL());
        } catch (IOException e) {
            e.printStackTrace();
            return getErrorXResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (MethodNotSupportException mnse) {
            return getErrorXResponse(HttpServletResponse.SC_METHOD_NOT_ALLOWED, mnse.getMessage());
        }
    }

    private static Response getErrorXResponse(int code, String errorMsg) {
        return new Response(code, errorMsg, null, null, null);
    }

    private static Response toXHttpResponse(HttpUriRequest request, String defaultCharset
            , boolean useSSL) throws IOException {

        try (CloseableHttpClient httpClient = useSSL
                ? getHttpsClient()
                : HttpClients.createDefault()) {
            HttpResponse response = httpClient.execute(request);
            Response result = new Response(response.getStatusLine().getStatusCode(), response.getAllHeaders());
            HttpEntity entity = response.getEntity();
            if (entity != null) {

                //content encoding
                String charset = (null == entity.getContentEncoding()) ? defaultCharset : entity.getContentEncoding().getValue();
                result.setContentEncoding(charset);

                //content type
                String contentType = (null == entity.getContentType()) ? "" : entity.getContentType().getValue();
                result.setContentType(contentType);

                try {
                    result.setResponseText(EntityUtils.toString(entity, defaultCharset));
                } finally {
                    try {
                        EntityUtils.consume(response.getEntity());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
    }

    private static CloseableHttpClient getHttpsClient() {
        return HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManagerShared(true)
                .build();
    }

    //create SSLConnectionSocketFactory to trust all
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        try {
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true)
                    .build();
            return new SSLConnectionSocketFactory(sslcontext,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
}
