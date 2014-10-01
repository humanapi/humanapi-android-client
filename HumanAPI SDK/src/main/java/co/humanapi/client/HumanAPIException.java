package co.humanapi.client;

/**
 * Exception class for HumanAPI
 */
public class HumanAPIException extends Exception {
    public HumanAPIException() {
        super();
    }

    public HumanAPIException(String message) {
        super(message);
    }

    public HumanAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public HumanAPIException(Throwable cause) {
        super(cause);
    }
}
