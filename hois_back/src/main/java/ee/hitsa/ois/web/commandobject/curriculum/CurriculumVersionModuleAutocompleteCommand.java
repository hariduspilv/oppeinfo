package ee.hitsa.ois.web.commandobject.curriculum;

import ee.hitsa.ois.web.commandobject.AutocompleteCommand;

public class CurriculumVersionModuleAutocompleteCommand extends AutocompleteCommand{
    
    private Long curriculumVersionId;
    private Boolean curriculumModules;
    private String curriculumVersionStatusCode;
    private Long schoolId;
    
    public Long getCurriculumVersionId() {
        return curriculumVersionId;
    }

    public void setCurriculumVersionId(Long curriculumVersionId) {
        this.curriculumVersionId = curriculumVersionId;
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

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
    
}
