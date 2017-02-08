package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.Student;
import ee.hitsa.ois.domain.StudentAbsence;
import ee.hitsa.ois.domain.StudentRepresentative;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class UserUtil {

    public static boolean canViewStudent(HoisUserDetails user, Student student) {
        // TODO add teacher
        return isSchoolAdmin(user, student.getSchool()) || isStudent(user, student)|| isStudentRepresentative(user, student);
    }

    public static boolean canEditStudent(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool()) || isAdultStudent(user, student)|| isStudentRepresentative(user, student);        
    }

    public static boolean canAddStudentAbsence(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool()) || isStudentRepresentative(user, student);
    }

    public static boolean canEditStudentAbsence(HoisUserDetails user, StudentAbsence absence) {
        Student student = absence.getStudent();
        // TODO representative can only edit absence created by him/her
        return isSchoolAdmin(user, student.getSchool()) || isStudentRepresentative(user, student);
    }

    /**
     * Can given user add representative to given student?
     *
     * @param user
     * @param student
     * @return
     */
    public static boolean canAddStudentRepresentative(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool()) || isAdultStudent(user, student);
    }

    /**
     * Can given user edit given student representative record?
     *
     * @param user
     * @param representative
     * @return
     */
    public static boolean canEditStudentRepresentative(HoisUserDetails user, StudentRepresentative representative) {
        Student student = representative.getStudent();
        if(isSchoolAdmin(user, student.getSchool()) || isAdultStudent(user, student)) {
            return true;
        }
        // representative can edit it's own record if student's data is visible to him/her
        return EntityUtil.getId(representative.getPerson()).equals(user.getPersonId()) && Boolean.TRUE.equals(representative.getIsStudentVisible());
    }

    /**
     * Is given user admin of given school?
     *
     * @param user
     * @param school
     * @return
     */
    public static boolean isSchoolAdmin(HoisUserDetails user, School school) {
        return user.isSchoolAdmin() && EntityUtil.getId(school).equals(user.getSchoolId());
    }

    public static boolean isAdultStudent(HoisUserDetails user, Student student) {
        return isStudent(user, student) && PersonUtil.isAdult(student.getPerson()) && Boolean.FALSE.equals(student.getIsRepresentativeMandatory());
    }

    public static boolean isStudent(HoisUserDetails user, Student student) {
        return user.getPersonId().equals(EntityUtil.getId(student.getPerson()));
    }

    public static boolean isStudentRepresentative(HoisUserDetails user, Student student) {
        return student.getRepresentatives().stream().anyMatch(r -> EntityUtil.getId(r.getPerson()).equals(user.getPersonId()));
    }
}
