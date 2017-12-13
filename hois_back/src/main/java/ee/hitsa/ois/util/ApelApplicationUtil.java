package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalReplacedSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.validation.ValidationFailedException;

public class ApelApplicationUtil {

    public static void assertFormalReplacedSubject(ApelApplicationFormalReplacedSubjectOrModule replacedSubject) {
        if (replacedSubject.getSubject() == null) {
            throw new ValidationFailedException("apel.error.subjectIsNull");
        }
    }
    
    public static void assertFormalReplacedModule(ApelApplicationFormalReplacedSubjectOrModule replacedModule) {
        if (replacedModule.getCurriculumVersionOmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        }
    }
    
    public static void assertFormalSubject(ApelApplicationFormalSubjectOrModule subject) {
        if (Boolean.FALSE.equals(subject.getIsMySchool()) && subject.getApelSchool() == null) {
            throw new ValidationFailedException("apel.error.schoolIsNull");
        } else if (Boolean.TRUE.equals(subject.getIsMySchool()) && subject.getSubject() == null) {
            throw new ValidationFailedException("apel.error.subjectIsNull");
        } else if (Boolean.TRUE.equals(subject.getIsMySchool()) && subject.getCurriculumVersionHmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        } else if (Boolean.FALSE.equals(subject.getIsMySchool()) && subject.getNameEt() == null) {
            throw new ValidationFailedException("apel.error.subjectNameEtIsNull");
        } else if (Boolean.FALSE.equals(subject.getIsMySchool()) && subject.getNameEn() == null) {
            throw new ValidationFailedException("apel.error.subjectNameEnIsNull");
        } else if (subject.getSubjectCode() == null) {
            throw new ValidationFailedException("apel.error.subjectCodeIsNull");
        }
    }
    
    public static void assertFormalModule(ApelApplicationFormalSubjectOrModule module) {
        if (Boolean.FALSE.equals(module.getIsMySchool()) && module.getApelSchool() == null) {
            throw new ValidationFailedException("apel.error.schoolIsNull");
        } else if (Boolean.TRUE.equals(module.getIsMySchool()) && module.getCurriculumVersionOmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        } else if (Boolean.FALSE.equals(module.getIsMySchool()) && module.getNameEt() == null) {
            throw new ValidationFailedException("apel.error.moduleNameEtIsNull");
        } else if (Boolean.FALSE.equals(module.getIsMySchool()) && module.getNameEn() == null) {
            throw new ValidationFailedException("apel.error.moduleNameEnIsNull");
        } 
    }
    
    public static void assertInformalSubject(ApelApplicationInformalSubjectOrModule subject) {
        if (subject.getSubject() == null) {
            throw new ValidationFailedException("apel.error.subjectIsNull");
        } else if (subject.getCurriculumVersionHmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        }
    }
    
    public static void assertInformalModule(ApelApplicationInformalSubjectOrModule module) {
        if (module.getCurriculumVersionOmodule() == null) {
            throw new ValidationFailedException("apel.error.moduleIsNull");
        }
    }
}
