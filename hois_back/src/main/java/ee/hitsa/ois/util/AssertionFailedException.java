package ee.hitsa.ois.util;

public class AssertionFailedException extends RuntimeException {

    public AssertionFailedException() {
    }

    public AssertionFailedException(String message) {
        super(message);
    }

    public static void assertTrue(boolean expression, String message) {
        if(!expression) {
            throw new AssertionFailedException(message);
        }
    }
}
