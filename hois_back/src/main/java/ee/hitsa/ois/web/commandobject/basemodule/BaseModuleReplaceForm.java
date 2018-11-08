package ee.hitsa.ois.web.commandobject.basemodule;

import java.util.Map;
import java.util.Set;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleThemeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

public class BaseModuleReplaceForm {

    private AutocompleteResult baseModule;
    private Set<BaseModuleThemeDto> baseModuleThemes;
    private Set<AutocompleteResult> baseModuleOutcomes;
    private AutocompleteResult curriculumModule;
    private Set<CurriculumVersionOccupationModuleDto> occupationModules;
    private Map<Long, Set<CurriculumVersionOccupationModuleThemeDto>> themes;
    private Set<AutocompleteResult> outcomes;
    
    public AutocompleteResult getBaseModule() {
        return baseModule;
    }
    
    public void setBaseModule(AutocompleteResult baseModule) {
        this.baseModule = baseModule;
    }
    
    public Set<BaseModuleThemeDto> getBaseModuleThemes() {
        return baseModuleThemes;
    }

    public void setBaseModuleThemes(Set<BaseModuleThemeDto> baseModuleThemes) {
        this.baseModuleThemes = baseModuleThemes;
    }
    
    public Set<AutocompleteResult> getBaseModuleOutcomes() {
        return baseModuleOutcomes;
    }

    public void setBaseModuleOutcomes(Set<AutocompleteResult> baseModuleOutcomes) {
        this.baseModuleOutcomes = baseModuleOutcomes;
    }

    public AutocompleteResult getCurriculumModule() {
        return curriculumModule;
    }
    
    public void setCurriculumModule(AutocompleteResult curriculumModule) {
        this.curriculumModule = curriculumModule;
    }
    
    public Set<CurriculumVersionOccupationModuleDto> getOccupationModules() {
        return occupationModules;
    }

    public void setOccupationModules(Set<CurriculumVersionOccupationModuleDto> occupationModules) {
        this.occupationModules = occupationModules;
    }

    public Map<Long, Set<CurriculumVersionOccupationModuleThemeDto>> getThemes() {
        return themes;
    }
    
    public void setThemes(Map<Long, Set<CurriculumVersionOccupationModuleThemeDto>> themes) {
        this.themes = themes;
    }

    public Set<AutocompleteResult> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Set<AutocompleteResult> outcomes) {
        this.outcomes = outcomes;
    }
}
