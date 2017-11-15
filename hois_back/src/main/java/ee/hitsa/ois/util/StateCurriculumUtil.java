package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

public class StateCurriculumUtil {
    
    public static boolean hasPermissionToView(HoisUserDetails user) {
        return UserUtil.isMainAdminOrExternalExpert(user) && 
                UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_RIIKLIKOPPEKAVA);
    }
    
    private static boolean hasPermissionToEdit(HoisUserDetails user) {
        return UserUtil.isMainAdminOrExternalExpert(user) && 
                UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_RIIKLIKOPPEKAVA);
    }
    
    private static boolean hasPermissionToConfirm(HoisUserDetails user) {
        return UserUtil.isMainAdminOrExternalExpert(user) && 
                UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_RIIKLIKOPPEKAVA);
    }
    
    public static boolean canView(HoisUserDetails user, StateCurriculum sc) {
        return ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, sc.getStatus()) || hasPermissionToView(user);
    }

    public static boolean canCreate(HoisUserDetails user) {
        return hasPermissionToEdit(user);
    }
    
    public static boolean canChange(HoisUserDetails user, StateCurriculum sc) {
        return canChange(user, EntityUtil.getCode(sc.getStatus()));
    }
    
    public static boolean canChange(HoisUserDetails user, String status) {
        return hasPermissionToEdit(user) && 
                (CurriculumStatus.OPPEKAVA_STAATUS_S.name().equals(status) || 
                CurriculumStatus.OPPEKAVA_STAATUS_K.name().equals(status));
    }
    
    public static boolean canDelete(HoisUserDetails user, StateCurriculum sc) {
        return hasPermissionToEdit(user) && ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_S, sc.getStatus());
    }
    
    public static boolean canConfirm(HoisUserDetails user, StateCurriculum sc) {
        return hasPermissionToConfirm(user) && ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_S, sc.getStatus());
    }
    
    /**
     * Only user with permission to confirm can close state curriculum
     */
    public static boolean canClose(HoisUserDetails user, StateCurriculum sc) {
        return hasPermissionToConfirm(user) && !ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_C, sc.getStatus());
    }
}
