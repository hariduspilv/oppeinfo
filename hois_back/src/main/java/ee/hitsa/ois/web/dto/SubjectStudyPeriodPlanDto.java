package ee.hitsa.ois.web.dto;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class SubjectStudyPeriodPlanDto extends VersionedCommand {
    private Long id;
    @NotNull
    private Long studyPeriod;
    @NotNull
    private Long subject;
    private Set<String> studyForms;
    private Set<Long> curriculums;
    @Valid
    private Set<SubjectStudyPeriodPlanCapacityDto> capacities;
    
    public static SubjectStudyPeriodPlanDto of(SubjectStudyPeriodPlan plan) {
        SubjectStudyPeriodPlanDto dto = new SubjectStudyPeriodPlanDto();
        dto.setId(EntityUtil.getId(plan));
        dto.setStudyPeriod(EntityUtil.getId(plan.getStudyPeriod()));
        dto.setSubject(EntityUtil.getId(plan.getSubject()));
        dto.setVersion(plan.getVersion());
        dto.setCurriculums(StreamUtil.toMappedSet(c -> EntityUtil.getId(c.getCurriculum()), plan.getCurriculums()));
        dto.setStudyForms(StreamUtil.toMappedSet(sf -> EntityUtil.getCode(sf.getStudyForm()), plan.getStudyForms()));
        dto.setCapacities(StreamUtil.toMappedSet(SubjectStudyPeriodPlanCapacityDto::of, plan.getCapacities()));
        return dto;
    }

    public Set<String> getStudyForms() {
        return studyForms;
    }

    public void setStudyForms(Set<String> studyForms) {
        this.studyForms = studyForms;
    }

    public Set<Long> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(Set<Long> curriculums) {
        this.curriculums = curriculums;
    }

    public Set<SubjectStudyPeriodPlanCapacityDto> getCapacities() {
        return capacities;
    }

    public void setCapacities(Set<SubjectStudyPeriodPlanCapacityDto> capacities) {
        this.capacities = capacities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }
}
