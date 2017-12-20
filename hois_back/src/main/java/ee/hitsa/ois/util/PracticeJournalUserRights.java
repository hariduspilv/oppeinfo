package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;

public abstract class PracticeJournalUserRights {
    
    private static final int DAYS_AFTER_END_CAN_EDIT = 30;
    
    public static boolean canEdit(LocalDate endDate) {
        return LocalDate.now().isBefore(endDate.plusDays(DAYS_AFTER_END_CAN_EDIT));
    }
    
    public static boolean canAddEntries(HoisUserDetails user, PracticeJournalSearchDto dto) {
        if(!canEdit(dto.getEndDate())) {
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