package ee.hitsa.ois.util;

public class EntityRemoveException extends RuntimeException {

    public EntityRemoveException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
