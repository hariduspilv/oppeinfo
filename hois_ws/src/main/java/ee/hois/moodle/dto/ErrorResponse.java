package ee.hois.moodle.dto;

public class ErrorResponse {
    private String message;
    private String errorcode;
    private String exception;
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getErrorcode() {
        return errorcode;
    }
    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }
    
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
    
}
