package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public final class ModuleProtocolValidationUtil {
    
    private ModuleProtocolValidationUtil(){
    }

    public static void assertIsSchoolAdminOrTeacherResponsible(HoisUserDetails user, Long teacherId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        if(user.isTeacher() && !user.getTeacherId().equals(teacherId)) {
            throw new ValidationFailedException("moduleProtocol.error.teacherMismatch");
        }
    }

    public static void assertCanEdit(HoisUserDetails user, Protocol protocol) {
        if(!ModuleProtocolUtil.canEdit(user, protocol)) {
            throw new ValidationFailedException("moduleProtocol.error.noPermissionToEdit");
        }
    }

    public static void assertCanDelete(HoisUserDetails user, Protocol protocol) {
        if(!ModuleProtocolUtil.canDelete(user, protocol)) {
            throw new ValidationFailedException("moduleProtocol.error.noPermissionToDelete");
        }
    }
}
