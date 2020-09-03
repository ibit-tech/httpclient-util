package tech.ibit.httpclient.utils;

import org.junit.Ignore;
import org.junit.Test;
import tech.ibit.httpclient.utils.request.Request;
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

        String url = "https://www.douban.com/doubanapp/dispatch?uri=/group/topic/191568149";
        //String url = "https://www.douban.com/group/topic/191568149/";

        Request request = new Request(url);

        String userAgent = UserAgentUtils.getRandomUserAgent();
        request.addHeader("User-Agent", userAgent);
        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        request.addHeader("Accept-Encoding", "gzip, deflate");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");


        //ssl
        request.setSecured(true);

        Response response = HttpClientUtils.doRequest(request, true);
        //System.out.println(response.getContent()); //response text
        System.out.println(response.getCode()); //response code
        System.out.println(response.getRealUri().getUri());
        System.out.println(response.getRealUri().isRedirect());
    }

}