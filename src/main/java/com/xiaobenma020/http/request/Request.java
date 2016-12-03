package com.xiaobenma020.http.request;

import com.xiaobenma020.http.exception.MethodNotSupportException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Request with body, support request method "GET", "HEAD", "OPTIONS"
 * mailto:xiaobenma020@gmail.com
 */
public class Request {
    private String url;
    private RequestMethod method;
    private Map<String, Object> urlParams; //param in query string
    private Map<String, Header> headerMap;
    private String requestCharset = "UTF-8";
    private String responseDefaultCharset = "UTF-8";
    private boolean useSSL;


    public Request(String url, RequestMethod method) throws MethodNotSupportException {
        this.url = url;
        checkMethod(method);
        this.method = method;
        urlParams =  new LinkedHashMap<>();
        headerMap = new LinkedHashMap<>();
    }

    public Request(String url) throws MethodNotSupportException {
        this(url, RequestMethod.GET);
    }

    //Check request method, "GET", "HEAD", "OPTIONS" is supported
    protected void checkMethod(RequestMethod method) throws MethodNotSupportException {
        if (null == method) {
            throw new MethodNotSupportException(null);
        }
        if (!RequestMethod.GET.equals(method) && !RequestMethod.HEAD.equals(method) && !RequestMethod.OPTIONS.equals(method)) {
            throw new MethodNotSupportException(method.name());
        }
    }

    public String getUrl() {
        return url;
    }


    public Map<String, Object> getUrlParams() {
        return urlParams;
    }

    public void addUrlParam(String name, Object value) {
        urlParams.put(name, value);
    }

    public void addUrlParams(Map<String, Object> urlParams) {
        if (null != urlParams && !urlParams.isEmpty()) {
            this.urlParams.putAll(urlParams);
        }
    }

    public void removeUrlParam(String name) {
        urlParams.remove(name);
    }

    public void addHeaders(Map<String, String> headers) {
        if (null != headers && !headers.isEmpty()) {
            headers.forEach(this::addHeader);
        }
    }

    public void addHeader(String name, String value) {
        headerMap.put(name, new BasicHeader(name, value));
    }

    public void removeHeader(String name) {
        headerMap.remove(name);
    }

    public Header getHeader(String name) {
        return headerMap.get(name);
    }

    public Header[] getAllHeaders() {
        return headerMap.values().toArray(new Header[headerMap.size()]);
    }

    public String getRequestCharset() {
        return requestCharset;
    }

    public void setRequestCharset(String requestCharset) {
        this.requestCharset = requestCharset;
    }

    public String getResponseDefaultCharset() {
        return responseDefaultCharset;
    }

    public void setResponseDefaultCharset(String responseDefaultCharset) {
        this.responseDefaultCharset = responseDefaultCharset;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) throws MethodNotSupportException {
        checkMethod(method);
        this.method = method;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public String getQueryString() {
        if (urlParams.isEmpty()) {
            return "";
        }
        StringBuilder queryStr = new StringBuilder();
        urlParams.forEach((key, value) -> {
            try {
                queryStr.append("&").append(key)
                        .append("=")
                        .append(null == value ? "" : URLEncoder.encode(value.toString(), requestCharset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        return queryStr.toString().substring(1);
    }

    public String getUrlWithQueryString() {
        String apiUrl = url;
        String queryStr = getQueryString();
        if (!StringUtils.isBlank(queryStr)) {
            apiUrl = apiUrl + (apiUrl.contains("?") ? "&" : "?") + queryStr;
        }
        return apiUrl;
    }


    public HttpRequestBase getHttpRequest() throws MethodNotSupportException {
        String apiUrl = getUrlWithQueryString();
        switch (method) {
            case GET:
                return new HttpGet(apiUrl);
            case POST:
                return new HttpPost(apiUrl);
            case HEAD:
                return new HttpHead(apiUrl);
            case OPTIONS:
                return new HttpOptions(apiUrl);
            case PUT:
                return new HttpPut(apiUrl);
            case PATCH:
                return new HttpPatch(apiUrl);
        }
        throw new MethodNotSupportException(method.name());
    }

}
