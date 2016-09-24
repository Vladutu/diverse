package ro.ucv.ace.exception;

/**
 * Created by Geo on 24.09.2016.
 */
public class InvalidSongException extends MyApplicationException {
    public InvalidSongException() {
    }

    public InvalidSongException(String message) {
        super(message);
    }

    public InvalidSongException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSongException(Throwable cause) {
        super(cause);
    }

    public InvalidSongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
