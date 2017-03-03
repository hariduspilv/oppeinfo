package ee.hitsa.ois.enums;

public enum DirectiveType {

    // TODO correct names from classifier KASKKIRI
    AKAD(StudentStatus.OPPURSTAATUS_AKAD, "nominalStudyEnd"),
    AKADOFF(StudentStatus.OPPURSTAATUS_OPIB, "nominalStudyEnd"),
    OPPEKOORMUSE_MUUTMINE(null, "fin", "finSpecific"),
    OPPEKAVA_VAHETAMINE(null, "curriculumVersion", "studyForm", "studentGroup"),
    FINALLIKAS_MUUTMINE(null, "fin", "finSpecific"),
    OPPEVORM_VAHETAMINE(null, "studyForm"),
    VALISOPPUR(StudentStatus.OPPURSTAATUS_VALISOPPUR),
    EXMAT(StudentStatus.OPPURSTAATUS_EKSMAT),
    LOPETAMINE(StudentStatus.OPPURSTAATUS_LOPETANUD),
    IMMAT(StudentStatus.OPPURSTAATUS_OPIB, "curriculumVersion", "studyLoad", "studyForm", "fin", "finSpecific", "language", "studentGroup", "previousStudyLevel"),
    ENNISTAMINE(null, "studendGroup"),
    KYLALISOPILANE(null, "nominalStudyEnd"),
    RIIGIKEEL(null, "nominalStudyEnd"),
    TYHISTAMINE(null);

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
