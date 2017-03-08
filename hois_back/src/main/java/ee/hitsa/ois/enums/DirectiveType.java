package ee.hitsa.ois.enums;

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
    KASKKIRI_ENNIST(null, "studentGroup"),
    KASKKIRI_TYHIST(null),
    // FIXME these are missing
    KYLALISOPILANE(null, "nominalStudyEnd"),
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
}
