package ee.hitsa.ois.web.commandobject.apelapplication;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationFormalSubjectOrModuleForm extends InsertedChangedVersionDto {

    private Long id;
    
    @NotNull
    private Boolean isMySchool;
    
    @NotNull
    @ClassifierRestriction(MainClassCode.VOTA_AINE_LIIK)
    private String type;
    
    private Long apelSchool;
    
    private Boolean isOptional;
    private Long subject;
    private Long curriculumVersionHmodule;
    private Long curriculumVersionOmodule;
    
    @NotNull
    @ClassifierRestriction({MainClassCode.KUTSEHINDAMINE, MainClassCode.KORGHINDAMINE})
    private String grade;
    
    private LocalDate gradeDate;

    @Size(max = 255)
    private String teachers;
    
    private Boolean transfer;
    
    @Size(max = 255)
    private String nameEt;
    
    @Size(max = 255)
    private String nameEn;
    
    @Size(max = 20)
    private String subjectCode;
    
    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal credits;
    
    @NotNull
    @ClassifierRestriction({MainClassCode.HINDAMISVIIS, MainClassCode.KUTSEHINDAMISVIIS})
    private String assessment;
    
    private ApelSchoolForm newApelSchool;
    
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getApelSchool() {
        return apelSchool;
    }

    public void setApelSchool(Long apelSchool) {
        this.apelSchool = apelSchool;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }
    
    public Long getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(Long curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public Long getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(Long curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
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

    public ApelSchoolForm getNewApelSchool() {
        return newApelSchool;
    }

    public void setNewApelSchool(ApelSchoolForm newApelSchool) {
        this.newApelSchool = newApelSchool;
    }
    
}
