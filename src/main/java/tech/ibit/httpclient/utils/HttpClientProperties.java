package tech.ibit.httpclient.utils;

/**
 * HttpClient 配置
 *
 * @author 小ben马
 */
public class HttpClientProperties {

    /**
     * 连接超时（单位：ms）
     */
    private int connectTimeout = 1500;

    /**
     * 请求读取超时（单位：ms）
     */
    private int connectionRequestTimeout = 2000;

    /**
     * socket超时（单位：ms）
     */
    private int socketTimeout = 5000;

    /**
     * keep alive 时间（单位：s）
     */
    private int connectionKeepAliveTimeout = 5;

    /**
     * 是否使用keep alive策略
     */
    private boolean useKeepAliveStrategy = true;

    /**
     * 池最大大小
     */
    private int poolMaxTotal = 50;

    /**
     * 单个主机的最大连接数
     */
    private int poolDefaultMaxPerRoute = 20;

    /**
     * 是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
     */
    private boolean socketTcpNoDelay = true;

    /**
     * 是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
     */
    private boolean socketSoReuseAddress = true;

    /**
     * 接收数据的等待超时时间（单位：ms）
     */
    private int socketSoTimeout = 500;

    /**
     * 关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
     */
    private int socketSoLinger = 60;

    /**
     * 是否开启监视TCP连接是否有效
     */
    private boolean socketSoKeepAlive = true;

    /**
     * 设置重试次数
     */
    private int retryCount = 0;

    /**
     * 是否启用请求重试
     */
    private boolean requestSentRetryEnabled = false;

    /**
     * Gets the value of connectTimeout
     *
     * @return the value of connectTimeout
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the connectTimeout
     * <p>You can use getConnectTimeout() to get the value of connectTimeout</p>
     *
     * @param connectTimeout connectTimeout
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * Gets the value of connectionRequestTimeout
     *
     * @return the value of connectionRequestTimeout
     */
    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    /**
     * Sets the connectionRequestTimeout
     * <p>You can use getConnectionRequestTimeout() to get the value of connectionRequestTimeout</p>
     *
     * @param connectionRequestTimeout connectionRequestTimeout
     */
    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    /**
     * Gets the value of socketTimeout
     *
     * @return the value of socketTimeout
     */
    public int getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * Sets the socketTimeout
     * <p>You can use getSocketTimeout() to get the value of socketTimeout</p>
     *
     * @param socketTimeout socketTimeout
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    /**
     * Gets the value of connectionKeepAliveTimeout
     *
     * @return the value of connectionKeepAliveTimeout
     */
    public int getConnectionKeepAliveTimeout() {
        return connectionKeepAliveTimeout;
    }

    /**
     * Sets the connectionKeepAliveTimeout
     * <p>You can use getConnectionKeepAliveTimeout() to get the value of connectionKeepAliveTimeout</p>
     *
     * @param connectionKeepAliveTimeout connectionKeepAliveTimeout
     */
    public void setConnectionKeepAliveTimeout(int connectionKeepAliveTimeout) {
        this.connectionKeepAliveTimeout = connectionKeepAliveTimeout;
    }

    /**
     * Gets the value of useKeepAliveStrategy
     *
     * @return the value of useKeepAliveStrategy
     */
    public boolean isUseKeepAliveStrategy() {
        return useKeepAliveStrategy;
    }

    /**
     * Sets the useKeepAliveStrategy
     * <p>You can use getUserKeepAliveStrategy() to get the value of useKeepAliveStrategy</p>
     *
     * @param useKeepAliveStrategy useKeepAliveStrategy
     */
    public void setUseKeepAliveStrategy(boolean useKeepAliveStrategy) {
        this.useKeepAliveStrategy = useKeepAliveStrategy;
    }

