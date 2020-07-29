package tech.ibit.httpclient.utils.request;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import tech.ibit.httpclient.utils.exception.MethodNotSupportException;

/**
 * Json 请求
 *
 * @author 小ben马
 */
public class JsonRequest extends BaseEntityRequest {

    /**
     * Json 的 conent-type
     */
    private static final String JSON_CONTENT_TYPE = "application/json; charset=";

    /**
     * Json对象
     */
    private Object jsonObject;

    /**
     * 构造函数
     *
     * @param url    请求url
     * @param method 方法名称
     * @throws MethodNotSupportException 方法不支持
     */
    public JsonRequest(String url, RequestMethod method) throws MethodNotSupportException {
        super(url, method);
    }

    /**
     * 构造函数
     *
     * @param url 请求url
     * @throws MethodNotSupportException 方法不支持
     */
    public JsonRequest(String url) throws MethodNotSupportException {
        super(url);
    }

    /**
     * 获取json对象
     *
     * @return json对象
     */
    public Object getJsonObject() {
        return jsonObject;
    }

    /**
     * 设置json对象
     *
     * @param jsonObject json对象
     */
    public void setJsonObject(Object jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public HttpEntity getEntity() {
        StringEntity stringEntity = new StringEntity(getJsonObject().toString(), getRequestCharset());
        stringEntity.setContentEncoding(getRequestCharset());
        stringEntity.setContentType(JSON_CONTENT_TYPE + getRequestCharset().toLowerCase());
        return stringEntity;
    }
}
