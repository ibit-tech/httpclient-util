package com.xiaobenma020.http.request;

import com.xiaobenma020.http.exception.MethodNotSupportException;
import org.apache.http.HttpEntity;

/**
 * mailto:xiaobenma020@gmail.com
 */
public abstract class BaseEntityRequest extends Request {

    public BaseEntityRequest(String url, RequestMethod method) throws MethodNotSupportException {
        super(url, method);
    }

    public BaseEntityRequest(String url) throws MethodNotSupportException {
        super(url);
    }

    //Check request method, "POST", "PUT", "PATCH" is supported
    @Override
    protected void checkMethod(RequestMethod method) throws MethodNotSupportException {
        if (null == method) {
            throw new MethodNotSupportException(null);
        }
        if (!RequestMethod.POST.equals(method) && !RequestMethod.PUT.equals(method) && !RequestMethod.PATCH.equals(method)) {
            throw new MethodNotSupportException(method.name());
        }
    }

    /**
     * Get HttpEntity about request body
     * @return HttpEntity
     */
    public abstract HttpEntity getEntity();
}
