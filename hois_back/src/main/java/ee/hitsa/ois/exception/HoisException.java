package ee.hitsa.ois.exception;

public class HoisException extends RuntimeException {
    private static final long serialVersionUID = 204312343476782852L;

    public HoisException(Throwable cause) {
        super(cause);
    }

    public HoisException(String message) {
        super(message);
    }

}
