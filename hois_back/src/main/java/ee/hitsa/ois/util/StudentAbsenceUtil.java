package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class StudentAbsenceUtil {

    public static boolean canCreate(HoisUserDetails user, Student student) {
        return UserUtil.isAdultStudent(user, student) || 
                UserUtil.isSchoolAdmin(user, student.getSchool()) || 
                UserUtil.isStudentGroupTeacher(user, student) || 
                UserUtil.isStudentRepresentative(user, student);
    }
    
    public static boolean canEdit(HoisUserDetails user, StudentAbsence absence) {
        Student student = absence.getStudent();
        return !accepted(absence) && 
                (UserUtil.isAdultStudent(user, student) || 
                UserUtil.isSchoolAdmin(user, student.getSchool()) || 
                UserUtil.isStudentGroupTeacher(user, student) || 
                UserUtil.isStudentRepresentative(user, student));
    }
    
    /**
     * Light method for search form. 
     * User do not see other absences on search form anyway, 
     * and more strict check of user rights in back end is done on accepting.
     */
    public static boolean hasPermissionToAccept(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isTeacher()) &&
              UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PUUDUMINE);
    }
    
    public static boolean hasPermissionToSearch(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isTeacher()) &&
              UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PUUDUMINE);
    }
    
    public static boolean canAccept(HoisUserDetails user, StudentAbsence absence) {
        Student student = absence.getStudent();
        return !accepted(absence) && 
              (UserUtil.isSchoolAdmin(user, student.getSchool()) || 
               UserUtil.isStudentGroupTeacher(user, student))
              && UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PUUDUMINE);
    }
    
    private static boolean accepted(StudentAbsence absence) {
        return Boolean.TRUE.equals(absence.getIsAccepted());
    }
}
