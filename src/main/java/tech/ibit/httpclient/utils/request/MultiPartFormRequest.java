package tech.ibit.httpclient.utils.request;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.CharsetUtils;
import tech.ibit.httpclient.utils.exception.MethodNotSupportException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * multi-part form data 请求
 *
 * @author 小ben马
 */
public class MultiPartFormRequest extends BaseEntityRequest {

    /**
     * Parts
     */
    private Map<String, Object> parts;

    /**
     * 构造函数
     *
     * @param url    请求url
     * @param method 方法名称
     * @throws MethodNotSupportException 方法不支持
     */
    public MultiPartFormRequest(String url, RequestMethod method) throws MethodNotSupportException {
        super(url, method);
        this.parts = new LinkedHashMap<>();
    }

    /**
     * 构造函数
     *
     * @param url 请求url
     * @throws MethodNotSupportException 方法不支持
     */
    public MultiPartFormRequest(String url) throws MethodNotSupportException {
        super(url);
        this.parts = new LinkedHashMap<>();
    }

    /**
     * 增加 Part
     *
     * @param name 名称
     * @param body 内容
     */
    public void addPart(String name, Object body) {
        parts.put(name, body);
    }

    /**
     * 批量增加 Part
     *
     * @param parts Part Map
     */
    public void addParts(Map<String, Object> parts) {
        if (null != parts && !parts.isEmpty()) {
            this.parts.putAll(parts);
        }
    }

    /**
     * 移除 map
     *
     * @param name Part名称
     */
    public void removePart(String name) {
        parts.remove(name);
    }

    /**
     * 通过Part Map
     *
     * @return Part Map
     */
    public Map<String, Object> getParts() {
        return parts;
    }

    @Override
    public HttpEntity getEntity() {
        try {
            Charset charset = CharsetUtils.get(getRequestCharset());
            //Run in browser-compatible mode to prevent file names from being garbled.
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setCharset(charset);
            getParts().forEach((key, value) -> {
                //for file
                if (value instanceof File) {
                    FileBody fileBody = new FileBody((File) value);
                    builder.addPart(key, fileBody);
                } else {
                    String stringValue = null == value ? "" : value.toString();
                    StringBody stringBody = new StringBody(
                            stringValue,
                            ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset)
                    );
                    builder.addPart(key, stringBody);
                }
            });
            return builder.build();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Charset '" + getRequestCharset() + "' is unsupported!");
        }
    }

}
