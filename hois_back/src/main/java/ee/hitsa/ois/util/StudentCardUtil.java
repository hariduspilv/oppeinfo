package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentCardStatus;

public class StudentCardUtil {

    public static boolean isMissing(Student student) {
        return ClassifierUtil.equals(StudentCardStatus.OPILASPILET_STAATUS_P, student.getStudentCardStatus());
    }
    
    public static boolean isOrdered(Student student) {
        return ClassifierUtil.equals(StudentCardStatus.OPILASPILET_STAATUS_T, student.getStudentCardStatus());
    }

    public static boolean isValid(Student student) {
        return ClassifierUtil.equals(StudentCardStatus.OPILASPILET_STAATUS_K, student.getStudentCardStatus());
    }

    public static boolean isHandedOver(Student student) {
        return ClassifierUtil.equals(StudentCardStatus.OPILASPILET_STAATUS_G, student.getStudentCardStatus());
    }

    public static boolean isReturned(Student student) {
        return ClassifierUtil.equals(StudentCardStatus.OPILASPILET_STAATUS_R, student.getStudentCardStatus());
    }
    
    public static void setReturned(Student student, Boolean returned, LocalDate when) {
        student.setIsStudentCardReturned(returned == null ? Boolean.FALSE : returned);
        student.setStudentCardReturnedDt(Boolean.TRUE.equals(student.getIsStudentCardReturned()) ? when : null);
        if (Boolean.TRUE.equals(student.getIsStudentCardReturned())) {
            setGiven(student, Boolean.FALSE, null);
        }
    }
    
    public static void setGiven(Student student, Boolean given, LocalDate when) {
        student.setIsStudentCardGiven(given != null ? given : Boolean.FALSE);
        student.setStudentCardGivenDt(Boolean.TRUE.equals(student.getIsStudentCardGiven()) ? when : null);
        if (Boolean.TRUE.equals(student.getIsStudentCardGiven())) {
            setReturned(student, Boolean.FALSE, null);
        }
    }
}
