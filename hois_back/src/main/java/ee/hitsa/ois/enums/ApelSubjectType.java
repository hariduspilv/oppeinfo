package ee.hitsa.ois.enums;

import ee.hitsa.ois.validation.ApelApplicationValidation;
import ee.hitsa.ois.validation.ApelApplicationValidation.*;

public enum ApelSubjectType {

    /** previously passed subject/module (as another student) */
    VOTA_AINE_LIIK_V(InternalPreviouslyPassedModule.class, null, InternalPreviouslyPassedSubject.class, null),
    /** curriculum subject/module */
    VOTA_AINE_LIIK_O(InternalCurriculumModule.class, null, InternalCurriculumSubject.class, null),
    /** other */
    VOTA_AINE_LIIK_M(InternalOtherModule.class, ExternalOtherModule.class, InternalOtherSubject.class, ExternalOtherSubject.class);

    private final Class<? extends ApelApplicationValidation> internalModule;
    private final Class<? extends ApelApplicationValidation> externalModule;
    private final Class<? extends ApelApplicationValidation> internalSubject;
    private final Class<? extends ApelApplicationValidation> externalSubject;

    private ApelSubjectType(Class<? extends ApelApplicationValidation> internalModule,
        Class<? extends ApelApplicationValidation> externalModule,
        Class<? extends ApelApplicationValidation> internalSubject,
        Class<? extends ApelApplicationValidation> externalSubject) {
            this.internalModule = internalModule;
            this.externalModule = externalModule;
            this.internalSubject = internalSubject;
            this.externalSubject = externalSubject;
    }

    public Class<? extends ApelApplicationValidation> getValidationGroup(Boolean isVocational, Boolean isMySchool) {
        if (Boolean.TRUE.equals(isVocational)) {
            return Boolean.TRUE.equals(isMySchool) ? internalModule : externalModule;
        } else {
            return Boolean.TRUE.equals(isMySchool) ? internalSubject : externalSubject;
        }
    }

}
