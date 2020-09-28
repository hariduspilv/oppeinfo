package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class StudentAutocompleteCommand extends SearchCommand {

    private Boolean active;
    private Boolean finishing;
    private Boolean finished;
    private Boolean studying;
    private Boolean academicLeave;
    private Boolean nominalStudy;
    private Boolean higher;
    private Boolean showStudentGroup;
    private Boolean showGuestStudent;
    private Boolean hideGuestStudents;
    private Boolean onlyStudyingOrFinishedGuestStudent;
    private List<Long> curriculumVersion;
    private List<Long> studentGroup;
    private Long studentGroupTeacher;
    private Long userId;
    private Boolean hasCurriculumVersion;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getFinishing() {
        return finishing;
    }

    public void setFinishing(Boolean finishing) {
        this.finishing = finishing;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getStudying() {
        return studying;
    }

    public void setStudying(Boolean studying) {
        this.studying = studying;
    }

    public Boolean getAcademicLeave() {
        return academicLeave;
    }

    public void setAcademicLeave(Boolean academicLeave) {
        this.academicLeave = academicLeave;
    }

    public Boolean getNominalStudy() {
        return nominalStudy;
    }

    public void setNominalStudy(Boolean nominalStudy) {
        this.nominalStudy = nominalStudy;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getShowStudentGroup() {
        return showStudentGroup;
    }

    public void setShowStudentGroup(Boolean showStudentGroup) {
        this.showStudentGroup = showStudentGroup;
    }

    public List<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(List<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public List<Long> getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(List<Long> studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Long getStudentGroupTeacher() {
        return studentGroupTeacher;
    }

    public void setStudentGroupTeacher(Long studentGroupTeacher) {
        this.studentGroupTeacher = studentGroupTeacher;
    }

    public Boolean getShowGuestStudent() {
        return showGuestStudent;
    }

    public void setShowGuestStudent(Boolean showGuestStudent) {
        this.showGuestStudent = showGuestStudent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getHideGuestStudents() {
        return hideGuestStudents;
    }

    public void setHideGuestStudents(Boolean hideGuestStudents) {
        this.hideGuestStudents = hideGuestStudents;
    }

    public Boolean getOnlyStudyingOrFinishedGuestStudent() {
        return onlyStudyingOrFinishedGuestStudent;
    }

    public void setOnlyStudyingOrFinishedGuestStudent(Boolean onlyStudyingOrFinishedGuestStudent) {
        this.onlyStudyingOrFinishedGuestStudent = onlyStudyingOrFinishedGuestStudent;
    }

    public Boolean getHasCurriculumVersion() {
        return hasCurriculumVersion;
    }

    public void setHasCurriculumVersion(Boolean hasCurriculumVersion) {
        this.hasCurriculumVersion = hasCurriculumVersion;
    }

}
