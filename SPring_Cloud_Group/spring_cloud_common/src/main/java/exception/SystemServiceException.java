package exception;

public class SystemServiceException extends Exception {
    public SystemServiceException() {
        super();
    }

    public SystemServiceException(String message) {
        super(message);
    }

    public SystemServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemServiceException(Throwable cause) {
        super(cause);
    }

    protected SystemServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
