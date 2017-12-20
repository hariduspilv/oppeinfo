package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class TeacherUserRightsValidator extends TeacherUserRights {
    
    public static void assertCanView(HoisUserDetails user, Teacher teacher) {
        if(!canView(user, teacher)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

}
