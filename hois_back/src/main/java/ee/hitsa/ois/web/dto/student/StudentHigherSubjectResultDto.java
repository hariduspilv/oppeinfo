package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private Boolean isNewApel;
    private Long replacedSubjectHigherModuleId;
    private BigDecimal recordTransferredCredits;
    private Boolean isAddedFromDirective;
    private BigDecimal consideredReplacedCredits;
    private Boolean leftOverCreditsResult;
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

    public static StudentHigherSubjectResultDto copy(StudentHigherSubjectResultDto dto) {
        StudentHigherSubjectResultDto copyDto = new StudentHigherSubjectResultDto();
        copyDto.setId(dto.getId());
        copyDto.setSubject(dto.getSubject());
        copyDto.setGrades(dto.getGrades());
        copyDto.setLastGrade(dto.getLastGrade());
        copyDto.setElectiveModule(dto.getElectiveModule());
        copyDto.setHigherModule(dto.getHigherModule());
        copyDto.setIsOptional(dto.getIsOptional());
        copyDto.setIsExtraCurriculum(dto.getIsExtraCurriculum());
        copyDto.setIsOk(dto.getIsOk());
        copyDto.setIsApelTransfer(dto.getIsApelTransfer());
        copyDto.setIsFormalLearning(dto.getIsFormalLearning());
        copyDto.setIsNewApel(dto.getIsNewApel());
        copyDto.setReplacedSubjectHigherModuleId(dto.getReplacedSubjectHigherModuleId());
        copyDto.setRecordTransferredCredits(dto.getRecordTransferredCredits());
        copyDto.setIsAddedFromDirective(dto.getIsAddedFromDirective());
        copyDto.setAllTeachers(dto.getAllTeachers());
        return copyDto;
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
            grades.sort(StreamUtil.comparingWithNullsLast(StudentHigherResultGradeDto::getIsActive).reversed());
            lastGrade = grades.get(0);
            isOk = Boolean.valueOf((lastGrade.getGrade() != null && HigherAssessment.isPositive(lastGrade.getGrade().getCode())) || showUncompleted);
        } else {
            isOk = this.isAddedFromDirective == null ? Boolean.FALSE : this.isAddedFromDirective;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsNewApel() {
        return isNewApel;
    }

    public void setIsNewApel(Boolean isNewApel) {
        this.isNewApel = isNewApel;
    }

    public Long getReplacedSubjectHigherModuleId() {
        return replacedSubjectHigherModuleId;
    }

    public void setReplacedSubjectHigherModuleId(Long replacedSubjectHigherModuleId) {
        this.replacedSubjectHigherModuleId = replacedSubjectHigherModuleId;
    }

    public BigDecimal getRecordTransferredCredits() {
        return recordTransferredCredits;
    }

    public void setRecordTransferredCredits(BigDecimal recordTransferredCredits) {
        this.recordTransferredCredits = recordTransferredCredits;
    }

    public BigDecimal getConsideredReplacedCredits() {
        return consideredReplacedCredits;
    }

    public void setConsideredReplacedCredits(BigDecimal consideredReplacedCredits) {
        this.consideredReplacedCredits = consideredReplacedCredits;
    }

    public Boolean getLeftOverCreditsResult() {
        return leftOverCreditsResult;
    }

    public void setLeftOverCreditsResult(Boolean leftOverCreditsResult) {
        this.leftOverCreditsResult = leftOverCreditsResult;
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
