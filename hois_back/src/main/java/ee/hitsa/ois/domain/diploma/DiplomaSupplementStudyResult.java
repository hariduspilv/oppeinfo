package ee.hitsa.ois.domain.diploma;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class DiplomaSupplementStudyResult extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private DiplomaSupplement diplomaSupplement;
    private String nameEt;
    private String nameEn;
    private BigDecimal credits;
    private String grade;
    private String gradeNameEt;
    private String gradeNameEn;
    private LocalDate gradeDate;
    private String teacher;
    private Boolean isApelFormal;
    private Boolean isApelInformal;
    private String apelSchoolNameEt;
    private String apelSchoolNameEn;
    private Boolean isFinal;
    
    public DiplomaSupplement getDiplomaSupplement() {
        return diplomaSupplement;
    }
    public void setDiplomaSupplement(DiplomaSupplement diplomaSupplement) {
        this.diplomaSupplement = diplomaSupplement;
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
    public BigDecimal getCredits() {
        return credits;
    }
    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getGradeNameEt() {
        return gradeNameEt;
    }
    public void setGradeNameEt(String gradeNameEt) {
        this.gradeNameEt = gradeNameEt;
    }
    public String getGradeNameEn() {
        return gradeNameEn;
    }
    public void setGradeNameEn(String gradeNameEn) {
        this.gradeNameEn = gradeNameEn;
    }
    public LocalDate getGradeDate() {
        return gradeDate;
    }
    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public Boolean getIsApelFormal() {
        return isApelFormal;
    }
    public void setIsApelFormal(Boolean isApelFormal) {
        this.isApelFormal = isApelFormal;
    }
    public Boolean getIsApelInformal() {
        return isApelInformal;
    }
    public void setIsApelInformal(Boolean isApelInformal) {
        this.isApelInformal = isApelInformal;
    }
    public String getApelSchoolNameEt() {
        return apelSchoolNameEt;
    }
    public void setApelSchoolNameEt(String apelSchoolNameEt) {
        this.apelSchoolNameEt = apelSchoolNameEt;
    }
    public String getApelSchoolNameEn() {
        return apelSchoolNameEn;
    }
    public void setApelSchoolNameEn(String apelSchoolNameEn) {
        this.apelSchoolNameEn = apelSchoolNameEn;
    }
    public Boolean getIsFinal() {
        return isFinal;
    }
    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }
}
