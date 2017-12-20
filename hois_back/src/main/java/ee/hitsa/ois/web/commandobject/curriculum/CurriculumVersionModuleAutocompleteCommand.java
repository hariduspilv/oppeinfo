package ee.hitsa.ois.web.commandobject.curriculum;

import ee.hitsa.ois.web.commandobject.AutocompleteCommand;

public class CurriculumVersionModuleAutocompleteCommand extends AutocompleteCommand{
    
    private Long curriculumVersion;
    private Boolean curriculumModules;
    private String curriculumVersionStatusCode;
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

    public String getCurriculumVersionStatusCode() {
        return curriculumVersionStatusCode;
    }

    public void setCurriculumVersionStatusCode(String curriculumVersionStatusCode) {
        this.curriculumVersionStatusCode = curriculumVersionStatusCode;
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
