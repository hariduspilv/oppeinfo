package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*
     * For teacher container data
     */
    private Short studyLoad;
    private Boolean isStudyPeriodScheduleLoad;
    private Map<String, Integer> teacherPeriodVocationalCapacities;
    private Map<String, Integer> teacherYearVocationalCapacities;
    private List<ClassifierDto> vocationalCapacityTypes;

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

    public Short getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(Short studyLoad) {
        this.studyLoad = studyLoad;
    }

    public Boolean getIsStudyPeriodScheduleLoad() {
        return isStudyPeriodScheduleLoad;
    }

    public void setIsStudyPeriodScheduleLoad(Boolean studyPeriodScheduleLoad) {
        isStudyPeriodScheduleLoad = studyPeriodScheduleLoad;
    }

    public Map<String, Integer> getTeacherPeriodVocationalCapacities() {
        return teacherPeriodVocationalCapacities != null
                ? teacherPeriodVocationalCapacities : (teacherPeriodVocationalCapacities = new HashMap<>());
    }

    public void setTeacherPeriodVocationalCapacities(Map<String, Integer> teacherPeriodVocationalCapacities) {
        getTeacherPeriodVocationalCapacities().clear();
        getTeacherPeriodVocationalCapacities().putAll(teacherPeriodVocationalCapacities);
    }

    public Map<String, Integer> getTeacherYearVocationalCapacities() {
        return teacherYearVocationalCapacities != null
                ? teacherYearVocationalCapacities : (teacherYearVocationalCapacities = new HashMap<>());
    }

    public void setTeacherYearVocationalCapacities(Map<String, Integer> teacherYearVocationalCapacities) {
        getTeacherYearVocationalCapacities().clear();
        getTeacherYearVocationalCapacities().putAll(teacherYearVocationalCapacities);
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

    public List<ClassifierDto> getVocationalCapacityTypes() {
        return vocationalCapacityTypes != null ? vocationalCapacityTypes : (vocationalCapacityTypes = new ArrayList<>());
    }

    public void setVocationalCapacityTypes(List<ClassifierDto> vocationalCapacityTypes) {
        getVocationalCapacityTypes().clear();
        getVocationalCapacityTypes().addAll(vocationalCapacityTypes);
    }
}
