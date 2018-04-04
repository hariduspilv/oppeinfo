package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.http.HttpServletRequest;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.service.moodle.MoodleContext;
import ee.hitsa.ois.service.security.HoisUserDetails;

public class MoodleUtil {

    public static JournalEntryType gradeItemTypeToJournalEntryType(String gradeItemType) {
        switch (gradeItemType) {
        case "course": return JournalEntryType.SISSEKANNE_L;
        case "mod": return JournalEntryType.SISSEKANNE_H;
        default: return JournalEntryType.SISSEKANNE_T;
        }
    }

    public static boolean isFinalGradeItemType(String gradeItemType) {
        return "course".equals(gradeItemType);
    }
    
    public static OccupationalGrade pointsToGrade(BigDecimal points, BigDecimal max) {
        long grade = points.multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.ZERO.compareTo(max) < 0 ? max : BigDecimal.ONE, 0, RoundingMode.HALF_UP)
                .longValue();
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

    public static MoodleContext createContext(HoisUserDetails user, HttpServletRequest request) {
        MoodleContext context = new MoodleContext(user);
        context.setIpAddress(HttpUtil.getIpAddress(request));
        return context;
    }

    public static MoodleContext createContext(HoisUserDetails user, HttpServletRequest request, 
            Journal journal) {
        MoodleContext context = createContext(user, request);
        context.setJournalId(EntityUtil.getId(journal));
        return context;
    }

    public static MoodleContext createContext(HoisUserDetails user, HttpServletRequest request, 
            SubjectStudyPeriod subjectStudyPeriod) {
        MoodleContext context = createContext(user, request);
        context.setSubjectStudyPeriodId(EntityUtil.getId(subjectStudyPeriod));
        return context;
    }

}
