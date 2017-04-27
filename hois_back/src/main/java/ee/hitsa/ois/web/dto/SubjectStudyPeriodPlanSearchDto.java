package ee.hitsa.ois.web.dto;

import java.util.Set;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodPlan;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

public class SubjectStudyPeriodPlanSearchDto {

    private Long id;
    private Set<AutocompleteResult> curriculums;
    private Set<AutocompleteResult> studyForms;
    private Set<SubjectStudyPeriodPlanCapacityDto> capacities;

    public static SubjectStudyPeriodPlanSearchDto of (SubjectStudyPeriodPlan plan) {
        SubjectStudyPeriodPlanSearchDto dto = new SubjectStudyPeriodPlanSearchDto();
        dto.setId(EntityUtil.getId(plan));
        dto.setCurriculums(StreamUtil.toMappedSet(c -> AutocompleteResult.of(c.getCurriculum()), plan.getCurriculums()));
        dto.setStudyForms(StreamUtil.toMappedSet(sf -> 
        new AutocompleteResult(null, sf.getStudyForm().getNameEt(), sf.getStudyForm().getNameEn()), plan.getStudyForms()));
        dto.setCapacities(StreamUtil.toMappedSet(SubjectStudyPeriodPlanCapacityDto::of, plan.getCapacities()));
        return dto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Set<AutocompleteResult> getCurriculums() {
        return curriculums;
    }
    public void setCurriculums(Set<AutocompleteResult> curriculums) {
        this.curriculums = curriculums;
    }
    public Set<AutocompleteResult> getStudyForms() {
        return studyForms;
    }
    public void setStudyForms(Set<AutocompleteResult> studyForms) {
        this.studyForms = studyForms;
    }
    public Set<SubjectStudyPeriodPlanCapacityDto> getCapacities() {
        return capacities;
    }
    public void setCapacities(Set<SubjectStudyPeriodPlanCapacityDto> capacities) {
        this.capacities = capacities;
    }
}
