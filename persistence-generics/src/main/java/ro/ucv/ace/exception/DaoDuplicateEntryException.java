package ro.ucv.ace.exception;

/**
 * This exception is thrown when a save or update operation occurs and a field that is set as unique already exists.
 *
 * @author Georgian Vladutu
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
