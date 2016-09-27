package ro.ucv.ace.error;

/**
 * This class contains information about an error.
 *
 * @author Georgian Alexnadru
 */
public class ErrorInfo {

    protected int statusCode;

    protected String message;

    public ErrorInfo(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}