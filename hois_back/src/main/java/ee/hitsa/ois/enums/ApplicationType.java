package ee.hitsa.ois.enums;

import ee.hitsa.ois.validation.ApplicationValidation;

public enum ApplicationType {

    AVALDUS_LIIK_AKAD(DirectiveType.KASKKIRI_AKAD, ApplicationValidation.Akad.class),
    AVALDUS_LIIK_AKADK(DirectiveType.KASKKIRI_AKADK, ApplicationValidation.Akadk.class),
    AVALDUS_LIIK_EKSMAT(DirectiveType.KASKKIRI_EKSMAT, ApplicationValidation.Eksmat.class),
    AVALDUS_LIIK_VALIS(DirectiveType.KASKKIRI_VALIS, ApplicationValidation.Valis.class),
    AVALDUS_LIIK_OKAVA(DirectiveType.KASKKIRI_OKAVA, ApplicationValidation.Okava.class),
    AVALDUS_LIIK_OVORM(DirectiveType.KASKKIRI_OVORM, ApplicationValidation.Ovorm.class),
    AVALDUS_LIIK_FINM(DirectiveType.KASKKIRI_FINM, ApplicationValidation.Finm.class),
    AVALDUS_LIIK_RAKKAVA(null, ApplicationValidation.Rakkava.class),
    AVALDUS_LIIK_OVERSKAVA(null, ApplicationValidation.Overskava.class),
    AVALDUS_LIIK_TUGI(DirectiveType.KASKKIRI_TUGI, ApplicationValidation.Tugi.class),
    AVALDUS_LIIK_MUU(null, ApplicationValidation.Muu.class);

    private final DirectiveType directiveType;
    private final Class<? extends ApplicationValidation> validationGroup;

    ApplicationType(DirectiveType directiveType, Class<? extends ApplicationValidation> validationGroup) {
        this.directiveType = directiveType;
        this.validationGroup = validationGroup;
    }

    public DirectiveType directiveType() {
        return directiveType;
    }

    public Class<? extends ApplicationValidation> validationGroup() {
        return validationGroup;
    }
}
