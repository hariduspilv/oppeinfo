package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;

public final class PracticeJournalUserRights {

    private static final int DAYS_AFTER_END_CAN_EDIT = 30;

    public static boolean canEdit(HoisUserDetails user, LocalDate endDate) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PRAKTIKAPAEVIK)){
            return false;
        }
        return isBeforeDaysAfterCanEdit(endDate);
    }
    
    public static boolean canConfirm(HoisUserDetails user, LocalDate endDate) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PRAKTIKAPAEVIK)){
            return false;
        }
        return isBeforeDaysAfterCanEdit(endDate);
    }
    
    private static boolean isBeforeDaysAfterCanEdit(LocalDate endDate) {
        return LocalDate.now().isBefore(endDate.plusDays(DAYS_AFTER_END_CAN_EDIT));
    }
    
    private static boolean isConfirmed(String status) {
        return JournalStatus.PAEVIK_STAATUS_K.name().equals(status);
    }
    
    public static boolean canAddEntries(HoisUserDetails user, PracticeJournalSearchDto dto) {
        if(user.isStudent()) {
            return  Boolean.TRUE.equals(dto.getCanStudentAddEntries());
        } else if (user.isSchoolAdmin() || user.isTeacher()) {
            return isBeforeDaysAfterCanEdit(dto.getEndDate());
        }
        return false;
    }
    
    public static boolean canStudentAddEntries(String status, LocalDate endDate, Boolean hasSupervisorOpinion) {
        return !isConfirmed(status) && isBeforeDaysAfterCanEdit(endDate) && Boolean.FALSE.equals(hasSupervisorOpinion);
    }

    public static boolean canDelete(HoisUserDetails user, LocalDate endDate) {
        return canEdit(user, endDate) && !user.isStudent();
    }
    
}
