package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;

public abstract class JournalUtil {

    public static boolean confirmed(Journal journal) {
        return ClassifierUtil.equals(JournalStatus.PAEVIK_STAATUS_K, journal.getStatus());
    }

    public static boolean hasPermissionToView(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isTeacher()) &&
                UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PAEVIK);
    }

    public static boolean hasPermissionToView(HoisUserDetails user, School school) {
        return hasPermissionToView(user) && UserUtil.isSameSchool(user, school);
    }
    
    public static boolean teacherIsJournalFiller(HoisUserDetails user, Journal journal) {
        Map<Long, JournalTeacher> teachers = StreamUtil.toMap(jt -> EntityUtil.getId(jt.getTeacher()), journal.getJournalTeachers());
        
        if (teachers != null) {
            JournalTeacher teacher = teachers.get(user.getTeacherId());
            if (teacher != null && teacher.getIsFiller().booleanValue()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean teacherIsJournalConfirmer(HoisUserDetails user, Journal journal) {
        Map<Long, JournalTeacher> teachers = StreamUtil.toMap(jt -> EntityUtil.getId(jt.getTeacher()), journal.getJournalTeachers());
        
        if (teachers != null) {
            JournalTeacher teacher = teachers.get(user.getTeacherId());
            if (teacher != null && teacher.getIsConfirmer().booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermissionToChange(HoisUserDetails user, Journal journal) {
        return (UserUtil.isSchoolAdmin(user, journal.getSchool()) || 
                UserUtil.isTeacher(user, journal.getSchool()) && !confirmed(journal) && teacherIsJournalFiller(user, journal)) &&
                UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PAEVIK);
    }

    public static boolean hasPermissionToReview(HoisUserDetails user, Journal journal) {
        return UserUtil.isSchoolAdmin(user, journal.getSchool()) &&
                UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PAEVIKYLE);
    }

    public static boolean hasPermissionToConfirm(HoisUserDetails user, Journal journal) {
        return (UserUtil.isSchoolAdmin(user, journal.getSchool()) || 
                UserUtil.isTeacher(user, journal.getSchool()) && teacherIsJournalConfirmer(user, journal)) &&
                UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PAEVIK);
    }

    public static boolean hasFinalEntry(Journal journal) {
        return journal.getJournalEntries().stream()
                .anyMatch(je -> ClassifierUtil
                        .equals(JournalEntryType.SISSEKANNE_L, je.getEntryType()));
    }

    public static boolean canConfirm(HoisUserDetails user, Journal journal) {
        return hasPermissionToConfirm(user, journal) && !confirmed(journal);
    }

    /**
     * Teacher cannot unconfirm, that is why check if user is admin repeated
     */
    public static boolean canUnconfirm(HoisUserDetails user, Journal journal) {
        return hasPermissionToConfirm(user, journal) && UserUtil.isSchoolAdmin(user, journal.getSchool())  && 
                confirmed(journal);
    }

    public static boolean filterJournalEntryStudentsByOccupationalModule(CurriculumVersionOccupationModule curriculumVersionOccupationModule, JournalEntryStudent jes) {
        Long omodule = EntityUtil.getId(curriculumVersionOccupationModule);
        return jes.getJournalEntry().getJournal().getJournalOccupationModuleThemes().stream()
        .anyMatch(t -> EntityUtil.getId(t.getCurriculumVersionOccupationModuleTheme().getModule()).equals(omodule));
    }

    /**
     * @return students who are studying and have no final result
     */
    public static List<JournalStudentDto> withoutFinalResult(Journal journal) {
        List<JournalStudent> result = journal.getJournalStudents().stream()
                .filter(js -> StudentUtil.isStudying(js.getStudent()))
                .filter(js -> js.getJournalEntryStudents().stream()
                       .filter(jes -> jes.getGrade() != null)
                       .allMatch(jes -> !ClassifierUtil
                       .equals(JournalEntryType.SISSEKANNE_L, jes.getJournalEntry().getEntryType())))
                .collect(Collectors.toList());
        return StreamUtil.toMappedList(JournalStudentDto::of, result);
    }

    public static boolean canRemoveStudent(HoisUserDetails user, Journal journal) {
        return UserUtil.isSchoolAdmin(user, journal.getSchool()) && hasPermissionToChange(user, journal);
    }

    /**
     * Show "confirm all" button for school administrator 2 weeks before the end of study year
     *
     * @param user
     * @param studyYear
     * @return
     */
    public static boolean canConfirmAll(HoisUserDetails user, StudyYear studyYear) {
        return studyYear != null && user.isSchoolAdmin() && UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PAEVIK) &&
                LocalDate.now().plusWeeks(2).isAfter(studyYear.getEndDate());
    }

    public static void assertCanView(HoisUserDetails user) {
        if(!hasPermissionToView(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanView(HoisUserDetails user, Journal journal) {
        if(!hasPermissionToView(user, journal.getSchool())) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanChange(HoisUserDetails user, Journal journal) {
        if(!hasPermissionToChange(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanConfirm(HoisUserDetails user, Journal journal) {
        if(!canConfirm(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanUnconfirm(HoisUserDetails user, Journal journal) {
        if(!canUnconfirm(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanReview(HoisUserDetails user, Journal journal) {
        if (!hasPermissionToReview(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanRemoveStudent(HoisUserDetails user, Journal journal) {
        if (!canRemoveStudent(user, journal)) {
            throw new ValidationFailedException("journal.messages.removingStudentIsNotAllowed");
        }
    }

    public static void assertCanAddStudent(HoisUserDetails user, Journal journal) {
        if (!hasPermissionToChange(user, journal)) {
            throw new ValidationFailedException("journal.messages.addingStudentIsNotAllowed");
        }
    }

    public static void assertCanConfirmAll(HoisUserDetails user, StudyYear studyYear) {
        if(!canConfirmAll(user, studyYear)) {
            throw new ValidationFailedException("journal.messages.confirmAllNotAllowed");
        }
    }
}
