package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class StateCurriculumUtil {

    /**
     * Checks user rights on state curriculum search form: 
     * presence of edit/new buttons and filtering by status depends on this method. 
     * In addition, it filters out unconfirmed state curricula 
     * if user do not have correspondent role and permission.
     */
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

    /**
     * Checks user rights when opening state curriculum form
     */
    public static boolean canView(HoisUserDetails user, StateCurriculum sc) {
        return ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, sc.getStatus()) || hasPermissionToView(user);
    }

    public static boolean canCreate(HoisUserDetails user) {
        return hasPermissionToEdit(user);
    }

    public static boolean canChange(HoisUserDetails user, StateCurriculum sc) {
        if(ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_C, sc.getStatus())) {
            return false;
        } else if(ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, sc.getStatus())) {
            return hasPermissionToConfirm(user);
        }
        return hasPermissionToEdit(user);
    }

    /**
     * This method is responsible for showing Change button on search form. 
     * The logic with other canChange() method is different.
     */
    public static boolean canChange(HoisUserDetails user, String status) {
        return CurriculumStatus.OPPEKAVA_STAATUS_S.name().equals(status) && hasPermissionToEdit(user);
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
