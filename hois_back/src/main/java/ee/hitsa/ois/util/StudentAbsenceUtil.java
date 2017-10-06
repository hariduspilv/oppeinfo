package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
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
    
    public static boolean canAccept(HoisUserDetails user, StudentAbsence absence) {
        Student student = absence.getStudent();
        return !accepted(absence) && 
              (UserUtil.isSchoolAdmin(user, student.getSchool()) || 
               UserUtil.isStudentGroupTeacher(user, student));
    }
    
    private static boolean accepted(StudentAbsence absence) {
        return Boolean.TRUE.equals(absence.getIsAccepted());
    }
}
