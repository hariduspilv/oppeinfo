package ee.hitsa.ois.util;

import java.util.List;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class SubjectStudyPeriodValidationUtil {
    
    public static void assertCanUpdate(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, subjectStudyPeriod.getSubject().getSchool());
        if(user.isTeacher() && !teacherAddedToSsp(user.getTeacherId(), subjectStudyPeriod.getTeachers())) {
            throw new ValidationFailedException("teacher not added to subject study period");
        }
    }
    
    private static boolean teacherAddedToSsp (Long teacherId, List<SubjectStudyPeriodTeacher> sspTeachers) {
        return sspTeachers.stream()
                .anyMatch(t -> teacherId.equals(EntityUtil.getId(t.getTeacher())));
    }

}
