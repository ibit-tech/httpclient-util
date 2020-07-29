package tech.ibit.httpclient.utils.dns;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.DnsResolver;
import org.apache.http.impl.conn.InMemoryDnsResolver;
import tech.ibit.common.cache.LruCache;
import tech.ibit.common.cache.MemCache;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * DNS 缓存
 *
 * @author ben
 */
public class CacheDnsResolver implements DnsResolver {

    private final Log log = LogFactory.getLog(InMemoryDnsResolver.class);

    /**
     * dns缓存
     */
    private MemCache<String, InetAddress[]> dnsCache;


    /**
     * 构造函数
     *
     * @param maxSize 缓存大小
     * @param timeout 过期时间（单位：s)
     */
    public CacheDnsResolver(int maxSize, int timeout) {
        dnsCache = new LruCache<>(maxSize, timeout * 1000);
    }

    @Override
    public InetAddress[] resolve(String host) throws UnknownHostException {

        InetAddress[] resolvedAddresses = dnsCache.get(host);

        if (null == resolvedAddresses) {
            resolvedAddresses = InetAddress.getAllByName(host);
            if (ArrayUtils.isEmpty(resolvedAddresses)) {
                throw new UnknownHostException(host + " cannot be resolved");
            }
            dnsCache.put(host, resolvedAddresses);
        }

        if (this.log.isInfoEnabled()) {
            this.log.info("Resolving " + host + " to " + Arrays.deepToString(resolvedAddresses));
        }
        return resolvedAddresses;
    }
}
