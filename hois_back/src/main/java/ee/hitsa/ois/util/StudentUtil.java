package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentStatus;

public class StudentUtil {

    public static boolean isActive(Student student) {
        StudentStatus status = student.getStatus() != null ? StudentStatus.valueOf(EntityUtil.getCode(student.getStatus())) : null;
        return status == StudentStatus.OPPURSTAATUS_O || status == StudentStatus.OPPURSTAATUS_A || status == StudentStatus.OPPURSTAATUS_V;
    }

    public static boolean isStudying(Student student) {
        StudentStatus status = student.getStatus() != null ? StudentStatus.valueOf(EntityUtil.getCode(student.getStatus())) : null;
        return status == StudentStatus.OPPURSTAATUS_O;
    }

    public static boolean isOnAcademicLeave(Student student) {
        StudentStatus status = student.getStatus() != null ? StudentStatus.valueOf(EntityUtil.getCode(student.getStatus())) : null;
        return status == StudentStatus.OPPURSTAATUS_A;
    }

    public static boolean isNominalStudy(Student student) {
        LocalDate nominalStudyEnd = student.getNominalStudyEnd();
        return nominalStudyEnd != null && LocalDate.now().isBefore(student.getNominalStudyEnd());
    }

    public static boolean isAdult(Student student) {
        return PersonUtil.isAdult(student.getPerson()) && Boolean.FALSE.equals(student.getIsRepresentativeMandatory());
    }
}
