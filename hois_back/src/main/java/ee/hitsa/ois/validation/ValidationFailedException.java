package ee.hitsa.ois.validation;

public class ValidationFailedException extends RuntimeException {

    private String field;
    private String message;

    public ValidationFailedException(String field, String message) {
        this.field = field;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
