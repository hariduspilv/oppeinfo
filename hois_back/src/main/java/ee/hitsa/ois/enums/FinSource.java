package ee.hitsa.ois.enums;

public enum FinSource {

    FINALLIKAS_RE,  // free
    FINALLIKAS_REV; // paid

    public static boolean isFree(String source) {
        return FINALLIKAS_RE.name().equals(source);
    }
}
