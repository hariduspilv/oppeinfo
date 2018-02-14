package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class CommitteeUserRights {

    public static boolean canEdit(HoisUserDetails user, Committee committee) {
        return (UserUtil.isSchoolAdmin(user, committee.getSchool()) || UserUtil.isTeacher(user, committee.getSchool()))
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KOMISJON);
    }
    
    public static boolean canDelete(HoisUserDetails user, Committee committee) {
        return canEdit(user, committee);
    }

}