    /**
     * Gets the value of poolMaxTotal
     *
     * @return the value of poolMaxTotal
     */
    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    /**
     * Sets the poolMaxTotal
     * <p>You can use getPoolMaxTotal() to get the value of poolMaxTotal</p>
     *
     * @param poolMaxTotal poolMaxTotal
     */
    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    /**
     * Gets the value of poolDefaultMaxPerRoute
     *
     * @return the value of poolDefaultMaxPerRoute
     */
    public int getPoolDefaultMaxPerRoute() {
        return poolDefaultMaxPerRoute;
    }

    /**
     * Sets the poolDefaultMaxPerRoute
     * <p>You can use getPoolDefaultMaxPerRoute() to get the value of poolDefaultMaxPerRoute</p>
     *
     * @param poolDefaultMaxPerRoute poolDefaultMaxPerRoute
     */
    public void setPoolDefaultMaxPerRoute(int poolDefaultMaxPerRoute) {
        this.poolDefaultMaxPerRoute = poolDefaultMaxPerRoute;
    }

    /**
     * Gets the value of socketTcpNoDelay
     *
     * @return the value of socketTcpNoDelay
     */
    public boolean isSocketTcpNoDelay() {
        return socketTcpNoDelay;
    }

    /**
     * Sets the socketTcpNoDelay
     * <p>You can use getSocketTcpNoDelay() to get the value of socketTcpNoDelay</p>
     *
     * @param socketTcpNoDelay socketTcpNoDelay
     */
    public void setSocketTcpNoDelay(boolean socketTcpNoDelay) {
        this.socketTcpNoDelay = socketTcpNoDelay;
    }

    /**
     * Gets the value of socketSoReuseAddress
     *
     * @return the value of socketSoReuseAddress
     */
    public boolean isSocketSoReuseAddress() {
        return socketSoReuseAddress;
    }

    /**
     * Sets the socketSoReuseAddress
     * <p>You can use getSocketSoReuseAddress() to get the value of socketSoReuseAddress</p>
     *
     * @param socketSoReuseAddress socketSoReuseAddress
     */
    public void setSocketSoReuseAddress(boolean socketSoReuseAddress) {
        this.socketSoReuseAddress = socketSoReuseAddress;
    }

    /**
     * Gets the value of socketSoTimeout
     *
     * @return the value of socketSoTimeout
     */
    public int getSocketSoTimeout() {
        return socketSoTimeout;
    }

    /**
     * Sets the socketSoTimeout
     * <p>You can use getSocketSoTimeout() to get the value of socketSoTimeout</p>
     *
     * @param socketSoTimeout socketSoTimeout
     */
    public void setSocketSoTimeout(int socketSoTimeout) {
        this.socketSoTimeout = socketSoTimeout;
    }

    /**
     * Gets the value of socketSoLinger
     *
     * @return the value of socketSoLinger
     */
    public int getSocketSoLinger() {
        return socketSoLinger;
    }

    /**
     * Sets the socketSoLinger
     * <p>You can use getSocketSoLinger() to get the value of socketSoLinger</p>
     *
     * @param socketSoLinger socketSoLinger
     */
    public void setSocketSoLinger(int socketSoLinger) {
        this.socketSoLinger = socketSoLinger;
    }

    /**
     * Gets the value of socketSoKeepAlive
     *
     * @return the value of socketSoKeepAlive
     */
    public boolean isSocketSoKeepAlive() {
        return socketSoKeepAlive;
    }

    /**
     * Sets the socketSoKeepAlive
     * <p>You can use getSocketSoKeepAlive() to get the value of socketSoKeepAlive</p>
     *
     * @param socketSoKeepAlive socketSoKeepAlive
     */
    public void setSocketSoKeepAlive(boolean socketSoKeepAlive) {
        this.socketSoKeepAlive = socketSoKeepAlive;
    }

    /**
     * Gets the value of retryCount
     *
     * @return the value of retryCount
     */
    public int isRetryCount() {
        return retryCount;
    }

    /**
     * Sets the retryCount
     * <p>You can use getRetryCount() to get the value of retryCount</p>
     *
     * @param retryCount retryCount
     */
    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}
