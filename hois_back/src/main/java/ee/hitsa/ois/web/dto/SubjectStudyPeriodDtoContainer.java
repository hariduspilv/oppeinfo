package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SubjectStudyPeriodDtoContainer {

    @NotNull
    private Long studyPeriod;
    
    /*
     * For student group form studentGroup must not be null, and
     * for teachers form teacher must not be null
     * 
     * As single container class is used for both forms, @NotNull annotations are not used.
     * Appropriate checks are done in controller methods instead.
     */
    private Long studentGroup;    
    private Long teacher;
    private Long subject;

    @Valid
    private List<SubjectStudyPeriodDto> subjectStudyPeriodDtos;
    
    private List<AutocompleteResult> subjects;

    private List<SubjectStudyPeriodPlanDto> subjectStudyPeriodPlans;
    
    private List<ClassifierDto> capacityTypes;

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

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

    public List<ClassifierDto> getCapacityTypes() {
        return capacityTypes;
    }

    public void setCapacityTypes(List<ClassifierDto> capacityTypes) {
        this.capacityTypes = capacityTypes;
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
