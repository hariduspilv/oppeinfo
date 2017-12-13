package ee.hitsa.ois.web.dto.apelapplication;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;

public class ApelApplicationFormalSubjectOrModuleDto extends VersionedCommand {

    private Long id;
    private Boolean isMySchool;
    private String nameEt;
    private String nameEn;
    private String type;
    private ApelSchoolDto apelSchool;
    private Boolean isOptional;
    private SubjectDto subject;
    private CurriculumVersionHigherModuleDto curriculumVersionHmodule;
    private CurriculumVersionOccupationModuleDto curriculumVersionOmodule;
    private LocalDate gradeDate;
    private String grade;
    private String teachers;
    private String subjectCode;
    private BigDecimal credits;
    private String assessment;
    private Boolean transfer;
    
    public static ApelApplicationFormalSubjectOrModuleDto of(
            ApelApplicationFormalSubjectOrModule subjectOrModule) {
        ApelApplicationFormalSubjectOrModuleDto dto = EntityUtil.bindToDto(subjectOrModule,
                new ApelApplicationFormalSubjectOrModuleDto());
        dto.setApelSchool(subjectOrModule.getApelSchool() != null ? ApelSchoolDto.of(subjectOrModule.getApelSchool()) : null);
        dto.setSubject(subjectOrModule.getSubject() != null ? SubjectDto.of(subjectOrModule.getSubject(), null) : null);
        dto.setCurriculumVersionHmodule(subjectOrModule.getCurriculumVersionHmodule() != null 
                ? CurriculumVersionHigherModuleDto.of(subjectOrModule.getCurriculumVersionHmodule()) : null);
        dto.setCurriculumVersionOmodule(subjectOrModule.getCurriculumVersionOmodule() != null 
                ? CurriculumVersionOccupationModuleDto.forApelApplicationForm(subjectOrModule.getCurriculumVersionOmodule()) : null);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsMySchool() {
        return isMySchool;
    }

    public void setIsMySchool(Boolean isMySchool) {
        this.isMySchool = isMySchool;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ApelSchoolDto getApelSchool() {
        return apelSchool;
    }

    public void setApelSchool(ApelSchoolDto apelSchool) {
        this.apelSchool = apelSchool;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public CurriculumVersionHigherModuleDto getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(CurriculumVersionHigherModuleDto curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public CurriculumVersionOccupationModuleDto getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModuleDto curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }
    
}
