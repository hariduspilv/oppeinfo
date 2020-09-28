package ee.hitsa.ois.enums;

import java.util.EnumSet;

import ee.hitsa.ois.validation.DirectiveValidation;

public enum DirectiveType {

    KASKKIRI_AKAD(null, DirectiveValidation.Akad.class),
    KASKKIRI_AKADK(null, DirectiveValidation.Akadk.class),
    KASKKIRI_DUPLIKAAT(null, DirectiveValidation.Duplikaat.class),
    KASKKIRI_EKSMAT(StudentStatus.OPPURSTAATUS_K, DirectiveValidation.Eksmat.class),
    KASKKIRI_EKSTERN(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Ekstern.class, "curriculumVersion", "studyLoad", "studyForm", "fin", "finSpecific", "language", "studentGroup", "previousStudyLevel", "nominalStudyEnd", "dormitory"),
    KASKKIRI_EKSTERNKATK(StudentStatus.OPPURSTAATUS_K, DirectiveValidation.Ekstern.class),
    KASKKIRI_ENNIST(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Ennist.class, "studentGroup", "nominalStudyEnd"),
    KASKKIRI_FINM(null, DirectiveValidation.Finm.class, "fin", "finSpecific"),
    KASKKIRI_LOPET(StudentStatus.OPPURSTAATUS_L, DirectiveValidation.Lopet.class),
    KASKKIRI_OKAVA(null, DirectiveValidation.Okava.class, "curriculumVersion", "studyForm", "studentGroup"),
    KASKKIRI_OKOORM(null, DirectiveValidation.Okoorm.class, "studyLoad", "fin", "finSpecific"),
    KASKKIRI_OVORM(null, DirectiveValidation.Ovorm.class, "studyForm", "studentGroup"),
    KASKKIRI_VALIS(null, DirectiveValidation.Valis.class),
    KASKKIRI_IMMAT(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Immat.class, "curriculumVersion", "studyLoad", "studyForm", "fin", "finSpecific", "language", "studentGroup", "previousStudyLevel", "nominalStudyEnd", "dormitory"),
    KASKKIRI_IMMATV(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Immat.class, "curriculumVersion", "studyLoad", "studyForm", "fin", "finSpecific", "language", "studentGroup", "previousStudyLevel", "dormitory"),
    KASKKIRI_INDOK(null, DirectiveValidation.Indok.class),
    KASKKIRI_INDOKLOP(null, DirectiveValidation.Indoklop.class),
    KASKKIRI_MUU(null, DirectiveValidation.Muu.class),
    KASKKIRI_TYHIST(null, null),
    KASKKIRI_STIPTOET(null, DirectiveValidation.Stiptoet.class),
    KASKKIRI_STIPTOETL(null, DirectiveValidation.Stiptoetl.class),
    KASKKIRI_KIITUS(null, DirectiveValidation.Kiitus.class),
    KASKKIRI_NOOMI(null, DirectiveValidation.Noomi.class),
    KASKKIRI_PRAKTIK(null, DirectiveValidation.Praktik.class),
    KASKKIRI_OTEGEVUS(null, DirectiveValidation.Otegevus.class),
    KASKKIRI_TUGI(null, DirectiveValidation.Tugi.class),
    KASKKIRI_TUGILOPP(null, DirectiveValidation.Tugilopp.class),
    KASKKIRI_KYLALIS(StudentStatus.OPPURSTAATUS_O, DirectiveValidation.Kylalis.class, "curriculumVersion", "studentGroup", "previousStudyLevel"),
    KASKKIRI_VALISKATK(null, DirectiveValidation.Valiskatk.class);

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
    // TUGI is ignored. Application control added as additional filter.
    public static final EnumSet<DirectiveType> ONLY_FROM_APPLICATION = EnumSet.of(
            KASKKIRI_AKAD, KASKKIRI_AKADK, KASKKIRI_OKAVA, KASKKIRI_FINM, KASKKIRI_OVORM, KASKKIRI_VALIS);
}
