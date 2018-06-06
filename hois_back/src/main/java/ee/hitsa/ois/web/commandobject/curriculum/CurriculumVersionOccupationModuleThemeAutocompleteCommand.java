package ee.hitsa.ois.web.commandobject.curriculum;

import ee.hitsa.ois.validation.Required;

public class CurriculumVersionOccupationModuleThemeAutocompleteCommand {

    @Required
    private Long curriculumVersionOmoduleId;
    private Boolean closedCurriculumVersionModules;

    public Long getCurriculumVersionOmoduleId() {
        return curriculumVersionOmoduleId;
    }

    public void setCurriculumVersionOmoduleId(Long curriculumVersionOmoduleId) {
        this.curriculumVersionOmoduleId = curriculumVersionOmoduleId;
    }

    public Boolean getClosedCurriculumVersionModules() {
        return closedCurriculumVersionModules;
    }

    public void setClosedCurriculumVersionModules(Boolean closedCurriculumVersionModules) {
        this.closedCurriculumVersionModules = closedCurriculumVersionModules;
    }
    
}
