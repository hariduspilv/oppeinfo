package ee.hitsa.ois.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.Error;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.ErrorForField;

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
        List<ErrorForField> allErrors = new ArrayList<>();
        for(ConstraintViolation<T> e : errors) {
            allErrors.add(new ErrorForField(e.getMessage(), e.getPropertyPath().toString()));
        }
        this.errorInfo = ErrorInfo.of(allErrors);
    }

    public ValidationFailedException(List<? extends Error> errors) {
        this.errorInfo = ErrorInfo.of(errors);
    }
    
    public ValidationFailedException(String code, Map<Object, Object> params) {
        this(Collections.singletonList(new Error(code, params)));
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public static void throwIf(boolean expression, String message) {
        if(expression) {
            throw new ValidationFailedException(message);
        }
    }
    
    public static <T> void throwOnError(Set<ConstraintViolation<T>> errors) {
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
    }
}
