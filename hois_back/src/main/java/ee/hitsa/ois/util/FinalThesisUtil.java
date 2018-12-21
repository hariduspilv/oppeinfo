package ee.hitsa.ois.util;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.FinalThesisSupervisor;
import ee.hitsa.ois.enums.FinalThesisStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class FinalThesisUtil {

    public static boolean confirmed(FinalThesis finalThesis) {
        return ClassifierUtil.equals(FinalThesisStatus.LOPUTOO_STAATUS_K, finalThesis.getStatus());
    }
    
    private static boolean hasFinalThesisViewPermission(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTEEMA);
    }
    
    private static boolean hasFinalThesisEditPermission(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPTEEMA);
    }
    
    private static boolean hasFinalThesisConfirmPermission(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_LOPTEEMA);
    }
    
    private static boolean isSupervisor(HoisUserDetails user, FinalThesis finalThesis) {
        List<Long> supervisors = finalThesis.getSupervisors().stream().filter(s -> s.getTeacher() != null)
                .map(s -> EntityUtil.getId(s.getTeacher())).collect(Collectors.toList());
        return supervisors.contains(user.getTeacherId());
    }
    
    public static boolean canView(HoisUserDetails user, FinalThesis finalThesis) {
        if (!hasFinalThesisViewPermission(user)) {
            return false;
        }
        if (UserUtil.isSchoolAdmin(user, finalThesis.getStudent().getSchool()) || isSupervisor(user, finalThesis)) {
            return true;
        } else if (user.isStudent()) {
            return UserUtil.isStudent(user, finalThesis.getStudent());
        }
        
        return false;
    }
    
    public static boolean canCreate(HoisUserDetails user) {
        if (user.isSchoolAdmin()) {
            return hasFinalThesisEditPermission(user);
        } else if (user.isStudent()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean canEdit(HoisUserDetails user, FinalThesis finalThesis) {
        if (user.isSchoolAdmin() || isSupervisor(user, finalThesis)) {
            if (confirmed(finalThesis)) {
                return hasFinalThesisConfirmPermission(user);
            }
            return hasFinalThesisEditPermission(user);
        } else if (user.isStudent()) {
            return !confirmed(finalThesis);
        }
        return false;
    }
    
    public static boolean canConfirm(HoisUserDetails user, FinalThesis finalThesis) {
        if (confirmed(finalThesis) || !hasFinalThesisConfirmPermission(user)) {
            return false;
        }
        return user.isSchoolAdmin() || isSupervisor(user, finalThesis);
    }
    
    public static void assertCanCreate(HoisUserDetails user) {
        if (!canCreate(user)) {
            throw new ValidationFailedException("finalThesis.error.noPermissionToCreate");
        }
    }
    
    public static void assertCanView(HoisUserDetails user, FinalThesis finalThesis) {
        if (!canView(user, finalThesis)) {
            throw new ValidationFailedException("finalThesis.error.noPermissionToView");
        }
    }
    
    public static void assertCanEdit(HoisUserDetails user, FinalThesis finalThesis) {
        if (!canEdit(user, finalThesis)) {
            throw new ValidationFailedException("finalThesis.error.noPermissionToEdit");
        }
    }
    
    public static void assertCanConfirm(HoisUserDetails user, FinalThesis finalThesis) {
        if (!canConfirm(user, finalThesis)) {
            throw new ValidationFailedException("finalThesis.error.noPermissionToConfirm");
        }
    }
    
    public static void hasRequiredSupervisors(FinalThesis finalThesis) {
        List<FinalThesisSupervisor> supervisors = finalThesis.getSupervisors();
        FinalThesisSupervisor primarySupervisor = supervisors.stream()
                .filter(s -> Boolean.TRUE.equals(s.getIsPrimary())).findFirst().orElse(null);
        if (primarySupervisor == null) {
            throw new ValidationFailedException("finalThesis.error.primarySupervisorRequired");
        }
        
        if (Boolean.FALSE.equals(finalThesis.getStudent().getCurriculumVersion().getCurriculum().getHigher())) {
            long inSchoolSupervisors = supervisors.stream().filter(s -> Boolean.FALSE.equals(s.getIsExternal())).count();
            if (inSchoolSupervisors == 0) {
                throw new ValidationFailedException("finalThesis.error.inSchoolSupervisorRequired");
            }
        }
    }
}
