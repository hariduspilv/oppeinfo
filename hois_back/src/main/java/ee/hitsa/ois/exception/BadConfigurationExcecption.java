package ee.hitsa.ois.exception;

public class BadConfigurationExcecption extends RuntimeException {
    private static final long serialVersionUID = 3625599093932597086L;

    public BadConfigurationExcecption(String message) {
        super(message);
    }

    public BadConfigurationExcecption(String message, Throwable cause) {
        super(message, cause);
    }

}
