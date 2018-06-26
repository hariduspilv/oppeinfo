package ee.hitsa.ois.domain.diploma;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;

@Entity
public class Diploma extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private String firstname;
    private String lastname;
    private String idcode;
    private LocalDate birthdate;
    private Boolean isCumLaude;
    private String merCode;
    private String curriculumNameEt;
    private String schoolNameEt;
    private String schoolNameGenitiveEt;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Classifier type;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Directive directive;
    @ManyToOne(fetch = FetchType.LAZY)
    private Form form;
    private String level;
    @Column(name = "signer1_name")
    private String signer1Name;
    @Column(name = "signer1_position")
    private String signer1Position;
    @Column(name = "signer2_name")
    private String signer2Name;
    @Column(name = "signer2_position")
    private String signer2Position;
    private String occupationText;
    private String partoccupationText;
    private Boolean isOccupation;
    private Boolean isPartoccupation;
    private String curriculumGradeNameEt;
    private String curriculumGradeNameEn;
    private String city;
    
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
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
    
    public Boolean getIsCumLaude() {
        return isCumLaude;
    }
    public void setIsCumLaude(Boolean isCumLaude) {
        this.isCumLaude = isCumLaude;
    }
    
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    
    public String getCurriculumNameEt() {
        return curriculumNameEt;
    }
    public void setCurriculumNameEt(String curriculumNameEt) {
        this.curriculumNameEt = curriculumNameEt;
    }
    
    public String getSchoolNameEt() {
        return schoolNameEt;
    }
    public void setSchoolNameEt(String schoolNameEt) {
        this.schoolNameEt = schoolNameEt;
    }
    
    public String getSchoolNameGenitiveEt() {
        return schoolNameGenitiveEt;
    }
    public void setSchoolNameGenitiveEt(String schoolNameGenitiveEt) {
        this.schoolNameGenitiveEt = schoolNameGenitiveEt;
    }
    
    public Classifier getType() {
        return type;
    }
    public void setType(Classifier type) {
        this.type = type;
    }
    
    public Classifier getStatus() {
        return status;
    }
    public void setStatus(Classifier status) {
        this.status = status;
    }
    
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Directive getDirective() {
        return directive;
    }
    public void setDirective(Directive directive) {
        this.directive = directive;
    }
    
    public Form getForm() {
        return form;
    }
    public void setForm(Form form) {
        this.form = form;
    }
    
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
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
    
    public String getOccupationText() {
        return occupationText;
    }
    public void setOccupationText(String occupationText) {
        this.occupationText = occupationText;
    }
    
    public String getPartoccupationText() {
        return partoccupationText;
    }
    public void setPartoccupationText(String partoccupationText) {
        this.partoccupationText = partoccupationText;
    }
    
    public Boolean getIsOccupation() {
        return isOccupation;
    }
    public void setIsOccupation(Boolean isOccupation) {
        this.isOccupation = isOccupation;
    }
    
    public Boolean getIsPartoccupation() {
        return isPartoccupation;
    }
    public void setIsPartoccupation(Boolean isPartoccupation) {
        this.isPartoccupation = isPartoccupation;
    }
    
    public String getCurriculumGradeNameEt() {
        return curriculumGradeNameEt;
    }
    public void setCurriculumGradeNameEt(String curriculumGradeNameEt) {
        this.curriculumGradeNameEt = curriculumGradeNameEt;
    }
    
    public String getCurriculumGradeNameEn() {
        return curriculumGradeNameEn;
    }
    public void setCurriculumGradeNameEn(String curriculumGradeNameEn) {
        this.curriculumGradeNameEn = curriculumGradeNameEn;
    }
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
}
