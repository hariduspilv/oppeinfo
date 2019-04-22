package ee.hitsa.ois.domain.diploma;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate curriculumMerRegDate;
    private String curriculumCompletion;
    private String curriculumCompletionEn;
    private BigDecimal averageMark;
    private Integer studyPeriod;
    private String studyLoadNameEt;
    private String studyLoadNameEn;
    private String studyFormNameEt;
    private String studyFormNameEn;
    private String studyLanguageNameEt;
    private String studyLanguageNameEn;
    private String outcomesEt;
    private String outcomesEn;
    private String studyCompany;
    @Column(name = "final_21")
    private String final21;
    @Column(name = "final_31")
    private String final31;
    @Column(name = "final_33")
    private String final33;
    @Column(name = "final_51")
    private String final51;
    @Column(name = "final_52")
    private String final52;
    @Column(name = "final_61")
    private String final61;
    @Column(name = "final_62")
    private String final62;
    @Column(name = "final_en_21")
    private String finalEn21;
    @Column(name = "final_en_31")
    private String finalEn31;
    @Column(name = "final_en_33")
    private String finalEn33;
    @Column(name = "final_en_51")
    private String finalEn51;
    @Column(name = "final_en_52")
    private String finalEn52;
    @Column(name = "final_en_61")
    private String finalEn61;
    @Column(name = "final_en_62")
    private String finalEn62;
    private String schoolType;
    private String schoolTypeEn;
    @Column(name = "signer1_name")
    private String signer1Name;
    @Column(name = "signer1_position")
    private String signer1Position;
    @Column(name = "signer2_name")
    private String signer2Name;
    @Column(name = "signer2_position")
    private String signer2Position;
    @Column(name = "signer1_position_en")
    private String signer1PositionEn;
    @Column(name = "signer2_position_en")
    private String signer2PositionEn;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier statusEn;
    private LocalDate printed;
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
    
    public LocalDate getCurriculumMerRegDate() {
        return curriculumMerRegDate;
    }
    public void setCurriculumMerRegDate(LocalDate curriculumMerRegDate) {
        this.curriculumMerRegDate = curriculumMerRegDate;
    }
    
    public String getCurriculumCompletion() {
        return curriculumCompletion;
    }
    public void setCurriculumCompletion(String curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }
    
    public String getCurriculumCompletionEn() {
        return curriculumCompletionEn;
    }
    public void setCurriculumCompletionEn(String curriculumCompletionEn) {
        this.curriculumCompletionEn = curriculumCompletionEn;
    }
    
    public BigDecimal getAverageMark() {
        return averageMark;
    }
    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }
    
    public Integer getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Integer studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    
    public String getStudyLoadNameEt() {
        return studyLoadNameEt;
    }
    public void setStudyLoadNameEt(String studyLoadNameEt) {
        this.studyLoadNameEt = studyLoadNameEt;
    }
    
    public String getStudyLoadNameEn() {
        return studyLoadNameEn;
    }
    public void setStudyLoadNameEn(String studyLoadNameEn) {
        this.studyLoadNameEn = studyLoadNameEn;
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
    
    public String getOutcomesEn() {
        return outcomesEn;
    }
    public void setOutcomesEn(String outcomesEn) {
        this.outcomesEn = outcomesEn;
    }
    
    public String getStudyCompany() {
        return studyCompany;
    }
    public void setStudyCompany(String studyCompany) {
        this.studyCompany = studyCompany;
    }
    
    public String getFinal21() {
        return final21;
    }
    public void setFinal21(String final21) {
        this.final21 = final21;
    }
    public String getFinal31() {
        return final31;
    }
    public void setFinal31(String final31) {
        this.final31 = final31;
    }
    public String getFinal33() {
        return final33;
    }
    public void setFinal33(String final33) {
        this.final33 = final33;
    }
    public String getFinal51() {
        return final51;
    }
    public void setFinal51(String final51) {
        this.final51 = final51;
    }
    public String getFinal52() {
        return final52;
    }
    public void setFinal52(String final52) {
        this.final52 = final52;
    }
    public String getFinal61() {
        return final61;
    }
    public void setFinal61(String final61) {
        this.final61 = final61;
    }
    public String getFinal62() {
        return final62;
    }
    public void setFinal62(String final62) {
        this.final62 = final62;
    }
    
    public String getFinalEn21() {
        return finalEn21;
    }
    public void setFinalEn21(String finalEn21) {
        this.finalEn21 = finalEn21;
    }
    public String getFinalEn31() {
        return finalEn31;
    }
    public void setFinalEn31(String finalEn31) {
        this.finalEn31 = finalEn31;
    }
    public String getFinalEn33() {
        return finalEn33;
    }
    public void setFinalEn33(String finalEn33) {
        this.finalEn33 = finalEn33;
    }
    public String getFinalEn51() {
        return finalEn51;
    }
    public void setFinalEn51(String finalEn51) {
        this.finalEn51 = finalEn51;
    }
    public String getFinalEn52() {
        return finalEn52;
    }
    public void setFinalEn52(String finalEn52) {
        this.finalEn52 = finalEn52;
    }
    public String getFinalEn61() {
        return finalEn61;
    }
    public void setFinalEn61(String finalEn61) {
        this.finalEn61 = finalEn61;
    }
    public String getFinalEn62() {
        return finalEn62;
    }
    public void setFinalEn62(String finalEn62) {
        this.finalEn62 = finalEn62;
    }
    
    public String getSchoolType() {
        return schoolType;
    }
    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }
    
    public String getSchoolTypeEn() {
        return schoolTypeEn;
    }
    public void setSchoolTypeEn(String schoolTypeEn) {
        this.schoolTypeEn = schoolTypeEn;
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
    
    public String getSigner2Name() {
        return signer2Name;
    }
    public void setSigner2Name(String signer2Name) {
        this.signer2Name = signer2Name;
    }
    
    public String getSigner2Position() {
        return signer2Position;
    }
    public void setSigner2Position(String signer2Position) {
        this.signer2Position = signer2Position;
    }
    
    public String getSigner1PositionEn() {
        return signer1PositionEn;
    }
    public void setSigner1PositionEn(String signer1PositionEn) {
        this.signer1PositionEn = signer1PositionEn;
    }
    
    public String getSigner2PositionEn() {
        return signer2PositionEn;
    }
    public void setSigner2PositionEn(String signer2PositionEn) {
        this.signer2PositionEn = signer2PositionEn;
    }
    
    public Classifier getStatus() {
        return status;
    }
    public void setStatus(Classifier status) {
        this.status = status;
    }
    
    public Classifier getStatusEn() {
        return statusEn;
    }
    public void setStatusEn(Classifier statusEn) {
        this.statusEn = statusEn;
    }
    
    public LocalDate getPrinted() {
        return printed;
    }
    public void setPrinted(LocalDate printed) {
        this.printed = printed;
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
        return studyResults != null ? studyResults : (studyResults = new ArrayList<>());
    }
    
    public void setStudyResults(List<DiplomaSupplementStudyResult> studyResults) {
        this.studyResults = studyResults;
    }
    
}
