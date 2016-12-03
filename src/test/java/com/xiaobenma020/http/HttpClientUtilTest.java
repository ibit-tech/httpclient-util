package com.xiaobenma020.http;

import com.xiaobenma020.http.request.RequestMethod;
import com.xiaobenma020.http.request.UrlEncodedFormRequest;
import com.xiaobenma020.http.response.Response;
import org.junit.Test;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class HttpClientUtilTest {

    @Test
    public void doResponse() throws Exception {
        UrlEncodedFormRequest request = new UrlEncodedFormRequest("https://xxx.com/login", RequestMethod.POST);

        //url form param
        request.addParam("loginId", "loginId");
        request.addParam("password", "password");

        //query string param
        request.addUrlParam("version", "v1");

        //ssl
        request.setUseSSL(true);

        Response response = HttpClientUtil.doRequest(request);
        System.out.println(response.getResponseText()); //response text
        System.out.println(response.getCode()); //response code
        System.out.println(response.getHeader("Set-Cookie"));
    }

}