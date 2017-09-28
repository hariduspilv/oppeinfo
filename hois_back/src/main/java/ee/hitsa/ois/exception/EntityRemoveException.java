package ee.hitsa.ois.exception;

public class EntityRemoveException extends RuntimeException {

    public EntityRemoveException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
