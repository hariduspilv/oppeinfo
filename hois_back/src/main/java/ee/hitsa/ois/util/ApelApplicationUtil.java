package ee.hitsa.ois.util;

import java.util.List;

import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalReplacedSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class ApelApplicationUtil {

    public static boolean canSearch(HoisUserDetails user) {
        if (user.isSchoolAdmin() || user.isLeadingTeacher()) {
            return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTA)
                    || UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTAKOM);
        } else if (user.isTeacher()) {
            return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTAKOM);
        } else if (user.isStudent()) {
            return true;
        }
        return false;
    }

    public static boolean canView(HoisUserDetails user, ApelApplication application) {
        School school = application.getSchool();
        if (UserUtil.isSchoolAdmin(user, school)) {
            return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTA)
                    || (UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTAKOM)
                            && isCommitteeMember(user, application));
        } else if (UserUtil.isLeadingTeacher(user, school)) {
            return (UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isLeadingTeacher(user, application.getStudent()))
                    || (UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTAKOM)
                            && isCommitteeMember(user, application));
        } else if (UserUtil.isTeacher(user, school)) {
            return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTAKOM)
                    && isCommitteeMember(user, application);
        } else if (UserUtil.isStudent(user, application.getStudent())) {
            return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTA);
        }
        return false;
    }

    public static boolean canCreate(HoisUserDetails user) {
        return user.isStudent() || (user.isSchoolAdmin()
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA));
    }

    public static boolean canEdit(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        if (UserUtil.isStudent(user, application.getStudent())) {
            return ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status);
        } else if (UserUtil.isSchoolAdmin(user, application.getSchool())) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status)
                            || ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status));
        }
        return false;
    }

    public static boolean canEdit(HoisUserDetails user, String status) {
        if (user.isStudent()) {
            return ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status);
        } else if (user.isSchoolAdmin()) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status)
                            || ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status));
        }
        return false;
    }

    public static boolean canReview(HoisUserDetails user, ApelApplication application) {
        if (ApelApplicationStatus.VOTA_STAATUS_V.name().equals(EntityUtil.getCode(application.getStatus()))
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTAKOM)) {
            if (UserUtil.isSchoolAdmin(user, application.getSchool())) {
                return true;
            } else if (UserUtil.isTeacher(user, application.getSchool())
                    || UserUtil.isLeadingTeacher(user, application.getSchool())) {
                return isCommitteeMember(user, application);
            }
        }
        return false;
    }

    public static boolean canReview(HoisUserDetails user, String status, List<Long> committeeMembers) {
        if (ApelApplicationStatus.VOTA_STAATUS_V.name().equals(status)
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTAKOM)) {
            if (user.isSchoolAdmin()) {
                return true;
            } else if (user.isTeacher() || user.isLeadingTeacher()) {
                return committeeMembers.contains(user.getPersonId());
            }
        }
        return false;
    }

    public static boolean canCanChangeTransferStatus(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdmin(user, application.getSchool());
        }
        return canReview(user, application);
    }

    public static boolean canSubmit(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status)) {
            if (UserUtil.isSchoolAdmin(user, student.getSchool())) {
                return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
            }
            return UserUtil.isStudent(user, student);
        }
        return false;
    }

    public static boolean canSendToConfirm(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return UserUtil.isSchoolAdmin(user, student.getSchool())
                    && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
        }
        return canReview(user, application);
    }

    public static boolean canSendToCommittee(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return UserUtil.isSchoolAdmin(user, student.getSchool())
                    && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
        }
        return false;
    }

    public static boolean canSendBackToCreation(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return UserUtil.isSchoolAdmin(user, student.getSchool())
                    && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
        }
        return canReview(user, application);
    }

    public static boolean canConfirm(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(status)) {
            return UserUtil.isSchoolAdmin(user, student.getSchool())
                    && UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_VOTA);
        }
        return false;
    }

    public static boolean canRemoveConfirmation(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_C.name().equals(status)) {
            return UserUtil.isSchoolAdmin(user, student.getSchool())
                    && UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_VOTA);
        }
        return false;
    }

    public static boolean canSendBack(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(status)) {
            return UserUtil.isSchoolAdmin(user, student.getSchool())
                    && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
        }
        return false;
    }

    public static boolean canReject(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)
                || ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(status)) {
            return UserUtil.isSchoolAdmin(user, student.getSchool());
        }
        return canReview(user, application);
    }

    public static boolean isCommitteeMember(HoisUserDetails user, ApelApplication application) {
        Committee committee = application.getCommittee();
        if (committee == null) {
            return false;
        }
        List<Long> members = StreamUtil.toMappedList(m -> EntityUtil.getId(m.getPerson()), committee.getMembers());
        return members.contains(user.getPersonId());
    }

    public static void assertFormalReplacedSubject(ApelApplicationFormalReplacedSubjectOrModule replacedSubject) {
        if (replacedSubject.getSubject() == null) {
            throw new ValidationFailedException("apel.error.subjectIsNull");
        }
    }

    public static void assertFormalReplacedModule(ApelApplicationFormalReplacedSubjectOrModule replacedModule) {
        if (replacedModule.getCurriculumVersionOmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        }
    }

    public static void assertFormalSubject(ApelApplicationFormalSubjectOrModule subject) {
        if (Boolean.FALSE.equals(subject.getIsMySchool()) && subject.getApelSchool() == null) {
            throw new ValidationFailedException("apel.error.schoolIsNull");
        } else if (Boolean.TRUE.equals(subject.getIsMySchool()) && subject.getSubject() == null) {
            throw new ValidationFailedException("apel.error.subjectIsNull");
        } else if (Boolean.TRUE.equals(subject.getIsMySchool()) && subject.getCurriculumVersionHmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        } else if (Boolean.FALSE.equals(subject.getIsMySchool()) && subject.getNameEt() == null) {
            throw new ValidationFailedException("apel.error.subjectNameEtIsNull");
        } else if (Boolean.FALSE.equals(subject.getIsMySchool()) && subject.getNameEn() == null) {
            throw new ValidationFailedException("apel.error.subjectNameEnIsNull");
        }
    }

    public static void assertFormalModule(ApelApplicationFormalSubjectOrModule module) {
        if (Boolean.FALSE.equals(module.getIsMySchool()) && module.getApelSchool() == null) {
            throw new ValidationFailedException("apel.error.schoolIsNull");
        } else if (Boolean.TRUE.equals(module.getIsMySchool()) && module.getCurriculumVersionOmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        } else if (Boolean.FALSE.equals(module.getIsMySchool()) && module.getNameEt() == null) {
            throw new ValidationFailedException("apel.error.moduleNameEtIsNull");
        }
    }

    public static void assertInformalSubject(ApelApplicationInformalSubjectOrModule subject) {
        if (subject.getSubject() == null) {
            throw new ValidationFailedException("apel.error.subjectIsNull");
        }
    }

    public static void assertInformalModule(ApelApplicationInformalSubjectOrModule module) {
        if (module.getCurriculumVersionOmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        }
    }
}
