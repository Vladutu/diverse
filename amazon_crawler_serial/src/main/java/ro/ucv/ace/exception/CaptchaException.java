package ro.ucv.ace.exception;

/**
 * Created by Geo on 09.02.2017.
 */
public class CaptchaException extends RuntimeException {
    public CaptchaException() {
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }

    public CaptchaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
