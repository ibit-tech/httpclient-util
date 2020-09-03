package tech.ibit.httpclient.utils;

import java.util.*;

/**
 * UserAgent
 *
 * @author 小ben马
 */

public class UserAgentUtils {


    private static final Random random = new Random();

    /**
     * CHROME 版本号
     */
    private static Map<String, String> CHROME_VERSION_MAP = new HashMap<String, String>() {{
        put("70.0", "535.1");
        put("71.0", "535.2");
        put("72.0", "535.2");
        put("73.0", "535.7");
        put("74.0", "535.11");
        put("75.0", "535.19");
        put("76.0", "536.5");
        put("77.0", "536.1");
        put("78.0", "537.1");
        put("79.0", "537.4");
        put("80.0", "537.11");
        put("81.0", "537.17");
        put("82.0", "537.22");
        put("83.0", "537.31");
        put("84.0", "537.36");
    }};

    private static List<String> CHROME_VERSION_LIST = new ArrayList<>(CHROME_VERSION_MAP.keySet());

    /**
     * 随机盐
     */
    private static final int CHROME_SALT = CHROME_VERSION_LIST.size() * 10;

    /**
     * 系统版本
     */
    private final static String[] OS = new String[]{
            "Windows NT 6.4",
            "Windows NT 6.3",
            "Windows NT 6.2",
            "Windows NT 6.2; ARM;",
            "Windows NT 6.1",

            "Linux",
            "X11; Ubuntu;",

            "Macintosh; Intel Mac OS X 10_14",
            "Macintosh; Intel Mac OS X 10_13",
            "Macintosh; Intel Mac OS X 10_12",
            "Macintosh; Intel Mac OS X 10_11",

            "X11; CrOS x86_64"
    };

    /**
     * 随机盐
     */
    private static final int OS_SALT = OS.length * 10;

    /**
     * 获取随机的 User-Agent
     *
     * @return 随机 User-Agent
     */
    public static String getRandomUserAgent() {

        String os = OS[Math.abs(random.nextInt(OS_SALT)) % OS.length];
        String version = CHROME_VERSION_LIST.get(Math.abs(random.nextInt(CHROME_SALT)) % CHROME_VERSION_LIST.size());
        String webkitVersion = CHROME_VERSION_MAP.get(version);

        version = version + "." + random.nextInt(9999) + "." + random.nextInt(100);

        return String.format("Mozilla/5.0 (%s) AppleWebKit/%s (KHTML, like Gecko) Chrome/%s Safari/%s", os, webkitVersion, version, webkitVersion);
    }
}
