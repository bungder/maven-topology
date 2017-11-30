package pub.gordon.dg.exception;

/**
 * @author Gordon
 * @date 2017-11-16 23:20
 */
public class POMNotFoundException extends SilentException{
    public POMNotFoundException() {
    }

    public POMNotFoundException(String message) {
        super(message);
    }

    public POMNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public POMNotFoundException(Throwable cause) {
        super(cause);
    }

    public POMNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
