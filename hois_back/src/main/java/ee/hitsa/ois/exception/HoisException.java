package ee.hitsa.ois.exception;

public class HoisException extends SingleMessageWithParamsException {

    public HoisException(Throwable cause) {
        super(cause);
    }

    public HoisException(String message) {
        super(message);
    }

}
