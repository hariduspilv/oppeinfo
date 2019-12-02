package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class CommitteeUserRights {

    public static boolean canSearch(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KOMISJON);
    }

    public static boolean canView(HoisUserDetails user, Committee committee) {
        School school = committee.getSchool();
        return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school) || UserUtil.isTeacher(user, school))
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KOMISJON);
    }

    public static boolean canCreate(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KOMISJON);
    }

    public static boolean canEdit(HoisUserDetails user, Committee committee) {
        return (UserUtil.isSchoolAdmin(user, committee.getSchool()) || UserUtil.isTeacher(user, committee.getSchool()))
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KOMISJON);
    }

    public static boolean canDelete(HoisUserDetails user, Committee committee) {
        return canEdit(user, committee);
    }

}
