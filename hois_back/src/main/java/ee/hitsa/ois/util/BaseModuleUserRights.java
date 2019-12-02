package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class BaseModuleUserRights {

    private static boolean canCreate(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher()) && canEditBaseModule(user);
    }

    public static void assertCanCreate(HoisUserDetails user) {
        if (!canCreate(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean canEdit(HoisUserDetails user, School school) {
        return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school))
                && canEditBaseModule(user);
    }

    public static void assertCanEdit(HoisUserDetails user, School school) {
        if (!canEdit(user, school)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canView(HoisUserDetails user, School school) {
        return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school))
                && canViewBaseModule(user);
    }

    public static void assertCanView(HoisUserDetails user, School school) {
        if (!canView(user, school)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanDelete(HoisUserDetails user, BaseModule module, School school) {
        if (!(UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school))) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
        if (!canDeleteBaseModule(user, module)) {
            throw new ValidationFailedException("basemodule.error.hasconnectedcurriculums");
        }
    }

    private static boolean canSearch(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher()) && canViewBaseModule(user);
    }

    public static void assertCanSearch(HoisUserDetails user) {
        if (!canSearch(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canGenerate(HoisUserDetails user, CurriculumModule cModule) {
        return canCreate(user) && cModule != null && cModule.getBaseModule() == null;
    }

    public static void assertCanGenerate(HoisUserDetails user, CurriculumModule cModule) {
        if (!canGenerate(user, cModule)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canRelease(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPEKAVA);
    }

    public static void assertCanRelease(HoisUserDetails user) {
        if (!canRelease(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canReplace(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPEKAVA);
    }

    public static void assertCanReplace(HoisUserDetails user) {
        if (!canReplace(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canGetReplaceForm(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPPEKAVA);
    }

    public static void assertCanGetReplaceForm(HoisUserDetails user) {
        if (!canGetReplaceForm(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canDeleteTheme(HoisUserDetails user, School school) {
        return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school))
                && canEditBaseModule(user);
    }

    public static void assertCanDeleteTheme(HoisUserDetails user, School school) {
        if (!canDeleteTheme(user, school)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean canViewBaseModule(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_BAASMOODUL);
    }

    private static boolean canEditBaseModule(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_BAASMOODUL);
    }

    private static boolean canDeleteBaseModule(HoisUserDetails user, BaseModule module) {
        if (UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_BAASMOODUL)
                && module.getCurriculumModules().isEmpty()) {
            return true;
        }
        return false;
    }

}
