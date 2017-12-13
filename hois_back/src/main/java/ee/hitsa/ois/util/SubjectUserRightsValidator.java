package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class SubjectUserRightsValidator extends SubjectUserRights {
    
    
    public static void assertCanView(HoisUserDetails user, Subject subject) {
        if(!canView(user, subject)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanSearch(HoisUserDetails user) {
        if(!canSearch(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanCreate(HoisUserDetails user) {
        if(!hasPermissionToEdit(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanEdit(HoisUserDetails user, Subject subject) {
        if(!canEdit(user, subject)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanDelete(HoisUserDetails user, Subject subject) {
        if(!canDelete(user, subject)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanSetActive(HoisUserDetails user, Subject subject) {
        if(!canSetActive(user, subject)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanSetPassive(HoisUserDetails user, Subject subject) {
        if(!canSetPassive(user, subject)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
}
