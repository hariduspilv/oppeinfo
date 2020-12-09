package ee.hitsa.ois.util;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationCommittee;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

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
            return true;
        }
        return false;
    }

    public static boolean canCreate(HoisUserDetails user) {
        return user.isStudent() || ((user.isSchoolAdmin() || user.isLeadingTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA));
    }

    public static boolean canEdit(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        if (UserUtil.isStudent(user, application.getStudent())) {
            return ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status);
        } else if (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status)
                || ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent());
        }
        return false;
    }
    
    public static boolean canComment(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        if (UserUtil.isStudent(user, application.getStudent())) {
            return ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status);
        } else if (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status)
                || ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent());
        } else if (ApelApplicationStatus.VOTA_STAATUS_V.name().equals(status)) {
            return ((UserUtil.isTeacher(user, application.getSchool()) || UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent())) 
            		&& isCommitteeMember(user, application))
            		|| (UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent()));
        }
        return false;
    }

    public static boolean canEdit(HoisUserDetails user, String status, Long curriculumId) {
        if (user.isStudent()) {
            return ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status);
        } else if (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status)
                || ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && (user.isSchoolAdmin() || UserUtil.isLeadingTeacher(user, curriculumId));
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
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(EntityUtil.getCode(application.getStatus()))) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent());
        }
        return canReview(user, application);
    }

    public static boolean hasRightsToExtendNominalDuration(HoisUserDetails user, ApelApplication application) {
        if (!Boolean.TRUE.equals(application.getIsEhisSent())) {
            if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(EntityUtil.getCode(application.getStatus()))) {
                return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                        && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent());
            }
            return canReview(user, application);
        }
        return false;
    }

    public static boolean canSubmit(HoisUserDetails user, ApelApplication application) {
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(EntityUtil.getCode(application.getStatus()))) {
            if (UserUtil.isSchoolAdminOrLeadingTeacher(user, student)) {
                return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
            }
            return UserUtil.isStudent(user, student);
        }
        return false;
    }
    
    public static boolean canAdminOrChairmanChangeWhileBeingConfirmed(HoisUserDetails user, ApelApplication application) {
        if (ApelApplicationStatus.VOTA_STAATUS_V.name().equals(EntityUtil.getCode(application.getStatus()))
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTAKOM)) {
            if (UserUtil.isSchoolAdmin(user, application.getSchool())) {
                return true;
            } else if (UserUtil.isTeacher(user, application.getSchool())
                    || UserUtil.isLeadingTeacher(user, application.getSchool())) {
                return isCommitteeChairman(user, application);
            }
        }
        return false;
    }

    public static boolean canSendToConfirm(HoisUserDetails user, ApelApplication application) {
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(EntityUtil.getCode(application.getStatus()))) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent());
        }
        return canAdminOrChairmanChangeWhileBeingConfirmed(user, application);
    }

    public static boolean canSendToCommittee(HoisUserDetails user, ApelApplication application) {
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(EntityUtil.getCode(application.getStatus()))) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent());
        }
        return false;
    }

    public static boolean canSendBackToCreation(HoisUserDetails user, ApelApplication application) {
        /** Same rights as 'canSendToConfirm */
        return canSendToConfirm(user, application);
    }

    public static boolean canConfirm(HoisUserDetails user, ApelApplication application) {
        if (ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(EntityUtil.getCode(application.getStatus()))) {
            return UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdmin(user, application.getSchool());
        }
        return false;
    }

    public static boolean canRemoveConfirmation(HoisUserDetails user, ApelApplication application) {
        if (ApelApplicationStatus.VOTA_STAATUS_C.name().equals(EntityUtil.getCode(application.getStatus()))) {
            return UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdmin(user, application.getSchool());
        }
        return false;
    }

    public static boolean canSendBack(HoisUserDetails user, ApelApplication application) {
        if (ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(EntityUtil.getCode(application.getStatus()))) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent());
        }
        return false;
    }

    public static boolean canReject(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)
                || ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(status)) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA)
                    && UserUtil.isSchoolAdminOrLeadingTeacher(user, application.getStudent());
        }
        return canAdminOrChairmanChangeWhileBeingConfirmed(user, application);
    }

    public static boolean isCommitteeMember(HoisUserDetails user, ApelApplication application) {
        List<ApelApplicationCommittee> committees = application.getCommittees();
        if (committees == null) {
            return false;
        }
        List<Long> members = committees.stream()
                .map(p -> p.getCommittee())
                .flatMap(p -> p.getMembers().stream())
                .map(p -> EntityUtil.getId(p.getPerson())).collect(Collectors.toList());
        return members.contains(user.getPersonId());
    }
    
    public static boolean isCommitteeChairman(HoisUserDetails user, ApelApplication application) {
        List<ApelApplicationCommittee> committees = application.getCommittees();
        if (committees == null) {
            return false;
        }
        List<Long> members = committees.stream()
            .map(p -> p.getCommittee())
            .flatMap(p -> p.getMembers().stream())
            .filter(p -> Boolean.TRUE.equals(p.getIsChairman()))
            .map(p -> EntityUtil.getId(p.getPerson())).collect(Collectors.toList());
        return members.contains(user.getPersonId());
    }

}
