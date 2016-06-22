package ro.ucv.ace.exception;

/**
 * This exception is thrown when an entity is not found.
 *
 * @author Georgian Vladutu
 */
public class DaoEntityNotFoundException extends DaoException {

    public DaoEntityNotFoundException() {
    }

    public DaoEntityNotFoundException(String message) {
        super(message);
    }

    public DaoEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoEntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public DaoEntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
