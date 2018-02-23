package ee.hitsa.ois.util;

import java.util.Collection;
import java.util.List;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class SubjectStudyPeriodUtil {

    public static List<SubjectStudyPeriod> filterSsps(Collection<SubjectStudyPeriod> ssps, Long studyPeriod) {
        return StreamUtil.toFilteredList(ssp -> EntityUtil.getId(ssp.getStudyPeriod()).equals(studyPeriod), ssps);
    }

    public static Long getHours(List<SubjectStudyPeriod> ssps) {
        long sum = 0;
        for (SubjectStudyPeriod ssp : ssps) {
            sum += ssp.getCapacities().stream().mapToLong(SubjectStudyPeriodCapacity::getHours).sum();
        }
        return Long.valueOf(sum);
    }

    public static void assertCanUpdate(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, subjectStudyPeriod.getSubject().getSchool());
        if(user.isTeacher() && !teacherAddedToSsp(user.getTeacherId(), subjectStudyPeriod.getTeachers())) {
            throw new ValidationFailedException("teacher not added to subject study period");
        }
    }

    private static boolean teacherAddedToSsp(Long teacherId, List<SubjectStudyPeriodTeacher> sspTeachers) {
        return sspTeachers.stream()
                .anyMatch(t -> teacherId.equals(EntityUtil.getId(t.getTeacher())));
    }
}
