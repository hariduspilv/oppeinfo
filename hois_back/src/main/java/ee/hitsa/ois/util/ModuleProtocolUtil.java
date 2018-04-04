package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class ModuleProtocolUtil {

    public static boolean canEdit(HoisUserDetails user, Protocol protocol) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_MOODULPROTOKOLL)) {
            return false;
        }
        if(UserUtil.isSchoolAdmin(user, protocol.getSchool())) {
            return true;
        }
        if(user.isTeacher()) {
            return !ProtocolUtil.confirmed(protocol) && isTeacherResponsible(user, protocol);
        }
        return false;
    }

    public static boolean canEdit(HoisUserDetails user, ProtocolStatus status, Long teacherResponsible) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_MOODULPROTOKOLL)) {
            return false;
        }
        if(user.isSchoolAdmin()) {
            return true;
        }
        if(user.isTeacher()) {
            return ProtocolStatus.PROTOKOLL_STAATUS_S.equals(status) && user.getTeacherId().equals(teacherResponsible);
        }
        return false;
    }

    public static boolean canDelete(HoisUserDetails user, Protocol protocol) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_MOODULPROTOKOLL)) {
            return false;
        }
        return !ProtocolUtil.confirmed(protocol) && allResultsEmpty(protocol) && 
                (user.isSchoolAdmin() || isTeacherResponsible(user, protocol));
    }

    public static boolean canConfirm(HoisUserDetails user, Protocol protocol) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_MOODULPROTOKOLL)) {
            return false;
        }
        if (UserUtil.isSchoolAdmin(user, protocol.getSchool())) {
            return true;
        }
        if (user.isTeacher()) {
            return !ProtocolUtil.confirmed(protocol) && isTeacherResponsible(user, protocol);
        }
        return false;
    }

    public static void assertIsSchoolAdminOrTeacherResponsible(HoisUserDetails user, Long teacherId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        if(user.isTeacher() && !user.getTeacherId().equals(teacherId)) {
            throw new ValidationFailedException("moduleProtocol.error.teacherMismatch");
        }
    }

    public static void assertCanEdit(HoisUserDetails user, Protocol protocol) {
        if(!canEdit(user, protocol)) {
            throw new ValidationFailedException("moduleProtocol.error.noPermissionToEdit");
        }
    }

    public static void assertCanDelete(HoisUserDetails user, Protocol protocol) {
        if(!canDelete(user, protocol)) {
            throw new ValidationFailedException("moduleProtocol.error.noPermissionToDelete");
        }
    }

    public static void assertCanConfirm(HoisUserDetails user, Protocol protocol) {
        UserUtil.throwAccessDeniedIf(!canConfirm(user, protocol), "User has no confirm rights");
    }

    private static boolean isTeacherResponsible(HoisUserDetails user, Protocol protocol) {
        return UserUtil.isTeacher(user, protocol.getSchool()) && 
                EntityUtil.getId(protocol.getProtocolVdata().getTeacher()).equals(user.getTeacherId());
    }

    private static boolean allResultsEmpty(Protocol protocol) {
        return protocol.getProtocolStudents().stream().allMatch(ps -> ps.getGrade() == null);
    }
}
