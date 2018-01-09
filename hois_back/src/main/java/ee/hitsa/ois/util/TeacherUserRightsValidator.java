package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public final class TeacherUserRightsValidator {
    
    private TeacherUserRightsValidator() {
    }
    
    public static void assertCanView(HoisUserDetails user, Teacher teacher) {
        if(!TeacherUserRights.canView(user, teacher)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanSearch(HoisUserDetails user) {
        if(!TeacherUserRights.hasPermissionToView(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanCreate(HoisUserDetails user) {
        if(!TeacherUserRights.hasPermissionToEdit(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanEdit(HoisUserDetails user, Teacher teacher) {
        if(!TeacherUserRights.canEdit(user, teacher)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanConfirm(HoisUserDetails user, Teacher teacher) {
        if(!TeacherUserRights.canConfirm(user, teacher)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanConfirm(HoisUserDetails user) {
        if(!TeacherUserRights.canConfirm(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

}
