package ro.ucv.ace.exception;

/**
 * Created by Geo on 05.06.2016.
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
