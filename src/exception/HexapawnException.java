package exception;

public class HexapawnException extends Exception {
    String message;

    public HexapawnException(String message, Exception e) {
        super(message, e);
    }

    public HexapawnException(String message) {
        super(message);
    }

    public HexapawnException(Throwable cause) {
        super(cause);
    }
}
