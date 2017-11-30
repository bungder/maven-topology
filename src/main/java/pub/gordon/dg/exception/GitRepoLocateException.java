package pub.gordon.dg.exception;

/**
 * @author Gordon
 * @date 2017-11-17 09:42
 */
public class GitRepoLocateException extends SilentException {
    public GitRepoLocateException() {
    }

    public GitRepoLocateException(String message) {
        super(message);
    }

    public GitRepoLocateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GitRepoLocateException(Throwable cause) {
        super(cause);
    }

    public GitRepoLocateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
