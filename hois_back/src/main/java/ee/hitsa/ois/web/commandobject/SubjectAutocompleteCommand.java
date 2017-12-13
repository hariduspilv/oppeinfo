package ee.hitsa.ois.web.commandobject;

public class SubjectAutocompleteCommand extends AutocompleteCommand {

    private Boolean practice;
    private Boolean withCredits = Boolean.TRUE;
    private Long curriculumVersion;
    private Boolean curriculumSubjects;
    private Long student;

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

    public Boolean getWithCredits() {
        return withCredits;
    }

    public void setWithCredits(Boolean withCredits) {
        this.withCredits = withCredits;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Boolean getCurriculumSubjects() {
        return curriculumSubjects;
    }

    public void setCurriculumSubjects(Boolean curriculumSubjects) {
        this.curriculumSubjects = curriculumSubjects;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }
    
}
