package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class StudentAbsenceUtil {

    public static boolean canCreate(HoisUserDetails user, Student student) {
        return UserUtil.isAdultStudent(user, student) || (UserUtil.isStudentAndDoNotNeedRepresentative(user, student) && Boolean.TRUE.equals(student.getSchool().getIsMinorStudentAbsence())) ||
                ((UserUtil.isSchoolAdmin(user, student.getSchool()) || UserUtil.isStudentGroupTeacher(user, student)) && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PUUDUMINE)) ||
                UserUtil.isStudentRepresentative(user, student);
    }

    public static boolean canEdit(HoisUserDetails user, StudentAbsence absence) {
        Student student = absence.getStudent();
        return !accepted(absence) && !rejected(absence) &&
                (UserUtil.isAdultStudent(user, student) || (UserUtil.isStudentAndDoNotNeedRepresentative(user, student) && Boolean.TRUE.equals(student.getSchool().getIsMinorStudentAbsence())) ||
                ((UserUtil.isSchoolAdmin(user, student.getSchool()) || UserUtil.isStudentGroupTeacher(user, student)) && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PUUDUMINE)) ||
                UserUtil.isStudentRepresentative(user, student));
    }

    /**
     * Light method for search form. 
     * User do not see other absences on search form anyway, 
     * and more strict check of user rights in back end is done on accepting.
     */
    public static boolean hasPermissionToChangeStatus(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isTeacher()) &&
              UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PUUDUMINE);
    }

    public static boolean hasPermissionToSearch(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isTeacher()) &&
              UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PUUDUMINE);
    }

    public static boolean canChangeStatus(HoisUserDetails user, StudentAbsence absence) {
        Student student = absence.getStudent();
        return !accepted(absence) && !rejected(absence) &&
              (UserUtil.isSchoolAdmin(user, student.getSchool()) || 
               UserUtil.isStudentGroupTeacher(user, student))
              && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PUUDUMINE);
    }

    public static void assertCanSearch(HoisUserDetails user) {
        AssertionFailedException.throwIf(!hasPermissionToSearch(user), "User cannot search student absences");
    }

    public static void assertCanCreate(HoisUserDetails user, Student student) {
        AssertionFailedException.throwIf(!canCreate(user, student), "User cannot add student absence");
    }

    public static void assertCanEdit(HoisUserDetails user, StudentAbsence absence) {
        AssertionFailedException.throwIf(!canEdit(user, absence), "User cannot edit student absence");
    }

    public static void assertCanChangeStatus(HoisUserDetails user, StudentAbsence absence) {
        AssertionFailedException.throwIf(!canChangeStatus(user, absence), "User cannot accept student absence");
    }

    private static boolean accepted(StudentAbsence absence) {
        return Boolean.TRUE.equals(absence.getIsAccepted()) && Boolean.FALSE.equals(absence.getIsRejected());
    }
    
    private static boolean rejected(StudentAbsence absence) {
        return Boolean.TRUE.equals(absence.getIsRejected());
    }
}
