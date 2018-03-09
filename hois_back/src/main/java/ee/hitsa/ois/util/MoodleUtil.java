package ee.hitsa.ois.util;

import java.util.Collections;

import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.web.dto.moodle.EnrollResult;

public class MoodleUtil {

    public static EnrollResult createEmptyEnrollResult() {
        EnrollResult result = new EnrollResult();
        result.setEnrolled(0);
        result.setFailed(Collections.emptyList());
        result.setMissingUser(Collections.emptyList());
        return result;
    }
    
    public static JournalEntryType gradeItemTypeToJournalEntryType(String gradeItemType) {
        switch (gradeItemType) {
        case "course": return JournalEntryType.SISSEKANNE_L;
        case "mod": return JournalEntryType.SISSEKANNE_H;
        default: return JournalEntryType.SISSEKANNE_T;
        }
    }
    
    public static OccupationalGrade pointsToGrade(Object points, long max) {
        long grade = pointsToNumber(points).longValue() * 100 / max;
        if (grade >= 90) {
            return OccupationalGrade.KUTSEHINDAMINE_5;
        } else if (grade >= 70) {
            return OccupationalGrade.KUTSEHINDAMINE_4;
        } else if (grade >= 45) {
            return OccupationalGrade.KUTSEHINDAMINE_3;
        } else if (grade >= 20) {
            return OccupationalGrade.KUTSEHINDAMINE_2;
        } else {
            return OccupationalGrade.KUTSEHINDAMINE_1;
        }
    }

    public static Number pointsToNumber(Object points) {
        if (points instanceof Number) {
            return (Number) points;
        }
        String stringPoints = (String) points;
        try {
            return Long.parseLong(stringPoints);
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            return Double.parseDouble(stringPoints);
        }
    }
    
}
