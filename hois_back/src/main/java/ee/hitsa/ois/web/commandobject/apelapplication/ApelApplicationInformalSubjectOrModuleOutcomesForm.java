package ee.hitsa.ois.web.commandobject.apelapplication;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationInformalSubjectOrModuleOutcomesForm extends InsertedChangedVersionDto {
    
    private Long id;
    @NotNull
    private AutocompleteResult curriculumModuleOutcomes;
    @NotNull
    private Long apelApplicationInformalSubjectOrModule;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public AutocompleteResult getCurriculumModuleOutcomes() {
        return curriculumModuleOutcomes;
    }
    
    public void setCurriculumModuleOutcomes(AutocompleteResult curriculumModuleOutcomes) {
        this.curriculumModuleOutcomes = curriculumModuleOutcomes;
    }
    
    public Long getApelApplicationInformalSubjectOrModule() {
        return apelApplicationInformalSubjectOrModule;
    }
    
    public void setApelApplicationInformalSubjectOrModule(Long apelApplicationInformalSubjectOrModule) {
        this.apelApplicationInformalSubjectOrModule = apelApplicationInformalSubjectOrModule;
    }
    
}
