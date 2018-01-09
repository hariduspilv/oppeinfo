package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;

public final class PracticeJournalUserRights {
    
    private PracticeJournalUserRights(){
    }
    
    private static final int DAYS_AFTER_END_CAN_EDIT = 30;
    
    public static boolean canEdit(HoisUserDetails user, LocalDate endDate) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PRAKTIKAPAEVIK)){
            return false;
        }
        return LocalDate.now().isBefore(endDate.plusDays(DAYS_AFTER_END_CAN_EDIT));
    }
    
    public static boolean canAddEntries(HoisUserDetails user, PracticeJournalSearchDto dto) {
        if(!canEdit(user, dto.getEndDate())) {
            return false;
        } else if(user.isTeacher()) {
            return Boolean.TRUE.equals(dto.getCanTeacherAddEntries());
        } else if(user.isStudent()) {
            return Boolean.TRUE.equals(dto.getCanStudentAddEntries());
        } else if(user.isSchoolAdmin()) {
            return true;
        }
        return false;
    }
}
