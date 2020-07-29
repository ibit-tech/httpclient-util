package tech.ibit.httpclient.utils.response;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Response
 *
 * @param <T> 内容模板
 * @author 小ben马
 */
public class Response<T> {

    /**
     * 返回码
     */
    private int code;


    /**
     * Content-Type
     */
    private String contentType;

    /**
     * Content编码
     */
    private String contentEncoding;

    /**
     * 返回头部
     */
    private Map<String, Header> headersMap;

    /**
     * 返回内容
     */
    private T content;

    /**
     * 构造函数
     *
     * @param code 返回码
     */
    public Response(int code) {
        this(code, null, null, null, null);
    }

    /**
     * 构造函数
     *
     * @param code    返回码
     * @param headers 返回头部
     */
    public Response(int code, Header[] headers) {
        this(code, null, null, null, headers);
    }

    /**
     * 构造函数
     *
     * @param code            返回码
     * @param content         返回内容
     * @param contentType     Content-Type
     * @param contentEncoding 编码
     * @param headers         头部信息
     */
    public Response(int code, T content, String contentType
            , String contentEncoding, Header[] headers) {
        this.code = code;
        this.content = content;
        this.contentType = StringUtils.trimToEmpty(contentType);
        this.contentEncoding = StringUtils.trimToEmpty(contentEncoding);
        headersMap = new LinkedHashMap<>();
        addHeaders(headers);
    }

    /**
     * 批量增加头部
     *
     * @param headers 头部数组
     */
    private void addHeaders(Header[] headers) {
        if (null != headers) {
            for (Header header : headers) {
                headersMap.put(header.getName(), header);
            }
        }
    }

    /**
     * 获取返回码
     *
     * @return 返回码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置返回码
     *
     * @param code 返回码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取Content-Type
     *
     * @return Content-Type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 设置 Content-Type
     *
     * @param contentType Content-Type
     */
    public void setContentType(String contentType) {
        this.contentType = StringUtils.trimToEmpty(contentType);
    }

    /**
     * 获取内容编码
     *
     * @return 内容编码
     */
    public String getContentEncoding() {
        return contentEncoding;
    }

    /**
     * 设置内容编码
     *
     * @param contentEncoding 内容编码
     */
    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = StringUtils.trimToEmpty(contentEncoding);
    }

    /**
     * 获取头部
     *
     * @param name 头部名称
     * @return 头部
     */
    public Header getHeader(String name) {
        return headersMap.get(name);
    }

    /**
     * 获取所有头部
     *
     * @return 头部数组
     */
    public Header[] getAllHeaders() {
        return headersMap.values().toArray(new Header[0]);
    }

    /**
     * 获取内容
     *
     * @return 内容
     */
    public T getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(T content) {
        this.content = content;
    }

    /**
     * 判断是否请求成功
     *
     * @return 是否请求成功
     */
    public boolean isSuccess() {
        return HttpServletResponse.SC_OK == code;
    }
}
