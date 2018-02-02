package ee.hitsa.ois.web.curriculum;

import ee.hitsa.ois.validation.Required;

public class CurriculumVersionHigherModuleAutocompleteCommand {
    
    @Required
    private Long curriculumVersion;
    private String curriculumVersionStatusCode;
    
    public Long getCurriculumVersion() {
        return curriculumVersion;
    }
    
    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    
    public String getCurriculumVersionStatusCode() {
        return curriculumVersionStatusCode;
    }
    
    public void setCurriculumVersionStatusCode(String curriculumVersionStatusCode) {
        this.curriculumVersionStatusCode = curriculumVersionStatusCode;
    }

}
