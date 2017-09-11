package ee.hitsa.ois.util;

import java.time.LocalDate;

import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.enums.StudentStatus;

public class StudentUtil {

    public static boolean isActive(Student student) {
        String status = EntityUtil.getNullableCode(student.getStatus());
        return status != null && StudentStatus.STUDENT_STATUS_ACTIVE.contains(status);
    }

    public static boolean isStudying(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_O, student.getStatus());
    }

    public static boolean isOnAcademicLeave(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_A, student.getStatus());
    }

    public static boolean isNominalStudy(Student student) {
        LocalDate nominalStudyEnd = student.getNominalStudyEnd();
        return nominalStudyEnd != null && LocalDate.now().isBefore(student.getNominalStudyEnd());
    }

    public static boolean isAdultAndDoNotNeedRepresentative(Student student) {
        return PersonUtil.isAdult(student.getPerson()) && Boolean.FALSE.equals(student.getIsRepresentativeMandatory());
    }

    public static boolean hasRepresentatives(Student student) {
        return !CollectionUtils.isEmpty(student.getRepresentatives()) &&
                student.getRepresentatives().stream().filter(StudentRepresentative::getIsStudentVisible).findFirst().isPresent();
    }
}
