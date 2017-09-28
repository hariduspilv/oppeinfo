package ee.hitsa.ois.validation;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo;

public class ValidationFailedException extends RuntimeException {

    private final ErrorInfo errorInfo;

    public ValidationFailedException(String message) {
        super(message);
        this.errorInfo = ErrorInfo.of(message);
    }

    public ValidationFailedException(String field, String message) {
        super(String.format("%s: %s", field, message));
        this.errorInfo = ErrorInfo.of(message, field);
    }

    public <T> ValidationFailedException(Set<ConstraintViolation<T>> errors) {
        List<Map.Entry<String, String>> allErrors = new ArrayList<>();
        for(ConstraintViolation<T> e : errors) {
            allErrors.add(new AbstractMap.SimpleImmutableEntry<>(e.getPropertyPath().toString(), e.getMessage()));
        }
        this.errorInfo = ErrorInfo.of(allErrors);
    }

    public ValidationFailedException(List<Map.Entry<String, String>> errors) {
        this.errorInfo = ErrorInfo.of(errors);
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public static <T> void throwOnError(Set<ConstraintViolation<T>> errors) {
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
    }
}
