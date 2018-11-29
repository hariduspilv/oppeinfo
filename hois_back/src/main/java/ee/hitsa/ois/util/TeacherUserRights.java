package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class TeacherUserRights {

    private static boolean modifyPermission(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPETAJA);
    }

    public static boolean hasPermissionToView(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isExternalExpert() || user.isTeacher()) && 
                UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPETAJA);
    }

    public static boolean hasPermissionToEdit(HoisUserDetails user) {
        return user.isSchoolAdmin() && modifyPermission(user);
    }

    public static boolean canView(HoisUserDetails user, Teacher teacher) {
        return hasPermissionToView(user) && (user.isExternalExpert() || UserUtil.isSameSchool(user, teacher.getSchool()));
    }

    public static boolean canEdit(HoisUserDetails user, Teacher teacher) {
        return hasPermissionToEdit(user) && UserUtil.isSameSchool(user, teacher.getSchool());
    }

    public static boolean canEditAsTeacher(HoisUserDetails user, Teacher teacher) {
        return user.isTeacher() && user.getTeacherId().equals(EntityUtil.getId(teacher)) && modifyPermission(user);
    }

    public static void assertCanView(HoisUserDetails user, Teacher teacher) {
        if(!canView(user, teacher)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanSearch(HoisUserDetails user) {
        if(!hasPermissionToView(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanCreate(HoisUserDetails user) {
        if(!hasPermissionToEdit(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanEdit(HoisUserDetails user, Teacher teacher) {
        if(!canEdit(user, teacher)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanEditAsTeacher(HoisUserDetails user, Teacher teacher) {
        if(!canEditAsTeacher(user, teacher)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

	public static void assertCanTeacherAndAdminEdit(HoisUserDetails user, Teacher teacher) {
		if(!canEdit(user, teacher) && !canEditAsTeacher(user, teacher)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
	}
}
