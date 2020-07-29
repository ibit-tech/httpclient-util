package tech.ibit.httpclient.utils.request;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import tech.ibit.httpclient.utils.exception.MethodNotSupportException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * UrlEncodedForm 请求
 *
 * @author 小ben马
 */
public class UrlEncodedFormRequest extends BaseEntityRequest {

    /**
     * 参数map
     */
    private Map<String, Object> params;

    /**
     * 构造函数
     *
     * @param url    请求url
     * @param method 方法名称
     * @throws MethodNotSupportException 方法不支持
     */
    public UrlEncodedFormRequest(String url, RequestMethod method) throws MethodNotSupportException {
        super(url, method);
        params = new LinkedHashMap<>();
    }

    /**
     * 构造函数
     *
     * @param url 请求url
     * @throws MethodNotSupportException 方法不支持
     */
    public UrlEncodedFormRequest(String url) throws MethodNotSupportException {
        super(url);
        params = new LinkedHashMap<>();
    }

    /**
     * 增加参数
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void addParam(String name, Object value) {
        params.put(name, value);
    }

    /**
     * 批量增加参数
     *
     * @param params 参数map
     */
    public void addParams(Map<String, Object> params) {
        if (null != params && !params.isEmpty()) {
            this.params.putAll(params);
        }
    }

    /**
     * 移除参数
     *
     * @param name 参数名称
     */
    public void removeParam(String name) {
        params.remove(name);
    }

    /**
     * 获取参数map
     *
     * @return 参数map
     */
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public HttpEntity getEntity() {
        Map<String, Object> params = getParams();
        List<NameValuePair> pairList = new ArrayList<>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }

        return new UrlEncodedFormEntity(pairList, Charset.forName(getRequestCharset()));
    }


}
