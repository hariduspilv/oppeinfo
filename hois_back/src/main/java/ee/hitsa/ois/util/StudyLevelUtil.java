package ee.hitsa.ois.util;

public abstract class StudyLevelUtil {

    public static boolean isHigher(String studyLevel) {
        Character c = studyLevel.charAt(9);
        if(!Character.isDigit(c)) {
            return false;
        }
        int i = Character.getNumericValue(c);
        return i >= 5;
    }
    
    public static boolean isVocational(String studyLevel) {
        Character c = studyLevel.charAt(9);
        if(!Character.isDigit(c)) {
            return false;
        }
        int i = Character.getNumericValue(c);
        return i < 5;
    }
}
