package pub.gordon.dg.exception;

/**
 * @author Gordon
 * @date 2017-11-03 11:43
 */
public class UnknownCommandException extends SilentException {
    public UnknownCommandException() {
    }

    public UnknownCommandException(String message) {
        super(message);
    }

    public UnknownCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCommandException(Throwable cause) {
        super(cause);
    }

    public UnknownCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
