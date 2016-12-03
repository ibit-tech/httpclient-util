package com.xiaobenma020.http.response;

import org.apache.http.Header;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * mailto:xiaobenma020@gmail.com
 */
public class Response {

    private int code;
    private String responseText;
    private String contentType;
    private String contentEncoding;

    private Map<String, Header> headersMap;

    public Response(int code) {
        this(code, null, null, null, null);
    }

    public Response(int code, Header[] headers) {
        this(code, null, null, null, headers);
    }


    public Response(int code, String responseText, String contentType
            , String contentEncoding, Header[] headers) {
        this.code = code;
        this.responseText = getNotNullString(responseText);
        this.contentType = getNotNullString(contentType);
        this.contentEncoding = getNotNullString(contentEncoding);
        headersMap = new LinkedHashMap<>();
        addHeaders(headers);
    }

    private void addHeaders(Header[] headers) {
        if (null != headers) {
            for (Header header : headers) {
                headersMap.put(header.getName(), header);
            }
        }
    }

    private String getNotNullString(String str) {
        return null == str ? "" : str;
    }

    private Header[] getNotNullHeaders(Header[] headers) {
        return null == headers ? new Header[0] : headers;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = getNotNullString(responseText);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = getNotNullString(contentType);
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = getNotNullString(contentEncoding);
    }

    public Header getHeader(String name) {
        return headersMap.get(name);
    }

    public Header[] getAllHeaders() {
        return headersMap.values().toArray(new Header[headersMap.size()]);
    }
}
