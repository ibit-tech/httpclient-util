package tech.ibit.httpclient.utils.request;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.message.BasicHeader;
import tech.ibit.httpclient.utils.exception.MethodNotSupportException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求
 *
 * @author 小ben马
 */
public class Request {

    /**
     * 支持方法
     */
    private Set<RequestMethod> supportedMethods = new HashSet<RequestMethod>() {{
        add(RequestMethod.GET);
        add(RequestMethod.HEAD);
        add(RequestMethod.OPTIONS);
    }};

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方法
     */
    private RequestMethod method;

    /**
     * url参数
     */
    private Map<String, Object> urlParams;

    /**
     * 头部信息
     */
    private Map<String, Header> headerMap;

    /**
     * 请求编码
     */
    private String requestCharset = "UTF-8";

    /**
     * 返回默认编码
     */
    private String responseDefaultCharset = "UTF-8";

    /**
     * 是否安全访问
     */
    private boolean secured;

    /**
     * 构造函数
     *
     * @param url    请求url
     * @param method 方法名称
     * @throws MethodNotSupportException 方法不支持
     */
    public Request(String url, RequestMethod method) throws MethodNotSupportException {
        this.url = url;
        checkMethod(method);
        this.method = method;
        urlParams = new LinkedHashMap<>();
        headerMap = new LinkedHashMap<>();
    }

    /**
     * 构造函数
     *
     * @param url 请求url
     * @throws MethodNotSupportException 方法不支持
     */
    public Request(String url) throws MethodNotSupportException {
        this(url, RequestMethod.GET);
    }

    /**
     * 获取支持的方法
     *
     * @return 支持方法
     */
    public Set<RequestMethod> getSupportedMethods() {
        return supportedMethods;
    }

    /**
     * 检查方法是否支持
     *
     * @param method 方法
     * @throws MethodNotSupportException 方法不支持异常
     */
    private void checkMethod(RequestMethod method) throws MethodNotSupportException {
        if (null == method) {
            throw new MethodNotSupportException(null);
        }
        if (!getSupportedMethods().contains(method)) {
            throw new MethodNotSupportException(method.name());
        }
    }

    /**
     * 获取url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取url参数
     *
     * @return url参数
     */
    public Map<String, Object> getUrlParams() {
        return urlParams;
    }

    /**
     * 增加url参数
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void addUrlParam(String name, Object value) {
        urlParams.put(name, value);
    }

    /**
     * 批量增加url参数
     *
     * @param urlParams url参数map
     */
    public void addUrlParams(Map<String, Object> urlParams) {
        if (null != urlParams && !urlParams.isEmpty()) {
            this.urlParams.putAll(urlParams);
        }
    }

    /**
     * 移除url参数map
     *
     * @param name 参数名称
     */
    public void removeUrlParam(String name) {
        urlParams.remove(name);
    }

    /**
     * 批量增加请求头
     *
     * @param headers 请求头
     */
    public void addHeaders(Map<String, String> headers) {
        if (null != headers && !headers.isEmpty()) {
            headers.forEach(this::addHeader);
        }
    }

    /**
     * 增加请求头
     *
     * @param name  头部名称
     * @param value 头部值
     */
    public void addHeader(String name, String value) {
        headerMap.put(name, new BasicHeader(name, value));
    }

    /**
     * 移除头部
     *
     * @param name 头部名称
     */
    public void removeHeader(String name) {
        headerMap.remove(name);
    }

    /**
     * 获取头部
     *
     * @param name 头部名称
     * @return 头部
     */
    public Header getHeader(String name) {
        return headerMap.get(name);
    }

    /**
     * 获取所有头部
     *
     * @return 所有头部
     */
    public Header[] getAllHeaders() {
        return headerMap.values().toArray(new Header[0]);
    }

    /**
     * 获取请求编码
     *
     * @return 请求编码
     */
    public String getRequestCharset() {
        return requestCharset;
    }

    /**
     * 设置请求编码
     *
     * @param requestCharset 请求编码
     */
    public void setRequestCharset(String requestCharset) {
        this.requestCharset = requestCharset;
    }

    /**
     * 获取返回默认编码
     *
     * @return 返回默认编码
     */
    public String getResponseDefaultCharset() {
        return responseDefaultCharset;
    }

    /**
     * 设置返回默认编码
     *
     * @param responseDefaultCharset 返回默认编码
     */
    public void setResponseDefaultCharset(String responseDefaultCharset) {
        this.responseDefaultCharset = responseDefaultCharset;
    }

    /**
     * 获取方法
     *
     * @return 方法
     */
    public RequestMethod getMethod() {
        return method;
    }

    /**
     * 设置方法
     *
     * @param method 方法
     * @throws MethodNotSupportException 方法不支持
     */
    public void setMethod(RequestMethod method) throws MethodNotSupportException {
        checkMethod(method);
        this.method = method;
    }

    /**
     * 是否使用https
     *
     * @return 是否使用https
     */
    public boolean isSecured() {
        return secured;
    }

    /**
     * 设置是否使用https
     *
     * @param secured 是否使用https
     */
    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    /**
     * 获取queryString
     *
     * @return queryString
     */
    private String getQueryString() {
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

    /**
     * 获取带queryString的url
     *
     * @return 带queryString的url
     */
    private String getUrlWithQueryString() {
        String apiUrl = url;
        String queryStr = getQueryString();
        if (!StringUtils.isBlank(queryStr)) {
            apiUrl = apiUrl + (apiUrl.contains("?") ? "&" : "?") + queryStr;
        }
        return apiUrl;
    }

    /**
     * 获取HttpClient请求
     *
     * @return HttpClient请求
     * @throws MethodNotSupportException 方法不支持
     */
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
            default:
                throw new MethodNotSupportException(method.name());
        }

    }

}
