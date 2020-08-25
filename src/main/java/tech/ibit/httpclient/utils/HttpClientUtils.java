package tech.ibit.httpclient.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import tech.ibit.httpclient.utils.exception.MethodNotSupportException;
import tech.ibit.httpclient.utils.request.BaseEntityRequest;
import tech.ibit.httpclient.utils.request.Request;
import tech.ibit.httpclient.utils.response.Response;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpClient工具类
 *
 * @author 小ben马
 */
public class HttpClientUtils {

    /**
     * 过期时间
     */
    private static final String TIMEOUT = "timeout";

    /**
     * http
     */
    private static final String PROTOCOL_HTTP = "http";

    /**
     * https
     */
    private static final String PROTOCOL_HTTPS = "https";

    /**
     * charset正则表达式
     */
    public static final String REGEX_CHARSET = "charset=([a-zA-Z0-9-]+);?";

    /**
     * 它是线程安全的，所有的线程都可以使用它一起发送http请求
     */
    private static CloseableHttpClient httpClient;

    static {
        init();
    }

    /**
     * 私有构造方法
     */
    private HttpClientUtils() {
    }

    /**
     * 初始化
     */
    private static void init() {
        init(new HttpClientProperties(), null);
    }

    /**
     * 初始化
     *
     * @param properties  属性
     * @param dnsResolver dns处理
     */
    public static void init(HttpClientProperties properties, DnsResolver dnsResolver) {

        // 重新初始化，先关闭已有的连接
        closeClient();

        // 支持http和https
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create().register(
                        PROTOCOL_HTTP, PlainConnectionSocketFactory.getSocketFactory())
                        .register(PROTOCOL_HTTPS, Objects.requireNonNull(createSSLConnSocketFactory())).build();

        // 初始化连接管理器
        PoolingHttpClientConnectionManager poolConnManager
                = new PoolingHttpClientConnectionManager(socketFactoryRegistry, dnsResolver);

        // 同时最多连接数
        poolConnManager.setMaxTotal(properties.getPoolMaxTotal());
        // 设置最大路由
        poolConnManager.setDefaultMaxPerRoute(properties.getPoolDefaultMaxPerRoute());

        // 请求配置
        RequestConfig config = RequestConfig
                .custom()
                .setConnectTimeout(properties.getConnectTimeout())
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout())
                .setSocketTimeout(properties.getSocketTimeout())
                .build();

        // socket 配置
        SocketConfig socketConfig = SocketConfig
                .custom()
                .setTcpNoDelay(properties.isSocketTcpNoDelay())
                .setSoReuseAddress(properties.isSocketSoReuseAddress())
                .setSoTimeout(properties.getSocketSoTimeout())
                .setSoLinger(properties.getSocketSoLinger())
                .setSoKeepAlive(properties.isSocketSoKeepAlive())
                .build();

        // keep-alive
        ConnectionKeepAliveStrategy keepAliveStrategy = properties.isUseKeepAliveStrategy()
                ? getConnectionKeepAliveStrategy(properties.getConnectionKeepAliveTimeout())
                : null;

        // 初始化httpClient
        httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(poolConnManager)
                .setKeepAliveStrategy(keepAliveStrategy)
                .setDefaultRequestConfig(config)
                .setDefaultSocketConfig(socketConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
    }

    /**
     * 关闭client
     */
    private static void closeClient() {
        if (null != httpClient) {
            try {
                httpClient.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * 配置keep-alive策略
     *
     * @return keep-alive策略
     */
    private static ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy(int keepAliveTimeout) {
        return (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator
                    (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase(TIMEOUT)) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return keepAliveTimeout * 1000;
        };
    }


    /**
     * 执行请求
     *
     * @param request      request object
     * @param convert2Text 是否将结果转换为文本，是：content为文本，否则为byte[]
     * @return response
     */
    public static Response doRequest(Request request, boolean convert2Text) {
        try {
            HttpRequestBase baseRequest = request.getHttpRequest();

            Header[] headers = request.getAllHeaders();
            if (null != headers && headers.length > 0) {
                baseRequest.setHeaders(headers);
            }

            // 含有body的请求
            if (request instanceof BaseEntityRequest) {
                ((HttpEntityEnclosingRequestBase) baseRequest).setEntity(((BaseEntityRequest) request).getEntity());
            }

            return getResponse(baseRequest, request.getResponseDefaultCharset(), convert2Text);

        } catch (IOException e) {
            e.printStackTrace();
            return getError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (MethodNotSupportException mnse) {
            return getError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, mnse.getMessage());
        }
    }

    /**
     * 错误信息Response
     *
     * @param code     返回码
     * @param errorMsg 错误信息
     * @return 错误信息Response
     */
    private static Response<String> getError(int code, String errorMsg) {
        return new Response<>(code, errorMsg, null, null, null);
    }

    /**
     * 获取Response
     *
     * @param request        请求
     * @param defaultCharset 默认编码
     * @param convert2Text   结果是否返回文本
     * @return Response
     * @throws IOException IO异常
     */
    private static Response getResponse(HttpUriRequest request
            , String defaultCharset, boolean convert2Text) throws IOException {

        CloseableHttpClient httpClient = getHttpClient();

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int code = response.getStatusLine().getStatusCode();
            Header[] headers = response.getAllHeaders();

            HttpEntity entity = response.getEntity();
            if (entity != null) {

                //content type
                String contentType = (null == entity.getContentType()) ? "" : entity.getContentType().getValue();

                //content encoding
                String charset = (null == entity.getContentEncoding()) ? null : entity.getContentEncoding().getValue();
                if (null == charset) {
                    charset = find(contentType, REGEX_CHARSET);
                    charset = null == charset ? defaultCharset : charset;
                }


                if (convert2Text) {
                    try {
                        String content = EntityUtils.toString(entity, defaultCharset);
                        return new Response<>(code, content, contentType, charset, headers);
                    } finally {
                        try {
                            EntityUtils.consume(response.getEntity());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    byte[] content = EntityUtils.toByteArray(entity);
                    return new Response<>(code, content, contentType, charset, headers);
                } finally {
                    try {
                        EntityUtils.consume(response.getEntity());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return new Response<String>(code, headers);
        }

    }

    /**
     * 查找字符串
     *
     * @param str   字符串
     * @param regex 正则表达式
     * @return 查找到的字符串
     */
    private static String find(String str, String regex) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 获取HttpClient
     *
     * @return HttpClient
     */
    private static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * https
     *
     * @return ssl socket Factory
     */
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
