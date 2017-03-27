package ee.hitsa.ois.validation;

import java.util.List;
import java.util.Map;

import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo;

public class ValidationFailedException extends RuntimeException {

    private final ErrorInfo errorInfo;

    public ValidationFailedException(String field, String message) {
        this.errorInfo = ErrorInfo.of(message, field);
    }

    public ValidationFailedException(List<Map.Entry<String, String>> errors) {
        this.errorInfo = ErrorInfo.of(errors);
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}
