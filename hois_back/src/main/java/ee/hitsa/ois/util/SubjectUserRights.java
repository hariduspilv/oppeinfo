package ee.hitsa.ois.util;

import java.util.AbstractMap.SimpleEntry;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class SubjectUserRights {

    public static SimpleEntry<Boolean, String> canView(HoisUserDetails user, Subject subject) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_AINE) || !UserUtil.isSameSchool(user, subject.getSchool())) {
            return new SimpleEntry<>(Boolean.FALSE, "main.messages.error.nopermission");
        } else if (user.isStudent() || user.isTeacher() || user.isRepresentative()) {
            return new SimpleEntry<>(Boolean.valueOf(SubjectUtil.isActive(subject)), "subject.message.passiveSubject");
        } else if (user.isSchoolAdmin()) {
            return new SimpleEntry<>(Boolean.TRUE, null);
        }
        return new SimpleEntry<>(Boolean.FALSE, "main.messages.error.nopermission");
    }

    public static boolean canViewAllSubjects(HoisUserDetails user) {
        return user.isSchoolAdmin() && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_AINE);
    }

    public static boolean canSearch(HoisUserDetails user) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_AINE)) {
            return false;
        }
        return user.isSchoolAdmin() || user.isTeacher() || user.isStudent() || user.isRepresentative();
    }

    public static boolean hasPermissionToEdit(HoisUserDetails user) {
        return user.isSchoolAdmin()
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_AINE);
    }

    public static boolean canEdit(HoisUserDetails user, Subject subject) {
        return hasPermissionToEdit(user) && UserUtil.isSameSchool(user, subject.getSchool());
    }

    public static boolean canDelete(HoisUserDetails user, Subject subject) {
        return hasPermissionToEdit(user) && UserUtil.isSameSchool(user, subject.getSchool());
    }

    public static boolean canSetActive(HoisUserDetails user, Subject subject) {
        return UserUtil.isSchoolAdmin(user, subject.getSchool())
                && UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_AINE)
                && !SubjectUtil.isActive(subject);
    }

    public static boolean canSetPassive(HoisUserDetails user, Subject subject) {
        return UserUtil.isSchoolAdmin(user, subject.getSchool())
                && UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_AINE)
                && SubjectUtil.isActive(subject);
    }

    public static void assertCanView(HoisUserDetails user, Subject subject) {
        SimpleEntry<Boolean,String> canView = canView(user, subject);
        if (Boolean.FALSE.equals(canView.getKey())) {
            throw new ValidationFailedException(canView.getValue());
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
