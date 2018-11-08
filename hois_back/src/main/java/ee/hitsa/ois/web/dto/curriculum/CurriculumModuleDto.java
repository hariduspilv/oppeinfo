package ee.hitsa.ois.web.dto.curriculum;

import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleForm;
import ee.hitsa.ois.web.dto.ClassifierSelection;

public class CurriculumModuleDto extends CurriculumModuleForm {

    private Long id;

    /**
     * for displaying CurriculumModuleForm.module variable
     */
    private ClassifierSelection type;
    private Boolean addedToImplementationPlan = Boolean.FALSE;
    
    /* variables related to curriculum */
    private Set<CurriculumOccupationDto> curriculumOccupations;
    private Boolean occupation;
    private Integer studyPeriod;
    private Boolean basicDataCanBeEdited;
    public Boolean canHaveOccupations;

    public static CurriculumModuleDto of(CurriculumModule module) {
        CurriculumModuleDto dto = EntityUtil.bindToDto
                (module, new CurriculumModuleDto(), "outcomes", "occupations", "competences");
        dto.setOutcomes(getOrderedOutcomes(module));
        dto.setOccupations(StreamUtil.toMappedSet(o -> EntityUtil.getNullableCode(o.getOccupation()), module.getOccupations()));
        dto.setCompetences(StreamUtil.toMappedSet(c -> EntityUtil.getNullableCode(c.getCompetence()), module.getCompetences()));
        dto.setAddedToImplementationPlan(Boolean.valueOf(!CollectionUtils.isEmpty(module.getCurriculumVersionOccupationModules())));
        
        Curriculum curriculum = module.getCurriculum();
        dto.setCurriculum(EntityUtil.getId(curriculum));
        dto.setCurriculumOccupations(StreamUtil.toMappedSet(CurriculumOccupationDto::of, curriculum.getOccupations()));
        dto.setBasicDataCanBeEdited(Boolean.valueOf(CurriculumUtil.basicDataCanBeEdited(curriculum)));
        dto.setOccupation(curriculum.getOccupation());
        dto.setCanHaveOccupations(Boolean.valueOf(CurriculumUtil.canHaveOccupations(curriculum)));
        if (module.getBaseModule() != null) {
            dto.setBaseModule(new CurriculumModuleForm.BaseModuleInfo(module.getBaseModule().getId(), module.getBaseModule().getNameEt(), module.getBaseModule().getNameEn()));
        }
        return dto;
    }
    
    /**
     * Is used on occupation module and occupation module themes' forms 
     * 
     * @return dto which only contains fields to display module name and to select outcomes
     */
    public static CurriculumModuleDto forOccupationModule(CurriculumModule module) {
        CurriculumModuleDto dto = new CurriculumModuleDto();
        dto.setId(EntityUtil.getId(module));
        dto.setNameEt(module.getNameEt());
        dto.setNameEn(module.getNameEn());
        dto.setCredits(module.getCredits());
        dto.setType(ClassifierSelection.of(module.getModule()));
        dto.setOutcomes(getOrderedOutcomes(module));
        dto.setAssessmentsEt(module.getAssessmentsEt());
        dto.setStudyPeriod(module.getCurriculum().getStudyPeriod());
        if (module.getBaseModule() != null) {
            dto.setBaseModule(new CurriculumModuleForm.BaseModuleInfo(module.getBaseModule().getId(), module.getBaseModule().getNameEt(), module.getBaseModule().getNameEn()));
        }
        return dto;
    }

    private static List<CurriculumModuleOutcomeDto> getOrderedOutcomes(CurriculumModule module) {
        return StreamUtil.toMappedList(CurriculumModuleOutcomeDto::of, module.getOutcomes().stream()
                .sorted(StreamUtil.comparingWithNullsLast(CurriculumModuleOutcome::getOrderNr)));
    }
    
    public Boolean getAddedToImplementationPlan() {
        return addedToImplementationPlan;
    }

    public void setAddedToImplementationPlan(Boolean addedToImplementationPlan) {
        this.addedToImplementationPlan = addedToImplementationPlan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CurriculumOccupationDto> getCurriculumOccupations() {
        return curriculumOccupations;
    }

    public void setCurriculumOccupations(Set<CurriculumOccupationDto> curriculumOccupations) {
        this.curriculumOccupations = curriculumOccupations;
    }

    public Boolean getBasicDataCanBeEdited() {
        return basicDataCanBeEdited;
    }

    public void setBasicDataCanBeEdited(Boolean basicDataCanBeEdited) {
        this.basicDataCanBeEdited = basicDataCanBeEdited;
    }

    public ClassifierSelection getType() {
        return type;
    }

    public void setType(ClassifierSelection type) {
        this.type = type;
    }

    public Boolean getOccupation() {
        return occupation;
    }

    public void setOccupation(Boolean occupation) {
        this.occupation = occupation;
    }

    public Integer getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Integer studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Boolean getCanHaveOccupations() {
        return canHaveOccupations;
    }

    public void setCanHaveOccupations(Boolean canHaveOccupations) {
        this.canHaveOccupations = canHaveOccupations;
    }
    
}
