package ro.ucv.ace.exception;

/**
 * This exception is the most general exception that the application can throw.
 *
 * @author Georgian Vladutu
 */
public class MyApplicationException extends Exception {

    public MyApplicationException() {
    }

    public MyApplicationException(String message) {
        super(message);
    }

    public MyApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyApplicationException(Throwable cause) {
        super(cause);
    }

    public MyApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
