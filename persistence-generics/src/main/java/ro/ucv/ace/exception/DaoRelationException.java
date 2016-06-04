package ro.ucv.ace.exception;

/**
 * Created by Geo on 30.05.2016.
 */
public class DaoRelationException extends DaoException {

    public DaoRelationException() {
    }

    public DaoRelationException(String message) {
        super(message);
    }

    public DaoRelationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoRelationException(Throwable cause) {
        super(cause);
    }

    public DaoRelationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
