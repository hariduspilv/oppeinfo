package ee.hitsa.ois.web.commandobject.curriculum;

public class CurriculumVersionOccupationModuleThemeAutocompleteCommand {

    private Long curriculumVersionOmoduleId;
    private String curriculumVersionStatusCode;
    
    public Long getCurriculumVersionOmoduleId() {
        return curriculumVersionOmoduleId;
    }

    public void setCurriculumVersionOmoduleId(Long curriculumVersionOmoduleId) {
        this.curriculumVersionOmoduleId = curriculumVersionOmoduleId;
    }

    public String getCurriculumVersionStatusCode() {
        return curriculumVersionStatusCode;
    }

    public void setCurriculumVersionStatusCode(String curriculumVersionStatusCode) {
        this.curriculumVersionStatusCode = curriculumVersionStatusCode;
    }
    
}
