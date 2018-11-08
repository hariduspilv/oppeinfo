package ee.hitsa.ois.web.commandobject.basemodule;

import java.util.Map;
import java.util.Set;

public class BaseModuleReplaceCommand {
    
    private Long baseModule;
    private Long curriculumModule;
    private Set<Long> curriculumVersionOModules;
    private Map<Long, Map<Long, Long>> themeReferences;
    private Map<Long, Long> outcomeReferences;
    
    public Long getBaseModule() {
        return baseModule;
    }
    
    public void setBaseModule(Long baseModule) {
        this.baseModule = baseModule;
    }
    
    public Long getCurriculumModule() {
        return curriculumModule;
    }
    
    public void setCurriculumModule(Long curriculumModule) {
        this.curriculumModule = curriculumModule;
    }
    
    public Set<Long> getCurriculumVersionOModules() {
        return curriculumVersionOModules;
    }
    
    public void setCurriculumVersionOModules(Set<Long> curriculumVersionOModules) {
        this.curriculumVersionOModules = curriculumVersionOModules;
    }
    
    public Map<Long, Map<Long, Long>> getThemeReferences() {
        return themeReferences;
    }
    
    public void setThemeReferences(Map<Long, Map<Long, Long>> themeReferences) {
        this.themeReferences = themeReferences;
    }

    public Map<Long, Long> getOutcomeReferences() {
        return outcomeReferences;
    }

    public void setOutcomeReferences(Map<Long, Long> outcomeReferences) {
        this.outcomeReferences = outcomeReferences;
    }
}
