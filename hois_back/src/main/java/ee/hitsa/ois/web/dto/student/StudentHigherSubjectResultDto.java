package ee.hitsa.ois.web.dto.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectSearchDto;

public class StudentHigherSubjectResultDto {

    private Long id;
    private SubjectSearchDto subject;
    private List<StudentHigherResultGradeDto> grades = new ArrayList<>();
    private StudentHigherResultGradeDto lastGrade;
    private Long electiveModule;
    private AutocompleteResult higherModule;
    private Boolean isOptional;
    private Boolean isExtraCurriculum;
    private Boolean isOk;
    private Boolean isApelTransfer;
    private Boolean isFormalLearning;
    private Boolean isAddedFromDirective;
    private List<SubjectResultReplacedSubjectDto> replacedSubjects = new ArrayList<>();
    private String allTeachers;

    public static StudentHigherSubjectResultDto ofHigherModuleSubject(CurriculumVersionHigherModuleSubject higherModuleSubject) {
        StudentHigherSubjectResultDto dto = new StudentHigherSubjectResultDto();
        dto.setSubject(getSubjectDto(higherModuleSubject.getSubject()));
        dto.setIsExtraCurriculum(Boolean.FALSE);
        dto.setIsOk(Boolean.FALSE);
        dto.setIsOptional(higherModuleSubject.getOptional());
        dto.setElectiveModule(EntityUtil.getNullableId(higherModuleSubject.getElectiveModule()));
        dto.setHigherModule(getHigherModuleDto(higherModuleSubject.getModule()));
        return dto;
    }

    private static SubjectSearchDto getSubjectDto(Subject subject) {
        SubjectSearchDto subjectDto = new SubjectSearchDto();
        subjectDto.setId(EntityUtil.getId(subject));
        subjectDto.setNameEt(subject.getNameEt());
        subjectDto.setNameEn(subject.getNameEn());
        subjectDto.setCode(subject.getCode());
        subjectDto.setCredits(subject.getCredits());
        subjectDto.setAssessment(EntityUtil.getCode(subject.getAssessment()));
        return subjectDto;
    }

    public void calculateIsOk(boolean showUncompleted) {
        if(grades != null && !grades.isEmpty()) {
            Collections.sort(grades, StreamUtil.comparingWithNullsLast(StudentHigherResultGradeDto::getIsActive));
            lastGrade = calculateLastGrade(showUncompleted);
            isOk = Boolean.valueOf((lastGrade.getGrade() != null && HigherAssessment.isPositive(lastGrade.getGrade())) || showUncompleted);
        } else {
            isOk = this.isAddedFromDirective == null ? Boolean.FALSE : this.isAddedFromDirective;
        }
    }

    private StudentHigherResultGradeDto calculateLastGrade(boolean showUncompleted) {
        for(int i = grades.size() - 1; i >= 0; i--) {
            StudentHigherResultGradeDto grade = grades.get(i);
            if(HigherAssessment.KORGHINDAMINE_MI.name().equals(grade.getGrade()) && i != 0 && !showUncompleted) {
                continue;
            }
            return grades.get(i);
        }
        return null;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean hasResult() {
        return this.getLastGrade() != null;
    }

    public static AutocompleteResult getHigherModuleDto(CurriculumVersionHigherModule module) {
        return new AutocompleteResult(EntityUtil.getId(module), module.getNameEt(), module.getNameEn());
    }

    public AutocompleteResult getHigherModule() {
        return higherModule;
    }

    public void setHigherModule(AutocompleteResult higherModule) {
        this.higherModule = higherModule;
    }

    public List<StudentHigherResultGradeDto> getGrades() {
        return grades;
    }

    public void setGrades(List<StudentHigherResultGradeDto> grades) {
        this.grades = grades;
    }

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

    public SubjectSearchDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectSearchDto subject) {
        this.subject = subject;
    }

    public Long getElectiveModule() {
        return electiveModule;
    }

    public void setElectiveModule(Long electiveModule) {
        this.electiveModule = electiveModule;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public Boolean getIsExtraCurriculum() {
        return isExtraCurriculum;
    }

    public void setIsExtraCurriculum(Boolean isExtraCurriculum) {
        this.isExtraCurriculum = isExtraCurriculum;
    }

    public StudentHigherResultGradeDto getLastGrade() {
        return lastGrade;
    }

    public void setLastGrade(StudentHigherResultGradeDto lastGrade) {
        this.lastGrade = lastGrade;
    }

    public Boolean getIsApelTransfer() {
        return isApelTransfer;
    }

    public void setIsApelTransfer(Boolean isApelTransfer) {
        this.isApelTransfer = isApelTransfer;
    }

    public Boolean getIsFormalLearning() {
        return isFormalLearning;
    }

    public void setIsFormalLearning(Boolean isFormalLearning) {
        this.isFormalLearning = isFormalLearning;
    }

    public List<SubjectResultReplacedSubjectDto> getReplacedSubjects() {
        return replacedSubjects;
    }

    public void setReplacedSubjects(List<SubjectResultReplacedSubjectDto> replacedSubjects) {
        this.replacedSubjects = replacedSubjects;
    }

    public String getAllTeachers() {
        return allTeachers;
    }

    public void setAllTeachers(String allTeachers) {
        this.allTeachers = allTeachers;
    }

    public Boolean getIsAddedFromDirective() {
        return isAddedFromDirective;
    }

    public void setIsAddedFromDirective(Boolean isAddedFromDirective) {
        this.isAddedFromDirective = isAddedFromDirective;
    }

}
