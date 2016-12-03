package com.xiaobenma020.http.request;

import com.xiaobenma020.http.exception.MethodNotSupportException;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

/**
 * Request for Json
 * mailto:xiaobenma020@gmail.com
 *
 */
public class JsonRequest extends BaseEntityRequest {

    private Object jsonObject;

    private static final String JSON_CONTENT_TYPE = "application/json";

    public JsonRequest(String url, RequestMethod method) throws MethodNotSupportException {
        super(url, method);
    }

    public JsonRequest(String url) throws MethodNotSupportException {
        super(url);
    }

    public Object getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(Object jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public HttpEntity getEntity() {
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), getRequestCharset());
        stringEntity.setContentEncoding(getResponseDefaultCharset());
        stringEntity.setContentType(JSON_CONTENT_TYPE);
        return stringEntity;
    }
}
