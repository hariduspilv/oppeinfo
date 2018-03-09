package ee.hois.moodle;

public class MoodleException extends RuntimeException {

    public MoodleException(String message) {
        super(message);
    }
    
    public MoodleException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MoodleException(Throwable cause) {
        super(cause);
    }
}
