package ee.hitsa.ois.web.dto;

import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOutcome;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StateCurriculumModuleDto extends VersionedCommand {
    
    private Long id;
    @ClassifierRestriction(MainClassCode.KUTSEMOODUL)
    private String module;  
    private String nameEt;
    private String nameEn;
    private Integer credits;
    private String objectivesEt;
    private String objectivesEn;
    private String assessmentsEt;
    private String assessmentsEn;

    private Set<String> moduleOccupations = new HashSet<>();

    private String outcomesEt;
    private String outcomesEn;

    
    public static StateCurriculumModuleDto of(StateCurriculumModule module) {
        StateCurriculumModuleDto dto = EntityUtil.bindToDto
                (module, new StateCurriculumModuleDto(), "moduleOccupations", "outcomesEt", "outcomesEt", "outcome");

        dto.setModuleOccupations(StreamUtil.toMappedSet(o -> EntityUtil.getNullableCode(o.getOccupation()), module.getModuleOccupations()));
        StateCurriculumModuleOutcome outcome = module.getOutcome();
        if(outcome != null) {
            dto.setOutcomesEt(outcome.getOutcomesEt());
            dto.setOutcomesEn(outcome.getOutcomesEn());
        }

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
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

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
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

    public Set<String> getModuleOccupations() {
        return moduleOccupations != null ? moduleOccupations : (moduleOccupations = new HashSet<>());
    }

    public void setModuleOccupations(Set<String> moduleOccupations) {
        this.moduleOccupations = moduleOccupations;
    }

    public String getOutcomesEt() {
        return outcomesEt;
    }

    public void setOutcomesEt(String outcomesEt) {
        this.outcomesEt = outcomesEt;
    }
    
    public String getOutcomesEn() {
        return outcomesEn;
    }
    
    public void setOutcomesEn(String outcomesEn) {
        this.outcomesEn = outcomesEn;
    }
}
