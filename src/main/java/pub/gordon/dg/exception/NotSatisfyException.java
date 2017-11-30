package pub.gordon.dg.exception;

/**
 * @author Gordon
 * @date 2017-11-03 10:37
 */
public class NotSatisfyException extends SilentException {
    public NotSatisfyException() {
    }

    public NotSatisfyException(String message) {
        super(message);
    }

    public NotSatisfyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSatisfyException(Throwable cause) {
        super(cause);
    }

    public NotSatisfyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
