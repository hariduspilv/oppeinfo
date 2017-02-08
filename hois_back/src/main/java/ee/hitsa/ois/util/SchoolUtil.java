package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.School;

public class SchoolUtil {

    public static boolean isHigher(School school) {
        return school.getStudyLevels().stream().anyMatch(sl -> CurriculumUtil.isHigher(sl.getStudyLevel()));
    }

    public static boolean isVocational(School school) {
        return school.getStudyLevels().stream().anyMatch(sl -> CurriculumUtil.isVocational(sl.getStudyLevel()));
    }
}
