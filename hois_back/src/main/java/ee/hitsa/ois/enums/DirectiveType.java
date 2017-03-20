package ee.hitsa.ois.enums;

import java.util.EnumSet;

public enum DirectiveType {

    KASKKIRI_AKAD(StudentStatus.OPPURSTAATUS_A, "nominalStudyEnd"),
    KASKKIRI_AKADK(StudentStatus.OPPURSTAATUS_O, "nominalStudyEnd"),
    KASKKIRI_OKOORM(null, "fin", "finSpecific"),
    KASKKIRI_OKAVA(null, "curriculumVersion", "studyForm", "studentGroup"),
    KASKKIRI_FINM(null, "fin", "finSpecific"),
    KASKKIRI_OVORM(null, "studyForm"),
    KASKKIRI_VALIS(StudentStatus.OPPURSTAATUS_V),
    KASKKIRI_EKSMAT(StudentStatus.OPPURSTAATUS_K),
    KASKKIRI_LOPET(StudentStatus.OPPURSTAATUS_L),
    KASKKIRI_IMMAT(StudentStatus.OPPURSTAATUS_O, "curriculumVersion", "studyLoad", "studyForm", "fin", "finSpecific", "language", "studentGroup", "previousStudyLevel"),
    KASKKIRI_IMMATV(StudentStatus.OPPURSTAATUS_O, "curriculumVersion", "studyLoad", "studyForm", "fin", "finSpecific", "language", "studentGroup", "previousStudyLevel"),
    KASKKIRI_ENNIST(null, "studentGroup"),
    KASKKIRI_TYHIST(null),
    // TODO these are missing
    KASKKIRI_KYLALIS(null, "nominalStudyEnd"),
    RIIGIKEEL(null, "nominalStudyEnd");

    private final StudentStatus studentStatus;
    private final String[] updatedFields;

    DirectiveType(StudentStatus studentStatus, String... updatedFields) {
        this.studentStatus = studentStatus;
        this.updatedFields = updatedFields;
    }

    public StudentStatus studentStatus() {
        return studentStatus;
    }

    public String[] updatedFields() {
        return updatedFields;
    }

    // these types require always application
    public static EnumSet<DirectiveType> ONLY_FROM_APPLICATION = EnumSet.of(
            KASKKIRI_AKAD, KASKKIRI_AKADK, KASKKIRI_OKAVA, KASKKIRI_FINM, KASKKIRI_OVORM, KASKKIRI_VALIS);
}
