package ee.hitsa.ois.web.commandobject.subject;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class SubjectAutocompleteCommand extends SearchCommand {

    private Boolean practice;
    private Boolean withCredits = Boolean.TRUE;
    private Boolean ignoreCurriculumVersionStatus;
    private Boolean curriculumSubjects;
    private Long student;
    private Boolean otherStudents;
    // no final thesis and final exam subjects
    private Boolean noFinalSubjects;
    private Long userId;
    private Long studyPeriod;

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

    public Boolean getWithCredits() {
        return withCredits;
    }

    public Boolean getIgnoreCurriculumVersionStatus() {
        return ignoreCurriculumVersionStatus;
    }

    public void setIgnoreCurriculumVersionStatus(Boolean ignoreCurriculumVersionStatus) {
        this.ignoreCurriculumVersionStatus = ignoreCurriculumVersionStatus;
    }

    public void setWithCredits(Boolean withCredits) {
        this.withCredits = withCredits;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

}
