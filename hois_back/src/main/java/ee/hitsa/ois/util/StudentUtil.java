package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.enums.StudentStatus;

public abstract class StudentUtil {

    public static boolean isActive(Student student) {
        String status = EntityUtil.getNullableCode(student.getStatus());
        return status != null && StudentStatus.STUDENT_STATUS_ACTIVE.contains(status);
    }

    public static boolean isStudying(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_O, student.getStatus());
    }

    public static boolean hasFinished(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_L, student.getStatus());
    }

    public static boolean isOnAcademicLeave(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_A, student.getStatus());
    }

    public static boolean isHigher(Student student) {
        return CurriculumUtil.isHigher(student.getCurriculumVersion().getCurriculum());
    }

    public static boolean isVocational(Student student) {
        return CurriculumUtil.isVocational(student.getCurriculumVersion().getCurriculum());
    }

    public static boolean hasQuit(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_K, student.getStatus());
    }
    
    public static boolean canBeEdited(Student student) {
        return !hasFinished(student) && !hasQuit(student);
    }

    public static boolean isNominalStudy(Student student) {
        LocalDate nominalStudyEnd = student.getNominalStudyEnd();
        return nominalStudyEnd != null && LocalDate.now().isBefore(student.getNominalStudyEnd());
    }

    public static boolean isAdultAndDoNotNeedRepresentative(Student student) {
        return doNotNeedRepresentative(student) && PersonUtil.isAdult(student.getPerson());
    }
    
    public static boolean doNotNeedRepresentative(Student student) {
        return Boolean.FALSE.equals(student.getIsRepresentativeMandatory());
    }

    public static boolean hasRepresentatives(Student student) {
        return StreamUtil.nullSafeList(student.getRepresentatives()).stream().anyMatch(StudentRepresentative::getIsStudentVisible);
    }

    public static BigDecimal getCurriculumCompletion(BigDecimal credits, Student student) {
        BigDecimal curriculumCredits = student.getCurriculumVersion().getCurriculum().getCredits();
        return credits.multiply(BigDecimal.valueOf(100))
                .divide(curriculumCredits, 0, RoundingMode.HALF_UP);
    }
    
}
