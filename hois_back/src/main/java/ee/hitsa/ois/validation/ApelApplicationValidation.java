package ee.hitsa.ois.validation;

public interface ApelApplicationValidation {

    interface InternalPreviouslyPassedModule extends ApelApplicationValidation { }
    interface InternalCurriculumModule extends ApelApplicationValidation { }
    interface InternalOtherModule extends ApelApplicationValidation { }
    interface ExternalOtherModule extends ApelApplicationValidation { }

    interface InternalPreviouslyPassedSubject extends ApelApplicationValidation { }
    interface InternalCurriculumSubject extends ApelApplicationValidation { }
    interface InternalOtherSubject extends ApelApplicationValidation { }
    interface ExternalOtherSubject extends ApelApplicationValidation { }

}
