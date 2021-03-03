package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public class FinalProtocolUtil {

    private static final String PROFESSIONAL_DIPLOMA_STUDY_LEVEL = "OPPEASTE_514";
    private static final LocalDate PROFESSIONAL_DIPLOMA_CURRICULUM_GRADE_ALLOWED_AFTER = LocalDate.of(2019, 8, 1);

    private static boolean hasProtocolViewPermission(HoisUserDetails user, Protocol protocol) {
        return UserUtil.hasPermission(user, Permission.OIGUS_V, protocolPermissionObject(protocol));
    }

    private static boolean hasProtocolEditPermission(HoisUserDetails user, Protocol protocol) {
        return UserUtil.hasPermission(user, Permission.OIGUS_M, protocolPermissionObject(protocol));
    }

    private static boolean hasProtocolConfirmPermission(HoisUserDetails user, Protocol protocol) {
        return UserUtil.hasPermission(user, Permission.OIGUS_K, protocolPermissionObject(protocol));
    }

    private static PermissionObject protocolPermissionObject(Protocol protocol) {
        return Boolean.TRUE.equals(protocol.getIsVocational()) ? PermissionObject.TEEMAOIGUS_LOPMOODULPROTOKOLL
                : PermissionObject.TEEMAOIGUS_LOPPROTOKOLL;
    }

    private static boolean canSearchVocational(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPMOODULPROTOKOLL);
    }

    private static boolean canSearchHigher(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPPROTOKOLL);
    }

    private static boolean canView(HoisUserDetails user, Protocol protocol) {
        if (!hasProtocolViewPermission(user, protocol)) {
            return false;
        }
        if (user.isSchoolAdmin() || user.isTeacher()) {
            return UserUtil.isSameSchool(user, protocol.getSchool());
        } else if (user.isLeadingTeacher()) {
            if (Boolean.TRUE.equals(protocol.getIsVocational())) {
                return UserUtil.isLeadingTeacher(user,
                        EntityUtil.getId(protocol.getProtocolVdata().getCurriculumVersion().getCurriculum()));
            }

            if (Boolean.TRUE.equals(protocol.getIsFinalThesis())) {
                return UserUtil.isLeadingTeacher(user, protocol.getProtocolHdata().getFinalSubject());
            }
            return UserUtil.isLeadingTeacher(user, protocol.getProtocolHdata().getSubjectStudyPeriod().getSubject());
        }
        return false;
    }

    public static boolean canEdit(HoisUserDetails user, Protocol protocol) {
        if (!hasProtocolEditPermission(user, protocol)) {
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

    // checking edit rights for search form
    public static boolean canEditVocational(HoisUserDetails user, String protocolStatus, List<Long> teachers) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPMOODULPROTOKOLL)) {
            return false;
        }
        return user.isSchoolAdmin() || (user.isTeacher() && !ProtocolUtil.confirmed(protocolStatus)
                && isTeacherResponsible(user, teachers));
    }

    // checking edit rights for search form
    public static boolean canEditHigher(HoisUserDetails user, String protocolStatus, List<Long> teachers) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPPROTOKOLL)) {
            return false;
        }
        return user.isSchoolAdmin() || (user.isTeacher() && !ProtocolUtil.confirmed(protocolStatus)
                && isTeacherResponsible(user, teachers));
    }

    public static boolean canConfirm(HoisUserDetails user, Protocol protocol) {
        if (!hasProtocolConfirmPermission(user, protocol)) {
            return false;
        }
        if (user.isSchoolAdmin()) {
            return true;
        }
        if (user.isTeacher()) {
            return !ProtocolUtil.confirmed(protocol) && isTeacherResponsible(user, protocol);
        }
        return false;
    }

    /**
     * Student cannot be deleted from the protocol, if he is exmatriculated and
     * has some result
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

    public static boolean studentCanSetCurriculumGrade(ProtocolStudent ps) {
        Curriculum curriculum = ps.getProtocol().getProtocolHdata().getCurriculum();
        if (PROFESSIONAL_DIPLOMA_STUDY_LEVEL.equals(EntityUtil.getCode(curriculum.getOrigStudyLevel()))) {
            LocalDate studyStart = ps.getStudent().getStudyStart();
            return studyStart != null && !studyStart.isBefore(PROFESSIONAL_DIPLOMA_CURRICULUM_GRADE_ALLOWED_AFTER);
        }
        return true;
    }

    public static boolean canDelete(HoisUserDetails user, Protocol protocol) {
        if(!hasProtocolEditPermission(user, protocol)) {
            return false;
        }
        
        return !ProtocolUtil.confirmed(protocol) && allResultsEmpty(protocol) && 
                (user.isSchoolAdmin() || isTeacherResponsible(user, protocol));
    }

    private static boolean isTeacherResponsible(HoisUserDetails user, Protocol protocol) {
        if (Boolean.TRUE.equals(protocol.getIsVocational())) {
            return UserUtil.isTeacher(user, protocol.getSchool())
                    && EntityUtil.getId(protocol.getProtocolVdata().getTeacher()).equals(user.getTeacherId());
        }

        if (ClassifierUtil.equals(ProtocolStatus.PROTOKOLL_STAATUS_S, protocol.getStatus())) {
            return protocol.getProtocolHdata().getSubjectStudyPeriod().getTeachers().stream()
                    .anyMatch(t -> EntityUtil.getId(t.getTeacher()).equals(user.getTeacherId()));
        }
        return protocol.getProtocolHdata().getSubjectStudyPeriod().getTeachers().stream()
                .anyMatch(t -> Boolean.TRUE.equals(t.getIsSignatory())
                        && EntityUtil.getId(t.getTeacher()).equals(user.getTeacherId()));
    }

    private static boolean isTeacherResponsible(HoisUserDetails user, List<Long> teachers) {
        return StreamUtil.nullSafeList(teachers).stream().anyMatch(t -> t.equals(user.getTeacherId()));
    }

    private static boolean allResultsEmpty(Protocol protocol) {
        return protocol.getProtocolStudents().stream().allMatch(ps -> ps.getGrade() == null);
    }

    private static boolean canCreateHigherProtocol(HoisUserDetails user) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPPROTOKOLL)) {
            return false;
        }
        return user.isSchoolAdmin() || user.isTeacher();
    }

    private static boolean canCreateVocationalProtocol(HoisUserDetails user) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPMOODULPROTOKOLL)) {
            return false;
        }
        return user.isSchoolAdmin() || user.isTeacher();
    }

    public static void assertCurriculumGradesInput(Protocol protocol) {
        Curriculum curriculum = protocol.getProtocolHdata().getCurriculum();
        if (!curriculum.getGrades().isEmpty()) {
            for (ProtocolStudent student : protocol.getProtocolStudents()) {
                if (!studentCanSetCurriculumGrade(student)) {
                    if (student.getCurriculumGrade() != null) {
                        throw new ValidationFailedException("finalProtocol.error.curriculumGradeNotAllowedToSet");
                    }
                    continue;
                }

                if (HigherAssessment.isPositive(EntityUtil.getNullableCode(student.getGrade()))
                        && student.getCurriculumGrade() == null) {
                    throw new ValidationFailedException("finalProtocol.error.curriculumGradeRequired");
                } else if (!HigherAssessment.isPositive(EntityUtil.getNullableCode(student.getGrade()))
                        && student.getCurriculumGrade() != null) {
                    throw new ValidationFailedException("finalProtocol.error.curriculumGradeNotAllowed");
                }
            }
        }
    }

    public static void assertCanSearchVocational(HoisUserDetails user) {
        UserUtil.throwAccessDeniedIf(!canSearchVocational(user));
    }

    public static void assertCanSearchHigher(HoisUserDetails user) {
        UserUtil.throwAccessDeniedIf(!canSearchHigher(user));
    }

    public static void assertCanView(HoisUserDetails user, Protocol protocol) {
        UserUtil.throwAccessDeniedIf(!canView(user, protocol), "finalProtocol.error.noPermissionToView");
    }

    public static void assertCanCreateHigherProtocol(HoisUserDetails user) {
        UserUtil.throwAccessDeniedIf(!canCreateHigherProtocol(user), "finalProtocol.error.noPermissionToCreate");
    }

    public static void assertCanCreateVocationalProtocol(HoisUserDetails user) {
        UserUtil.throwAccessDeniedIf(!canCreateVocationalProtocol(user), "finalProtocol.error.noPermissionToCreate");
    }

    public static void assertIsSchoolAdminOrTeacherResponsible(HoisUserDetails user, boolean isHigher, Long teacherId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        UserUtil.throwAccessDeniedIf(user.isTeacher() && !user.getTeacherId().equals(teacherId),
                isHigher ? "finalProtocol.error.teacherMismatchHigher" : "finalProtocol.error.teacherMismatchVocational");
    }

    public static void assertCanEdit(HoisUserDetails user, Protocol protocol) {
        UserUtil.throwAccessDeniedIf(!canEdit(user, protocol), "finalProtocol.error.noPermissionToEdit");
    }

    public static void assertCanConfirm(HoisUserDetails user, Protocol protocol) {
        UserUtil.throwAccessDeniedIf(!canConfirm(user, protocol), "finalProtocol.error.noPermissionToConfirm");
    }

    public static void assertCanDelete(HoisUserDetails user, Protocol protocol) {
        UserUtil.throwAccessDeniedIf(!canDelete(user, protocol), "finalProtocol.error.noPermissionToDelete");
    }
}
