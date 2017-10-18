package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.DeclarationDto;

public abstract class DeclarationUtil {

    public static boolean canChangeDeclarationFromSearchForm(HoisUserDetails user, DeclarationDto dto) {
        return user.isSchoolAdmin() && DeclarationStatus.OPINGUKAVA_STAATUS_S.name().equals(dto.getStatus());
    }

    public static boolean canChangeDeclaration(HoisUserDetails user, Declaration declaration) {
        return canEditOrChangeStatus(user, declaration.getStudent()) && 
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
            //TODO: can be unconfirmed by student only before deadline
            return !studyPeriodFinished(declaration.getStudyPeriod()); 
            
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
                canEditOrChangeStatus(user, declaration.getStudent());
    }

    public static boolean canEditOrChangeStatus(HoisUserDetails user, Student student) {
        return UserUtil.isSchoolAdmin(user, student.getSchool())  || 
                (UserUtil.isStudent(user, student) && StudentUtil.isStudying(student));
    }
}
