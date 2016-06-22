package ro.ucv.ace.exception;

/**
 * This exception is thrown when a unique result is expected, but more than one result is returned.
 *
 * @author Georgian Vladutu
 */
public class DaoNonUniqueResultException extends DaoException {

    public DaoNonUniqueResultException() {
    }

    public DaoNonUniqueResultException(String message) {
        super(message);
    }

    public DaoNonUniqueResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoNonUniqueResultException(Throwable cause) {
        super(cause);
    }

    public DaoNonUniqueResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
