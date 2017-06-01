package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Classifier;

public class CurriculumUtil {

    public static final char SCHOOL_STUDY_LEVEL = '5';

    /**
     *  Magic value here is 500(also contains weird values such as 7R)
     */
    public static boolean isHigher(Classifier studyLevel) {
        return studyLevel.getValue().charAt(0) >= SCHOOL_STUDY_LEVEL;
    }

    /**
     *  Magic value here is 500
     */
    public static boolean isVocational(Classifier studyLevel) {
        return isVocational(studyLevel.getValue());
    }

    public static boolean isVocational(String studyLevelValue) {
        return studyLevelValue != null && studyLevelValue.length() >  0 && studyLevelValue.charAt(0) < SCHOOL_STUDY_LEVEL;
    }

    public static String versionName(String versionCode, String curriculumName) {
        return versionCode+" "+curriculumName;
    }
}
