package com.xiaobenma020.http.request;

import com.xiaobenma020.http.exception.MethodNotSupportException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Request for UrlEncodedForm
 * mailto:xiaobenma020@gmail.com
 */
public class UrlEncodedFormRequest extends BaseEntityRequest {

    private Map<String, Object> params;

    public UrlEncodedFormRequest(String url, RequestMethod method) throws MethodNotSupportException {
        super(url, method);
        params = new LinkedHashMap<>();
    }

    public UrlEncodedFormRequest(String url) throws MethodNotSupportException {
        super(url);
        params = new LinkedHashMap<>();
    }

    @Override
    public HttpEntity getEntity() {
        List<NameValuePair> pairList = new ArrayList<>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }

        return new UrlEncodedFormEntity(pairList, Charset.forName(getRequestCharset()));
    }

    public void addParam(String name, Object value) {
        params.put(name, value);
    }

    public void addParams(Map<String, Object> params) {
        if (null != params && !params.isEmpty()) {
            this.params.putAll(params);
        }
    }

    public void removeParam(String name) {
        params.remove(name);
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
