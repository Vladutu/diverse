package ro.ucv.ace.exception;

/**
 * Created by Geo on 24.09.2016.
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
