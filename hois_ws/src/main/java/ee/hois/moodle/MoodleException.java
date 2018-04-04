package ee.hois.moodle;

import ee.hois.moodle.dto.ErrorResponse;

public class MoodleException extends RuntimeException {
    
    private String errorcode;

    public String getErrorcode() {
        return errorcode;
    }

    public MoodleException(String message) {
        super(message);
    }

    public MoodleException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MoodleException(Throwable cause) {
        super(cause);
    }

    public MoodleException(ErrorResponse error) {
        super(error.getMessage());
        this.errorcode = error.getErrorcode();
    }
    
}
