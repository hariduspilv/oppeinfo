package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

public final class TeacherUserRights {
    
    private TeacherUserRights() {
    }
    
    public static boolean hasPermissionToView(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isExternalExpert() || user.isTeacher()) && 
                UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPETAJA);
    }
    
    public static boolean hasPermissionToEdit(HoisUserDetails user) {
        return user.isSchoolAdmin() && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPETAJA);
    }
    
    public static boolean canView(HoisUserDetails user, Teacher teacher) {
        if(user.isExternalExpert()) {
            return hasPermissionToView(user);
        }
        return hasPermissionToView(user) && UserUtil.isSameSchool(user, teacher.getSchool());
    }
    
    public static boolean canEdit(HoisUserDetails user, Teacher teacher) {
        return hasPermissionToEdit(user) && UserUtil.isSameSchool(user, teacher.getSchool());
    }
    
    public static boolean canConfirm(HoisUserDetails user, Teacher teacher) {
        return canConfirm(user) && UserUtil.isSameSchool(user, teacher.getSchool());
    }
    
    public static boolean canConfirm(HoisUserDetails user) {
        return user.isSchoolAdmin() && 
                UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_OPETAJA);
    }


}
