package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.validation.StateCurriculumValidator.Vocational;
import ee.hitsa.ois.validation.StateCurriculumValidator.Secondary;

public class StateCurriculumModuleDto extends VersionedCommand {
    
    private Long id;
    @NotNull
    @ClassifierRestriction({MainClassCode.KUTSEMOODUL, MainClassCode.EHIS_AINE})
    private String module;  
    @NotBlank
    @Size(max=255)
    private String nameEt;
    @Size(max=255)
    private String nameEn;
    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal credits;
    @NotBlank
    @Size(max=10000)
    private String objectivesEt;
    @Size(max=10000)
    private String objectivesEn;
    @NotBlank
    @Size(max=20000)
    private String assessmentsEt;
    @Size(max=20000)
    private String assessmentsEn;
    /**
     * It is not obligatory, but it is false by default
     */
    private Boolean isAdditional = Boolean.FALSE;
    
    private Long coursesOrWeeks;
    
    private String syllabus;
    
    private String riigiteatajaUrl;
    
    @NotEmpty(groups = {Vocational.class})
    @ClassifierRestriction({MainClassCode.OSAKUTSE, MainClassCode.SPETSKUTSE, MainClassCode.KUTSE})
    private Set<String> moduleOccupations = new HashSet<>();

    @NotEmpty(groups = {Vocational.class})
    @Valid
    private Set<StateCurriculumModuleOutcomeDto> outcomes;
    
    @NotEmpty(groups = {Secondary.class})
    @Valid
    private Set<StateCurriculumModuleCompetenceDto> competences;
    
    public static StateCurriculumModuleDto of(StateCurriculumModule module) {
        StateCurriculumModuleDto dto = EntityUtil.bindToDto
                (module, new StateCurriculumModuleDto(), "moduleOccupations", "moduleCompetences", "outcomes");
        dto.setModuleOccupations(StreamUtil.toMappedSet(o -> EntityUtil.getNullableCode(o.getOccupation()), module.getModuleOccupations()));
        dto.setOutcomes(StreamUtil.toMappedSet(StateCurriculumModuleOutcomeDto::of, module.getOutcomes()));
        dto.setCompetences(StreamUtil.toMappedSet(StateCurriculumModuleCompetenceDto::of, module.getModuleCompetences()));
        Optional<ClassifierConnect> connect = ClassifierUtil.parentLinkFor(module.getModule(), MainClassCode.AINEVALDKOND);
        if (connect.isPresent()) {
            dto.setSyllabus(EntityUtil.getCode(connect.get().getConnectClassifier()));
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

    public Set<StateCurriculumModuleOutcomeDto> getOutcomes() {
        return outcomes != null ? outcomes : (outcomes = new HashSet<>());
    }

    public void setOutcomes(Set<StateCurriculumModuleOutcomeDto> outcomes) {
        this.outcomes = outcomes;
    }

    public Boolean getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(Boolean isAdditional) {
        this.isAdditional = isAdditional;
    }

    public Set<StateCurriculumModuleCompetenceDto> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<StateCurriculumModuleCompetenceDto> competences) {
        this.competences = competences;
    }

    public Long getCoursesOrWeeks() {
        return coursesOrWeeks;
    }

    public void setCoursesOrWeeks(Long coursesOrWeeks) {
        this.coursesOrWeeks = coursesOrWeeks;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getRiigiteatajaUrl() {
        return riigiteatajaUrl;
    }

    public void setRiigiteatajaUrl(String riigiteatajaUrl) {
        this.riigiteatajaUrl = riigiteatajaUrl;
    }
}
