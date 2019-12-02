package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.GeneralMessage;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.GeneralMessageForm;

public abstract class GeneralMessageUserRights {

    private static boolean canSearch(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_YLDTEADE);
    }

    public static void assertCanSearch(HoisUserDetails user) {
        if (!canSearch(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canView(HoisUserDetails user, GeneralMessage generalMessage) {
        School school = generalMessage.getSchool();
        if (school != null) {
            return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school))
                    && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_YLDTEADE);
        }
        return user.isMainAdmin();
    }

    public static void assertCanView(HoisUserDetails user, GeneralMessage generalMessage) {
        if (!canView(user, generalMessage)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canCreate(HoisUserDetails user, GeneralMessageForm form) {
        if (user.isSchoolAdmin() || user.isLeadingTeacher()) {
            assertTargets(form);
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_YLDTEADE);
        }
        return user.isMainAdmin();
    }

    public static void assertCanCreate(HoisUserDetails user, GeneralMessageForm form) {
        if (!canCreate(user, form)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canEdit(HoisUserDetails user, GeneralMessage generalMessage, GeneralMessageForm form) {
        School school = generalMessage.getSchool();
        if (school != null) {
            assertTargets(form);
            return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school))
                    && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_YLDTEADE);
        }
        return user.isMainAdmin();
    }

    public static void assertCanEdit(HoisUserDetails user, GeneralMessage generalMessage, GeneralMessageForm form) {
        if (!canEdit(user, generalMessage, form)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static void assertTargets(GeneralMessageForm form) {
        // Main admin created site messages have no targets. 
        // Therefore there needs to be a manual check for school general messages.
        UserUtil.throwAccessDeniedIf(form.getTargets() == null || form.getTargets().isEmpty(), "required targets");
    }

    private static boolean canDelete(HoisUserDetails user, GeneralMessage generalMessage) {
        School school = generalMessage.getSchool();
        if (school != null) {
            return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school))
                    && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_YLDTEADE);
        }
        return user.isMainAdmin();
    }

    public static void assertCanDelete(HoisUserDetails user, GeneralMessage generalMessage) {
        if (!canDelete(user, generalMessage)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

}
