package ee.hitsa.ois.util;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
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
    
    public static boolean hasPermissionToChange(HoisUserDetails user, Journal journal) {
        return (UserUtil.isSchoolAdmin(user, journal.getSchool()) || 
                UserUtil.isTeacher(user, journal.getSchool()) && !confirmed(journal)) &&
                UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PAEVIK);
    }

    public static boolean hasPermissionToConfirm(HoisUserDetails user, School school) {
        return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isTeacher(user, school)) &&
                UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PAEVIK);
    }
    
    public static boolean hasFinalEntry(Journal journal) {
        return journal.getJournalEntries().stream()
                .anyMatch(je -> ClassifierUtil
                        .equals(JournalEntryType.SISSEKANNE_L, je.getEntryType()));
    }
    
    public static boolean canConfirm(HoisUserDetails user, Journal journal) {
        return hasPermissionToConfirm(user, journal.getSchool()) && !confirmed(journal);
    }
    
    /*
     * Teacher cannot unconfirm, that is why check if user is admin repeated
     */
    public static boolean canUnconfirm(HoisUserDetails user, Journal journal) {
        return hasPermissionToConfirm(user, journal.getSchool()) && UserUtil.isSchoolAdmin(user, journal.getSchool())  && 
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
        if(!hasPermissionToChange(user, journal)) {
            return false;
        }
        if(user.isTeacher() && !teacherCanRemoveStudent(journal)) {
            return false;
        }
        return true;
    }
    
    public static boolean teacherCanRemoveStudent(Journal journal) {
        return journal.getJournalEntries().isEmpty();
    }
}
