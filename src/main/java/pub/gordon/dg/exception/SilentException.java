package pub.gordon.dg.exception;

public class SilentException extends RuntimeException {
    public SilentException() {
    }

    public SilentException(String message) {
        super(message);
    }

    public SilentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SilentException(Throwable cause) {
        super(cause);
    }

    public SilentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
