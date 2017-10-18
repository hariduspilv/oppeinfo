package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public class ModuleProtocolValidationUtil extends ModuleProtocolUtil {
    
    public static void assertIsSchoolAdminOrTeacherResponsible(HoisUserDetails user, Long teacherId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        if(user.isTeacher()) {
            AssertionFailedException.throwIf(user.getTeacherId().equals(teacherId), "teacher mismatch!");
        }
    }

    public static void assertCanEdit(HoisUserDetails user, Protocol protocol) {
        if(!canEdit(user, protocol)) {
            throw new ValidationFailedException("no rights to edit protocol");
        }
    }
    
    public static void assertCanDelete(HoisUserDetails user, Protocol protocol) {
        if(!canDelete(user, protocol)) {
            throw new ValidationFailedException("no rights to delete protocol");
        }
    }
}
