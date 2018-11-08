package ee.hitsa.ois.web.commandobject.curriculum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeDto;

public class CurriculumModuleForm extends VersionedCommand {

    @Required
    @ClassifierRestriction(MainClassCode.KUTSEMOODUL)
    private String module;
    
    @NotNull
    private Long curriculum;
    private BaseModuleInfo baseModule;

    @NotNull
    @Size(max=255)
    private String nameEt;
    @Size(max=255)
    private String nameEn;

    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal credits;

    @NotNull
    @Size(max=10000)
    private String objectivesEt;
    @Size(max=10000)
    private String objectivesEn;

    @NotNull
    private Boolean practice;
    @ClassifierRestriction({MainClassCode.KUTSE, MainClassCode.OSAKUTSE, MainClassCode.SPETSKUTSE})
    private Set<String> occupations;
    @ClassifierRestriction(MainClassCode.KOMPETENTS)
    private Set<String> competences;
    
    /**
     * It is not obligatory, but it is false by default
     */
    private Boolean isAdditional = Boolean.FALSE;
    
    @Size(max=20000)
    private String assessmentsEt;
    @Size(max=20000)
    private String assessmentsEn;
    
    @Required
    private List<CurriculumModuleOutcomeDto> outcomes;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getObjectivesEt() {
        return objectivesEt;
    }

    public void setObjectivesEt(String objectivesEt) {
        this.objectivesEt = objectivesEt;
    }

    public String getObjectivesEn() {
        return objectivesEn;
    }

    public void setObjectivesEn(String objectivesEn) {
        this.objectivesEn = objectivesEn;
    }

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

    public Set<String> getOccupations() {
        return occupations;
    }

    public void setOccupations(Set<String> occupations) {
        this.occupations = occupations;
    }

    public Set<String> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<String> competences) {
        this.competences = competences;
    }

    public List<CurriculumModuleOutcomeDto> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<CurriculumModuleOutcomeDto> outcomes) {
        this.outcomes = outcomes;
    }

    public Boolean getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(Boolean isAdditional) {
        this.isAdditional = isAdditional;
    }

    public String getAssessmentsEt() {
        return assessmentsEt;
    }

    public void setAssessmentsEt(String assessmentsEt) {
        this.assessmentsEt = assessmentsEt;
    }

    public String getAssessmentsEn() {
        return assessmentsEn;
    }

    public void setAssessmentsEn(String assessmentsEn) {
        this.assessmentsEn = assessmentsEn;
    }

    public BaseModuleInfo getBaseModule() {
        return baseModule;
    }

    public void setBaseModule(BaseModuleInfo baseModule) {
        this.baseModule = baseModule;
    }
    
    public static class BaseModuleInfo {
        
        private Long id;
        private String nameEt;
        private String nameEn;
        
        public BaseModuleInfo() {
            
        }
        
        public BaseModuleInfo(Long id, String nameEt, String nameEn) {
            this.id = id;
            this.nameEt = nameEt;
            this.nameEn = nameEn;
        }

        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getNameEt() {
            return nameEt;
        }
        
        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }
        
        public String getNameEn() {
            return nameEn;
        }
        
        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }
    }
}
