package ee.hitsa.ois.util;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public class FinalExamProtocolUtil {

    //TODO: could lead to concurrency issues
    public static String generateProtocolNumber(HoisUserDetails user, EntityManager em) {
        Long generatedProtocolNr = null;
        
        Query q = em.createNativeQuery("select MAX(protocol_nr) from protocol p where p.school_id = ?1");
        q.setParameter(1, user.getSchoolId());
        List<?> data = q.getResultList();
        
        if (data == null || data.isEmpty()) {
            generatedProtocolNr = Long.valueOf(1);
        } else {
            Long previousProtocolNr = Long.valueOf(resultAsString(q.getSingleResult(), 0).substring(2));
            int previousNr = previousProtocolNr.intValue();
            generatedProtocolNr = Long.valueOf(++previousNr);
        }
        
        return DateUtils.shortYear(LocalDate.now()) + String.format("%04d", generatedProtocolNr);
    }

    public static boolean canEdit(HoisUserDetails user, Protocol protocol) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPMOODULPROTOKOLL)) {
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
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPMOODULPROTOKOLL)) {
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
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPMOODULPROTOKOLL)) {
            return false;
        }
        return user.isSchoolAdmin() || user.isTeacher();
    }

    public static void assertCanCreateHigherProtocol(HoisUserDetails user) {
        if(canCreateHigherProtocol(user)) {
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
