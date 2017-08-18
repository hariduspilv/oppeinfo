package ee.hitsa.ois.exception;

import java.util.Map;

public class BadConfigurationExcecption extends SingleMessageWithParamsException {

    public BadConfigurationExcecption(String message) {
        super(message);
    }

    public BadConfigurationExcecption(String message, Map<Object, Object> params) {
        super(message, params);
    }

    public BadConfigurationExcecption(String message, Throwable cause) {
        super(message, cause);
    }

}
