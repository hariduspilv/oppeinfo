package ee.hitsa.ois.exception;

import java.util.Map;

public class BadConfigurationException extends SingleMessageWithParamsException {

    public BadConfigurationException(String message) {
        super(message);
    }

    public BadConfigurationException(String message, Map<Object, Object> params) {
        super(message, params);
    }

    public BadConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
