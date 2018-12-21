package ee.hitsa.ois.web.commandobject.curriculum;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class CurriculumVersionOccupationModuleAutocompleteCommand extends SearchCommand {

    private Long curriculumVersion;
    private Boolean curriculumModules;
    private Boolean closedCurriculumVersionModules;
    private Boolean ignoreStatuses;
    private Long school;
    private Long student;
    private Boolean otherStudents;

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Boolean getCurriculumModules() {
        return curriculumModules;
    }

    public void setCurriculumModules(Boolean curriculumModules) {
        this.curriculumModules = curriculumModules;
    }

    public Boolean getClosedCurriculumVersionModules() {
        return closedCurriculumVersionModules;
    }

    public void setClosedCurriculumVersionModules(Boolean closedCurriculumVersionModules) {
        this.closedCurriculumVersionModules = closedCurriculumVersionModules;
    }

    public Boolean getIgnoreStatuses() {
        return ignoreStatuses;
    }

    public void setIgnoreStatuses(Boolean ignoreStatuses) {
        this.ignoreStatuses = ignoreStatuses;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
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
