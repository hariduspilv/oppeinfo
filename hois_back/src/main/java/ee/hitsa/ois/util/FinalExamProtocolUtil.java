package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public class FinalExamProtocolUtil {
    
    private static boolean hasProtocolEditPermission(HoisUserDetails user, Protocol protocol) {
        PermissionObject object = Boolean.TRUE.equals(protocol.getIsVocational())
                ? PermissionObject.TEEMAOIGUS_LOPMOODULPROTOKOLL
                : PermissionObject.TEEMAOIGUS_LOPPROTOKOLL;
        return UserUtil.hasPermission(user, Permission.OIGUS_M, object);
    }

    public static boolean canEdit(HoisUserDetails user, Protocol protocol) {
        if(!hasProtocolEditPermission(user, protocol)) {
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

    public static boolean canEdit(HoisUserDetails user, Protocol protocol, Long teacherResponsible) {
        if(!hasProtocolEditPermission(user, protocol)) {
            return false;
        }
        if(user.isSchoolAdmin()) {
            return true;
        }
        if(user.isTeacher()) {
            return ProtocolStatus.PROTOKOLL_STAATUS_S.name().equals(EntityUtil.getCode(protocol.getStatus())) && user.getTeacherId().equals(teacherResponsible);
        }
        return false;
    }

    /**
     * Student cannot be deleted from the protocol, if he is exmatriculated and has some result
     */
    public static boolean studentCanBeDeleted(ProtocolStudent ps) {
        if(StudentUtil.hasFinished(ps.getStudent()) || StudentUtil.hasQuit(ps.getStudent())) {
            return !hasGrade(ps);
        }
        return true;
    }

    public static boolean hasGrade(ProtocolStudent ps) {
        return ps.getGrade() != null;
    }

    public static boolean canDelete(HoisUserDetails user, Protocol protocol) {
        if(!hasProtocolEditPermission(user, protocol)) {
            return false;
        }
        
        return !ProtocolUtil.confirmed(protocol) && allResultsEmpty(protocol) && 
                (user.isSchoolAdmin() || isTeacherResponsible(user, protocol));
    }

    private static boolean isTeacherResponsible(HoisUserDetails user, Protocol protocol) {
        return UserUtil.isTeacher(user, protocol.getSchool()) && 
                EntityUtil.getId(protocol.getProtocolVdata().getTeacher()).equals(user.getTeacherId());
    }

    private static boolean allResultsEmpty(Protocol protocol) {
        return protocol.getProtocolStudents().stream().allMatch(ps -> ps.getGrade() == null);
    }

    public static boolean canCreateHigherProtocol(HoisUserDetails user) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPPROTOKOLL)) {
            return false;
        }
        return user.isSchoolAdmin() || user.isTeacher();
    }

    public static void assertCanCreateHigherProtocol(HoisUserDetails user) {
        if(!canCreateHigherProtocol(user)) {
            throw new ValidationFailedException("finalExamProtocol.error.noPermissionToCreate");
        }
    }

    public static void assertIsSchoolAdminOrTeacherResponsible(HoisUserDetails user, Long teacherId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        if(user.isTeacher() && !user.getTeacherId().equals(teacherId)) {
            throw new ValidationFailedException("finalExamProtocol.error.teacherMismatch");
        }
    }

    public static void assertCanEdit(HoisUserDetails user, Protocol protocol) {
        if(!canEdit(user, protocol)) {
            throw new ValidationFailedException("finalExamProtocol.error.noPermissionToEdit");
        }
    }

    public static void assertCanDelete(HoisUserDetails user, Protocol protocol) {
        if(!canDelete(user, protocol)) {
            throw new ValidationFailedException("finalExamProtocol.error.noPermissionToDelete");
        }
    }
}
