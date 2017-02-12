package ro.ucv.ace.exception;

/**
 * Created by Geo on 09.02.2017.
 */
public class DownloadErrorException extends RuntimeException {
    public DownloadErrorException() {
    }

    public DownloadErrorException(String message) {
        super(message);
    }

    public DownloadErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadErrorException(Throwable cause) {
        super(cause);
    }

    public DownloadErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
