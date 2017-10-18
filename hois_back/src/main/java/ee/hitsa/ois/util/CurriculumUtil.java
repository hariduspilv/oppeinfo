package ee.hitsa.ois.util;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;

public class CurriculumUtil {
    public static final BigDecimal HOURS_PER_EKAP = BigDecimal.valueOf(26);
    public static final char SCHOOL_STUDY_LEVEL = '5';

    public static boolean isHigher(Curriculum curriculum) {
        return isHigher(curriculum.getOrigStudyLevel());
    }

    public static boolean isHigher(Classifier studyLevel) {
        return isHigher(studyLevel.getValue());
    }

    /**
     *  Magic value here is 500(also contains weird values such as 7R)
     */
    public static boolean isHigher(String studyLevelValue) {
        return studyLevelValue != null && studyLevelValue.length() >  0 && studyLevelValue.charAt(0) >= SCHOOL_STUDY_LEVEL;
    }

    public static boolean isVocational(Curriculum curriculum) {
        return isVocational(curriculum.getOrigStudyLevel());
    }

    public static boolean isVocational(Classifier studyLevel) {
        return isVocational(studyLevel.getValue());
    }

    /**
     *  Magic value here is 500
     */
    public static boolean isVocational(String studyLevelValue) {
        return studyLevelValue != null && studyLevelValue.length() >  0 && studyLevelValue.charAt(0) < SCHOOL_STUDY_LEVEL;
    }

    public static String moduleName(String moduleName, String moduleClassifierName, String curriculumCode) {
        return moduleName + " - " + moduleClassifierName + " (" + curriculumCode + ")";
    }

    public static String versionName(String versionCode, String curriculumName) {
        return versionCode+" "+curriculumName;
    }
}
