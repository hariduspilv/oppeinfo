package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SubjectStudyPeriodDtoContainer {

    @NotNull
    private Long studyPeriod;

    @NotNull
    private Long studentGroup;

    @Valid
    private List<SubjectStudyPeriodDto> subjectStudyPeriodDtos;
    
    private List<AutocompleteResult> subjects;

    private List<SubjectStudyPeriodPlanDto> subjectStudyPeriodPlans;

    public List<AutocompleteResult> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<AutocompleteResult> subjects) {
        this.subjects = subjects;
    }

    public List<SubjectStudyPeriodPlanDto> getSubjectStudyPeriodPlans() {
        return subjectStudyPeriodPlans;
    }

    public void setSubjectStudyPeriodPlans(List<SubjectStudyPeriodPlanDto> subjectStudyPeriodPlans) {
        this.subjectStudyPeriodPlans = subjectStudyPeriodPlans;
    }

    public List<SubjectStudyPeriodDto> getSubjectStudyPeriodDtos() {
        return subjectStudyPeriodDtos != null ? subjectStudyPeriodDtos : (subjectStudyPeriodDtos = new ArrayList<>());
    }

    public void setSubjectStudyPeriodDtos(List<SubjectStudyPeriodDto> subjectStudyPeriodDtos) {
        this.subjectStudyPeriodDtos = subjectStudyPeriodDtos;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
}
