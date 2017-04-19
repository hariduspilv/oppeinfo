package ee.hitsa.ois.web.dto.curriculum;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumModuleDto extends VersionedCommand {

    private Long id;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.KUTSEMOODUL)
    private String module;

    @NotNull
    private String nameEt;
    private String nameEn;

    @NotNull
    @Min(0)
    private Integer credits;

    @NotNull
    private String objectivesEt;
    private String objectivesEn;

    @NotNull
    private Boolean practice;

    private Set<String> occupations;

    private Set<String> competences;

    private Set<CurriculumModuleOutcomeDto> outcomes;

    public static CurriculumModuleDto of(CurriculumModule module) {
        CurriculumModuleDto dto = EntityUtil.bindToDto
                (module, new CurriculumModuleDto(), "outcomes", "occupations", "competences");

        dto.setOutcomes(StreamUtil.toMappedSet(CurriculumModuleOutcomeDto::of, module.getOutcomes()));
        dto.setOccupations(StreamUtil.toMappedSet(o -> EntityUtil.getNullableCode(o.getOccupation()), module.getOccupations()));
        dto.setCompetences(StreamUtil.toMappedSet(c -> EntityUtil.getNullableCode(c.getCompetence()), module.getCompetences()));

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

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

    public Set<CurriculumModuleOutcomeDto> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Set<CurriculumModuleOutcomeDto> outcomes) {
        this.outcomes = outcomes;
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
}
