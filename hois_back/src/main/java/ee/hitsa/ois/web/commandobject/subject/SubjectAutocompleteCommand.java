package ee.hitsa.ois.web.commandobject.subject;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class SubjectAutocompleteCommand extends SearchCommand {

    private Boolean practice;
    private Boolean withCredits = Boolean.TRUE;
    private Long curriculumVersion;
    private Boolean closedCurriculumVersionSubjects;
    private Boolean curriculumSubjects;
    private Long student;
    private Boolean otherStudents;

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

    public Boolean getClosedCurriculumVersionSubjects() {
        return closedCurriculumVersionSubjects;
    }

    public void setClosedCurriculumVersionSubjects(Boolean closedCurriculumVersionSubjects) {
        this.closedCurriculumVersionSubjects = closedCurriculumVersionSubjects;
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

    public Boolean getOtherStudents() {
        return otherStudents;
    }

    public void setOtherStudents(Boolean otherStudents) {
        this.otherStudents = otherStudents;
    }
}
