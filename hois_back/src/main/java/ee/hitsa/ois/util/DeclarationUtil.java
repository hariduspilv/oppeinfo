package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.StudyPeriodEventType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.DeclarationDto;

public abstract class DeclarationUtil {

    public static boolean canChangeDeclarationFromSearchForm(HoisUserDetails user, DeclarationDto dto) {
        return user.isSchoolAdmin() && DeclarationStatus.OPINGUKAVA_STAATUS_S.name().equals(dto.getStatus());
    }

    public static boolean canChangeDeclaration(HoisUserDetails user, Declaration declaration) {
        return canEditOrChangeStatus(user, declaration) && 
                ClassifierUtil.equals(DeclarationStatus.OPINGUKAVA_STAATUS_S, declaration.getStatus());
    }

    public static boolean declarationConfirmed(Declaration declaration) {
        return ClassifierUtil.equals(DeclarationStatus.OPINGUKAVA_STAATUS_K, declaration.getStatus());
    }

    public static boolean canUnconfirmDeclaration(HoisUserDetails user, Declaration declaration) {
        if(!declarationConfirmed(declaration)) {
            return false;
        }
        if(UserUtil.isStudent(user, declaration.getStudent())) {
            StudyPeriodEvent declarationPeriod = declaration.getStudyPeriod().getEvents().stream()
                    .filter(e -> StudyPeriodEventType.SYNDMUS_DEKP.name().equals(EntityUtil.getCode(e.getEventType())))
                    .findFirst().orElse(null);
            
            return isDeclarationPeriod(declarationPeriod); 
        } else if (UserUtil.isSchoolAdmin(user, declaration.getStudent().getSchool())) {
            return !studyPeriodFinished(declaration.getStudyPeriod());
        }
        return false;
    }

    private static boolean studyPeriodFinished(StudyPeriod studyPeriod) {
        return  LocalDate.now().isAfter(studyPeriod.getEndDate());
    }

    public static void assertCanConfirm(HoisUserDetails user, Declaration declaration) {
        AssertionFailedException.throwIf(!canConfirmDeclaration(user, declaration),
                "You cannot confirm declaration!");
    }

    public static void assertCanUnconfirmDeclaration(HoisUserDetails user, Declaration declaration) {
        AssertionFailedException.throwIf(!canUnconfirmDeclaration(user, declaration),
                "You cannot set declaration unconfirmed!");
    }

    public static void assertCanChangeDeclaration(HoisUserDetails user, Declaration declaration) {
        AssertionFailedException.throwIf(!canChangeDeclaration(user, declaration),
                "You cannot change declaration!");
    }

    public static boolean canConfirmDeclaration(HoisUserDetails user, Declaration declaration) {
        return ClassifierUtil.equals(DeclarationStatus.OPINGUKAVA_STAATUS_S, declaration.getStatus()) &&
                canEditOrChangeStatus(user, declaration);
    }

    public static boolean canEditOrChangeStatus(HoisUserDetails user, Declaration declaration) {
        Student student = declaration.getStudent();
        StudyPeriodEvent declarationPeriod = declaration.getStudyPeriod().getEvents().stream()
                .filter(e -> StudyPeriodEventType.SYNDMUS_DEKP.name().equals(EntityUtil.getCode(e.getEventType())))
                .findFirst().orElse(null);
        return UserUtil.isSchoolAdmin(user, student.getSchool()) || 
                (UserUtil.isStudent(user, student) && StudentUtil.isStudying(student) && isDeclarationPeriod(declarationPeriod));
    }
    
    public static boolean isDeclarationPeriod(StudyPeriodEvent declarationPeriod) {
        if (declarationPeriod != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime periodStart = declarationPeriod.getStart();
            LocalDateTime periodEnd = declarationPeriod.getEnd() != null ? declarationPeriod.getEnd() : null;
            
            if (now.isAfter(periodStart) && periodEnd == null) {
                return true;
            } else if (now.isAfter(periodStart) && periodEnd != null && now.isBefore(periodEnd)) {
                return true;
            }
        }
        return false;
    }
}
