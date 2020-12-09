package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

import ee.hitsa.ois.enums.StudyLanguage;
import org.apache.commons.lang3.StringUtils;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.PhotoAdd;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;

public abstract class StudentUtil {

    public static boolean isActive(Student student) {
        return isActive(EntityUtil.getNullableCode(student.getStatus()));
    }

    public static boolean isActive(String status) {
        return status != null && StudentStatus.STUDENT_STATUS_ACTIVE.contains(status);
    }

    public static boolean isStudying(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_O, student.getStatus());
    }
    
    public static boolean isExternal(Student student) {
        return ClassifierUtil.equals(StudentType.OPPUR_E, student.getType());
    }

    public static boolean hasFinished(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_L, student.getStatus());
    }

    public static boolean isOnAcademicLeave(Student student) {
        return ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_A, student.getStatus());
    }
    
    public static boolean isHigher(Student student) {
        if (student.getCurriculumVersion() == null) return getIsDirectiveHigher(student);
        return CurriculumUtil.isHigher(student.getCurriculumVersion().getCurriculum());
    }

    public static boolean isVocational(Student student) {
        if (student.getCurriculumVersion() == null) return !getIsDirectiveHigher(student);
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
    
    /**
     * Once ERIVAJADUS classifier found it should have ehis_value because it is sent to EHIS.
     * 
     * @param student
     * @return has valid special need
     */
    public static boolean hasSpecialNeeds(Student student) {
        return student.getSpecialNeeds().stream().filter(sn -> !StringUtils.isBlank(sn.getSpecialNeed().getEhisValue())).findAny().isPresent();
    }

    public static BigDecimal getCurriculumCompletion(BigDecimal credits, Student student) {
        BigDecimal curriculumCredits = student.getCurriculumVersion().getCurriculum().getCredits();
        return credits.multiply(BigDecimal.valueOf(100))
                .divide(curriculumCredits, 0, RoundingMode.HALF_UP);
    }
    
    public static boolean canStudentEditPhoto(Student student) {
        PhotoAdd permissionLevel = EnumUtil.valueOf(PhotoAdd.class, student.getSchool().getStudentPhotoAdd());
        switch (permissionLevel) {
        case FOTOLISA_TAIS:
            return isAdultAndDoNotNeedRepresentative(student);
        case FOTOLISA_KOIK:
            return true;
        case FOTOLISA_EI:
            // fallthrough
        default:
            return false;
        }
    }
    public static boolean getIsDirectiveHigher(Student student) {
        // should be only one directive per student
        Optional<Directive> directive = Optional.empty();
        if (StudentUtil.isGuestStudent(student)) {
            directive = student.getDirectiveStudents().stream()
                    .filter(p -> p.getDirective() != null && p.getDirective().getType() != null && !Boolean.TRUE.equals(p.getCanceled())
                        && DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name().equals(EntityUtil.getNullableCode(p.getDirective().getStatus()))
                        && DirectiveType.KASKKIRI_KYLALIS.name().equals(EntityUtil.getNullableCode(p.getDirective().getType())))
                    .map(p -> p.getDirective()).findFirst();
        } else if (StudentUtil.isExternal(student)) {
            directive = student.getDirectiveStudents().stream()
                    .filter(p -> p.getDirective() != null && p.getDirective().getStatus() != null && p.getDirective().getType() != null && !Boolean.TRUE.equals(p.getCanceled())
                        && DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name().equals(EntityUtil.getNullableCode(p.getDirective().getStatus()))
                        && DirectiveType.KASKKIRI_EKSTERN.name().equals(EntityUtil.getNullableCode(p.getDirective().getType())))
                    .map(p -> p.getDirective()).sorted(Comparator.comparing(Directive::getConfirmDate).reversed()).findFirst();
        }
        if (directive.isPresent()) {
            Boolean higher = directive.get().getIsHigher();
            if (higher == null) return false;
            return higher.booleanValue();
        }
        return false;
    }

    public static boolean isGuestStudent(Student student) {
        return StudentType.OPPUR_K.name().equals(EntityUtil.getNullableCode(student.getType()));
    }

    public static boolean isStudyingInEstonian(Student student) {
        return StudyLanguage.OPPEKEEL_E.name().equals(EntityUtil.getNullableCode(student.getLanguage()));
    }
}
