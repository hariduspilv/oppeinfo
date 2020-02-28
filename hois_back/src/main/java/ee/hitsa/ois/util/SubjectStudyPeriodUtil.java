package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacherCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class SubjectStudyPeriodUtil {
    
    public static final Comparator<SubjectStudyPeriodSubgroup> COMPARATOR_SUBGROUP = new Comparator<SubjectStudyPeriodSubgroup>() {

        @Override
        public int compare(SubjectStudyPeriodSubgroup o1, SubjectStudyPeriodSubgroup o2) {
            int result = o1.getCode().compareTo(o2.getCode());
            
            if (result != 0) {
                return result;
            }
            
            if (o1.getTeacher() == o2.getTeacher()) {
                return 0;
            }
            if (o1.getTeacher() == null) {
                return -1;
            } else if (o2.getTeacher() == null) {
                return 1;
            }
            
            result = PersonUtil.fullname(o1.getTeacher().getTeacher().getPerson()).compareTo(PersonUtil.fullname(o2.getTeacher().getTeacher().getPerson()));
            
            if (result != 0) {
                return result;
            }

            if (o1.getId() == o2.getId()) {
                return 0;
            }
            if (o1.getId() == null) {
                return 1;
            } else if (o2.getId() == null) {
                return -1;
            }
            
            return o1.getId().compareTo(o2.getId());
        }
    };

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

    public static Long getHours(List<SubjectStudyPeriod> ssps, Long teacherId) {
        long sum = 0;
        for (SubjectStudyPeriod ssp : ssps) {
            if (Boolean.TRUE.equals(ssp.getCapacityDiff())) {
                List<SubjectStudyPeriodTeacher> teacherSsps = StreamUtil
                        .toFilteredList(t -> EntityUtil.getId(t.getTeacher()).equals(teacherId), ssp.getTeachers());
                for (SubjectStudyPeriodTeacher teacherSsp : teacherSsps) {
                    sum += teacherSsp.getCapacities().stream().mapToLong(SubjectStudyPeriodTeacherCapacity::getHours)
                            .sum();
                }
            } else {
                sum += ssp.getCapacities().stream().mapToLong(SubjectStudyPeriodCapacity::getHours).sum();
            }
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
    
    /**
     * Check if subgroups in subject study period can be modified or subgroup can be changed for student.
     * 
     * @param subjectStudyPeriod - subject study period
     * @return true if can be edited
     */
    public static boolean canEditSubgroups(SubjectStudyPeriod subjectStudyPeriod) {
        return !subjectStudyPeriod.getStudyPeriod().getEndDate().isBefore(LocalDate.now());
    }
}
