package reflection;

/**
 * 反射工具类自定义异常
 *
 * @author lixin
 * @date 2018-12-24 18:07
 **/
public class ReflectionException extends RuntimeException{

    private static final long serialVersionUID = 5050706491422824442L;

    public ReflectionException() {
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }

    public ReflectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
