### Usage about HttpClientUtil

HttpClientUtil encapsulates the code to use HttpClient much easier.

#### Main dependencies

```
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.2</version>
</dependency>

<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpmime</artifactId>
    <version>4.5.2</version>
</dependency>
```

#### Main method

```java
/**
 * Do http request
 * @param request request object
 * @return response object
 */
public static Response doRequest(Request request)
```

#### Request

```
Relationship about Request

Request (Request without body, "GET", "OPTIONS", "HEAD" are supported)
      |---BaseEntityRequest (Request with body, "POST", "PUT", "PATCH" are supported)
                      |--- UrlEncodedFormRequest (application/x-www-form-urlencoded)
                      |--- MultiPartFormRequest (multipart/form-data)
                      |--- JsonRequest (json body)
```

It also support to send request with ssl connection.

If some exception was threw like following description, please solve the problem refer to the [blog](https://blogs.oracle.com/gc/entry/unable_to_find_valid_certification).
You can also find `InstallCert` at the package `com.xiaobenma020.http.cert` of the project.

```
javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
    at sun.security.ssl.Alerts.getSSLException(Alerts.java:192)
    at sun.security.ssl.SSLSocketImpl.fatal(SSLSocketImpl.java:1949)
    at sun.security.ssl.Handshaker.fatalSE(Handshaker.java:302)
    at sun.security.ssl.Handshaker.fatalSE(Handshaker.java:296)
...
Caused by: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
    at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:387)
    at sun.security.validator.PKIXValidator.engineValidate(PKIXValidator.java:292)
    at sun.security.validator.Validator.validate(Validator.java:260)
    at sun.security.ssl.X509TrustManagerImpl.validate(X509TrustManagerImpl.java:324)
    at sun.security.ssl.X509TrustManagerImpl.checkTrusted(X509TrustManagerImpl.java:229)

```

Solution key point:

```
$ java InstallCert _web_site_hostname_
  operate follow the tips

then move the file "jssecacerts" to $JAVA_HOME/jre/lib/security

```



#### example code

```
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
```

#### Finally

If you want to support much more request body, please to extend `BaseEntityRequest` and complete the method `getEntity`.
