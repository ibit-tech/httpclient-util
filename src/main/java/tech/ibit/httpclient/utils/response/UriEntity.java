package tech.ibit.httpclient.utils.response;

import java.net.URI;

/**
 * UriEntity
 *
 * @author ben
 */

public class UriEntity {

    /**
     * uri
     */
    private URI uri;

    /**
     * 是否重定向
     */
    private boolean redirect;

    /**
     * 构造函数
     *
     * @param uri uri
     */
    public UriEntity(URI uri) {
        this(uri, false);
    }

    /**
     * 构造函数
     *
     * @param uri      uri
     * @param redirect 是否重定向
     */
    public UriEntity(URI uri, boolean redirect) {
        this.uri = uri;
        this.redirect = redirect;
    }

    /**
     * Gets the value of uri
     *
     * @return the value of uri
     */
    public URI getUri() {
        return uri;
    }

    /**
     * Sets the uri
     * <p>You can use getUri() to get the value of uri</p>
     *
     * @param uri uri
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }

    /**
     * Gets the value of redirect
     *
     * @return the value of redirect
     */
    public boolean isRedirect() {
        return redirect;
    }

    /**
     * Sets the redirect
     * <p>You can use getRedirect() to get the value of redirect</p>
     *
     * @param redirect redirect
     */
    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }
}
