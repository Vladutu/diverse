package ro.ucv.ace.parser;

public class MultiSentenceException extends RuntimeException {

    public MultiSentenceException() {
    }

    public MultiSentenceException(String message) {
        super(message);
    }

    public MultiSentenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultiSentenceException(Throwable cause) {
        super(cause);
    }

    public MultiSentenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
