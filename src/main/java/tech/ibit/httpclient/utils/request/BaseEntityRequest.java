package tech.ibit.httpclient.utils.request;

import org.apache.http.HttpEntity;
import tech.ibit.httpclient.utils.exception.MethodNotSupportException;

import java.util.HashSet;
import java.util.Set;

/**
 * 含body基础的请求
 *
 * @author 小ben马
 */
public abstract class BaseEntityRequest extends Request {

    /**
     * 构造函数
     *
     * @param url    请求url
     * @param method 方法名称
     * @throws MethodNotSupportException 方法不支持
     */
    public BaseEntityRequest(String url, RequestMethod method) throws MethodNotSupportException {
        super(url, method);
    }

    /**
     * 构造函数
     *
     * @param url 请求url
     * @throws MethodNotSupportException 方法不支持
     */
    public BaseEntityRequest(String url) throws MethodNotSupportException {
        super(url);
    }

    @Override
    public Set<RequestMethod> getSupportedMethods() {
        return new HashSet<RequestMethod>() {{
            add(RequestMethod.POST);
            add(RequestMethod.PUT);
            add(RequestMethod.PATCH);
        }};
    }

    /**
     * 请求实体
     *
     * @return 请求实体
     */
    public abstract HttpEntity getEntity();
}
