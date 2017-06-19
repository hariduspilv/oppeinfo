package ee.hitsa.ois.web.commandobject.report;

public class CurriculumCompletionCommand {

    private String name;
    private Long curriculumVersion;
    private Long studentGroup;
    private String studyForm;
    private String studyLoad;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(String studyLoad) {
        this.studyLoad = studyLoad;
    }
}
