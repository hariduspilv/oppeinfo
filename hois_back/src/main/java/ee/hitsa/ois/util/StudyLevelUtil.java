package ee.hitsa.ois.util;

public abstract class StudyLevelUtil {

    public static boolean isHigher(String studyLevel) {
        char c = studyLevel.charAt(9);
        if(!Character.isDigit(c)) {
            return false;
        }
        return Character.getNumericValue(c) >= 5;
    }

    public static boolean isVocational(String studyLevel) {
        char c = studyLevel.charAt(9);
        if(!Character.isDigit(c)) {
            return false;
        }
        return Character.getNumericValue(c) < 5;
    }
}
