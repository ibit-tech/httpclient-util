package tech.ibit.httpclient.utils;

import org.junit.Ignore;
import org.junit.Test;
import tech.ibit.httpclient.utils.request.RequestMethod;
import tech.ibit.httpclient.utils.request.UrlEncodedFormRequest;
import tech.ibit.httpclient.utils.response.Response;

/**
 * HttpClient工具类测试
 *
 * @author 小ben马
 */
public class HttpClientUtilsTest {

    @Test
    @Ignore
    public void doResponse() throws Exception {
        UrlEncodedFormRequest request = new UrlEncodedFormRequest("https://xxx.com/login", RequestMethod.POST);

        //url form param
        request.addParam("loginId", "loginId");
        request.addParam("password", "password");

        //query string param
        request.addUrlParam("version", "v1");

        //ssl
        request.setSecured(true);

        Response response = HttpClientUtils.doRequest(request, true);
        System.out.println(response.getContent()); //response text
        System.out.println(response.getCode()); //response code
        System.out.println(response.getHeader("Set-Cookie"));
    }

}