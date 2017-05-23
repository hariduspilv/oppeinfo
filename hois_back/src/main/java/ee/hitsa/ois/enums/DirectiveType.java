package ee.hitsa.ois.enums;

import java.util.EnumSet;

import ee.hitsa.ois.validation.DirectiveValidation;

public enum DirectiveType {

    KASKKIRI_AKAD(StudentStatus.OPPURSTAATUS_A, DirectiveValidation.Akad.class),
    KASKKIRI_AKADK(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Akadk.class),
    KASKKIRI_EKSMAT(StudentStatus.OPPURSTAATUS_K, DirectiveValidation.Eksmat.class),
    KASKKIRI_ENNIST(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Ennist.class, "studentGroup", "nominalStudyEnd"),
    KASKKIRI_FINM(null, DirectiveValidation.Finm.class, "fin", "finSpecific"),
    KASKKIRI_LOPET(StudentStatus.OPPURSTAATUS_L, DirectiveValidation.Lopet.class),
    KASKKIRI_OKAVA(null, DirectiveValidation.Okava.class, "curriculumVersion", "studyForm", "studentGroup"),
    KASKKIRI_OKOORM(null, DirectiveValidation.Okoorm.class, "studyLoad", "fin", "finSpecific"),
    KASKKIRI_OVORM(null, DirectiveValidation.Ovorm.class, "studyForm", "studentGroup"),
    KASKKIRI_VALIS(StudentStatus.OPPURSTAATUS_V, DirectiveValidation.Valis.class),
    KASKKIRI_IMMAT(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Immat.class, "curriculumVersion", "studyLoad", "studyForm", "fin", "finSpecific", "language", "studentGroup", "previousStudyLevel", "nominalStudyEnd"),
    KASKKIRI_IMMATV(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Immat.class, "curriculumVersion", "studyLoad", "studyForm", "fin", "finSpecific", "language", "studentGroup", "previousStudyLevel"),
    KASKKIRI_TYHIST(null, null),
    // TODO not yet implemented
    KASKKIRI_KYLALIS(null, null, "nominalStudyEnd");

    private final StudentStatus studentStatus;
    private final Class<? extends DirectiveValidation> validationGroup;
    private final String[] updatedFields;

    DirectiveType(StudentStatus studentStatus, Class<? extends DirectiveValidation> validationGroup, String... updatedFields) {
        this.studentStatus = studentStatus;
        this.validationGroup = validationGroup;
        this.updatedFields = updatedFields;
    }

    public StudentStatus studentStatus() {
        return studentStatus;
    }

    public Class<? extends DirectiveValidation> validationGroup() {
        return validationGroup;
    }

    public String[] updatedFields() {
        return updatedFields;
    }

    // these types require always application
    public static final EnumSet<DirectiveType> ONLY_FROM_APPLICATION = EnumSet.of(
            KASKKIRI_AKAD, KASKKIRI_AKADK, KASKKIRI_OKAVA, KASKKIRI_FINM, KASKKIRI_OVORM, KASKKIRI_VALIS);
}
