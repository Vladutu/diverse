package ro.ucv.ace.exception;

/**
 * Created by Geo on 29.05.2016.
 */
public class DaoDuplicateEntryException extends DaoException {

    public DaoDuplicateEntryException() {
    }

    public DaoDuplicateEntryException(String message) {
        super(message);
    }

    public DaoDuplicateEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoDuplicateEntryException(Throwable cause) {
        super(cause);
    }

    public DaoDuplicateEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
