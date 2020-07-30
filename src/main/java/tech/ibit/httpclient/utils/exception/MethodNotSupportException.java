package tech.ibit.httpclient.utils.exception;

/**
 * 方法不支持
 *
 * @author 小ben马
 */
public class MethodNotSupportException extends Exception {

    /**
     * 构造函数
     *
     * @param methodName 方法名称
     */
    public MethodNotSupportException(String methodName) {
        super("Method " + methodName + " not support!");
    }
}
