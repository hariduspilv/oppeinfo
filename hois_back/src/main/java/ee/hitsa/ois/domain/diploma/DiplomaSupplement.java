package ee.hitsa.ois.domain.diploma;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.student.Student;

@Entity
public class DiplomaSupplement extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Diploma diploma;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    private String schoolNameEt;
    private String schoolNameEn;
    private String firstname;
    private String lastname;
    private String idcode;
    private LocalDate birthdate;
    private String curriculumNameEt;
    private String curriculumNameEn;
    private String merCode;
    private String ekr;
    private BigDecimal credits;
    private String vocationalCurriculumType;
    private String studyFormNameEt;
    private String studyFormNameEn;
    private String studyLanguageNameEt;
    private String studyLanguageNameEn;
    private String outcomesEt;
    private String studyCompany;
    @Column(name = "signer1_name")
    private String signer1Name;
    @Column(name = "signer1_position")
    private String signer1Position;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    private Integer pgNrEt;
    private Integer pgNrEn;
    @OneToMany(mappedBy = "diplomaSupplement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiplomaSupplementStudyResult> studyResults;
    
    public Diploma getDiploma() {
        return diploma;
    }
    public void setDiploma(Diploma diploma) {
        this.diploma = diploma;
    }
    
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public String getSchoolNameEt() {
        return schoolNameEt;
    }
    public void setSchoolNameEt(String schoolNameEt) {
        this.schoolNameEt = schoolNameEt;
    }
    
    public String getSchoolNameEn() {
        return schoolNameEn;
    }
    public void setSchoolNameEn(String schoolNameEn) {
        this.schoolNameEn = schoolNameEn;
    }
    
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    
    public LocalDate getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    
    public String getCurriculumNameEt() {
        return curriculumNameEt;
    }
    public void setCurriculumNameEt(String curriculumNameEt) {
        this.curriculumNameEt = curriculumNameEt;
    }
    
    public String getCurriculumNameEn() {
        return curriculumNameEn;
    }
    public void setCurriculumNameEn(String curriculumNameEn) {
        this.curriculumNameEn = curriculumNameEn;
    }
    
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    
    public String getEkr() {
        return ekr;
    }
    public void setEkr(String ekr) {
        this.ekr = ekr;
    }
    
    public BigDecimal getCredits() {
        return credits;
    }
    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }
    
    public String getVocationalCurriculumType() {
        return vocationalCurriculumType;
    }
    public void setVocationalCurriculumType(String vocationalCurriculumType) {
        this.vocationalCurriculumType = vocationalCurriculumType;
    }
    
    public String getStudyFormNameEt() {
        return studyFormNameEt;
    }
    public void setStudyFormNameEt(String studyFormNameEt) {
        this.studyFormNameEt = studyFormNameEt;
    }
    
    public String getStudyFormNameEn() {
        return studyFormNameEn;
    }
    public void setStudyFormNameEn(String studyFormNameEn) {
        this.studyFormNameEn = studyFormNameEn;
    }
    
    public String getStudyLanguageNameEt() {
        return studyLanguageNameEt;
    }
    public void setStudyLanguageNameEt(String studyLanguageNameEt) {
        this.studyLanguageNameEt = studyLanguageNameEt;
    }
    
    public String getStudyLanguageNameEn() {
        return studyLanguageNameEn;
    }
    public void setStudyLanguageNameEn(String studyLanguageNameEn) {
        this.studyLanguageNameEn = studyLanguageNameEn;
    }
    
    public String getOutcomesEt() {
        return outcomesEt;
    }
    public void setOutcomesEt(String outcomesEt) {
        this.outcomesEt = outcomesEt;
    }
    
    public String getStudyCompany() {
        return studyCompany;
    }
    public void setStudyCompany(String studyCompany) {
        this.studyCompany = studyCompany;
    }
    
    public String getSigner1Name() {
        return signer1Name;
    }
    public void setSigner1Name(String signer1Name) {
        this.signer1Name = signer1Name;
    }
    
    public String getSigner1Position() {
        return signer1Position;
    }
    public void setSigner1Position(String signer1Position) {
        this.signer1Position = signer1Position;
    }
    
    public Classifier getStatus() {
        return status;
    }
    public void setStatus(Classifier status) {
        this.status = status;
    }
    
    public Integer getPgNrEt() {
        return pgNrEt;
    }
    public void setPgNrEt(Integer pgNrEt) {
        this.pgNrEt = pgNrEt;
    }
    
    public Integer getPgNrEn() {
        return pgNrEn;
    }
    public void setPgNrEn(Integer pgNrEn) {
        this.pgNrEn = pgNrEn;
    }
    
    public List<DiplomaSupplementStudyResult> getStudyResults() {
        return studyResults;
    }
    public void setStudyResults(List<DiplomaSupplementStudyResult> studyResults) {
        this.studyResults = studyResults;
    }
    
}
