package ee.hitsa.ois.util;

public class AssertionFailedException extends RuntimeException {

    public AssertionFailedException() {
    }

    public AssertionFailedException(String message) {
        super(message);
    }
}
