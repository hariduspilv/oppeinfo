package ee.hitsa.ois.web.commandobject.subject;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class SubjectAutocompleteCommand extends SearchCommand {

    private Boolean practice;
    private Boolean withCredits = Boolean.TRUE;
    private Boolean closedCurriculumVersionSubjects;
    private Boolean curriculumSubjects;
    private Long student;
    private Boolean otherStudents;
    // no final thesis and final exam subjects
    private Boolean noFinalSubjects;

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

    public Boolean getNoFinalSubjects() {
        return noFinalSubjects;
    }

    public void setNoFinalSubjects(Boolean noFinalSubjects) {
        this.noFinalSubjects = noFinalSubjects;
    }

}
