package ro.ucv.ace.exception;

/**
 * Created by Geo on 13.02.2017.
 */
public class InvalidClickException extends RuntimeException {
    public InvalidClickException() {
    }

    public InvalidClickException(String message) {
        super(message);
    }

    public InvalidClickException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidClickException(Throwable cause) {
        super(cause);
    }

    public InvalidClickException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
