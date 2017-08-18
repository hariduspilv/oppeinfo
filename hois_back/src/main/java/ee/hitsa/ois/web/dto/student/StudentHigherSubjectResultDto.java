package ee.hitsa.ois.web.dto.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.SubjectAssessment;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectSearchDto;

public class StudentHigherSubjectResultDto {
    private SubjectSearchDto subject;
    private List<StudentHigherSubjectResultGradeDto> grades = new ArrayList<>();
    private StudentHigherSubjectResultGradeDto lastGrade;
    private Long electiveModule;
    private AutocompleteResult higherModule;
    private Boolean isOptional;
    private Boolean isExtraCurriculum;
    private Boolean isOk;
    
    public static StudentHigherSubjectResultDto ofFromProtocolStudent(ProtocolStudent protocolStudent) {
        StudentHigherSubjectResultDto dto = new StudentHigherSubjectResultDto();
        dto.setSubject(getSubjectDto(protocolStudent.getProtocol().getProtocolHdata().getSubjectStudyPeriod().getSubject()));
        dto.setIsExtraCurriculum(Boolean.TRUE);
        dto.setIsOk(Boolean.FALSE);
        dto.setIsOptional(Boolean.TRUE);
        dto.getGrades().add(StudentHigherSubjectResultGradeDto.of(protocolStudent));
        return dto;
    }
    
    public static StudentHigherSubjectResultDto ofFromHigherModuleSubject(CurriculumVersionHigherModuleSubject higherModuleSubject) {
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
    
    private Comparator<StudentHigherSubjectResultGradeDto> byDate =
            (StudentHigherSubjectResultGradeDto lhs, StudentHigherSubjectResultGradeDto rhs)
            -> lhs.getGradeDate().compareTo(rhs.getGradeDate());
    
    public void calculateIsOk() {
        if(!CollectionUtils.isEmpty(grades)) {
            Collections.sort(grades, byDate);
            lastGrade = calculateLastGrade();
            isOk = Boolean.valueOf(lastGrade.getGrade() != null && HigherAssessment.isPositive(lastGrade.getGrade()));
        } else {
            isOk = Boolean.FALSE;
        }
    }
    
    private StudentHigherSubjectResultGradeDto calculateLastGrade() {
        for(int i = grades.size() - 1; i >= 0; i--) {
            StudentHigherSubjectResultGradeDto grade = grades.get(i);
            if(HigherAssessment.KORGHINDAMINE_MI.name().equals(grade.getGrade()) && i != 0) {
                continue;
            }
            return grades.get(i);
        }
        return null;
    }
    
    public boolean isDistinctiveAssessment() {
        return !SubjectAssessment.HINDAMISVIIS_A.name().equals(this.getSubject().getAssessment());
    }
    
    public boolean hasResult() {
        return this.getLastGrade() != null;
    }
    
    private static AutocompleteResult getHigherModuleDto(CurriculumVersionHigherModule module) {
        return new AutocompleteResult(EntityUtil.getId(module), module.getNameEt(), module.getNameEn());
    }

    public AutocompleteResult getHigherModule() {
        return higherModule;
    }

    public void setHigherModule(AutocompleteResult higherModule) {
        this.higherModule = higherModule;
    }

    public List<StudentHigherSubjectResultGradeDto> getGrades() {
        return grades;
    }
    public void setGrades(List<StudentHigherSubjectResultGradeDto> grades) {
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
    public StudentHigherSubjectResultGradeDto getLastGrade() {
        return lastGrade;
    }
    public void setLastGrade(StudentHigherSubjectResultGradeDto lastGrade) {
        this.lastGrade = lastGrade;
    }
}
