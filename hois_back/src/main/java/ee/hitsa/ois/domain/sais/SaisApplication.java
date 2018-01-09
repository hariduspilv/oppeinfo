package ee.hitsa.ois.domain.sais;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.validation.Required;

@Entity
public class SaisApplication extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private SaisAdmission saisAdmission;

    @Column(nullable = false)
    private LocalDate submitted;
    private LocalDate saisChanged;

    @Required
    @Size(max = 100)
    private String firstname;

    @Required
    @Size(max = 100)
    private String lastname;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Size(max = 11)
    private String idcode;

    @Size(max = 50)
    private String foreignIdcode;

    @Size(max = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier sex;

    @Size(max = 100)
    private String phone;

    @Size(max = 100)
    private String email;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier fin;

    private BigDecimal points;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;

    @Required
    @Size(max = 100)
    private String applicationNr;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier citizenship;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyLoad;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier residenceCountry;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier studyForm;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier language;

    @Size(max = 50)
    private String saisId;
    @Size(max = 50)
    private String addressAds;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sais_application_id", nullable = false, updatable = false)
    private Set<SaisApplicationGraduatedSchool> graduatedSchools = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sais_application_id", nullable = false, updatable = false)
    private Set<SaisApplicationGrade> grades = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sais_application_id", nullable = false, updatable = false)
    private Set<SaisApplicationOtherData> otherData = new HashSet<>();

    public SaisAdmission getSaisAdmission() {
        return saisAdmission;
    }

    public void setSaisAdmission(SaisAdmission saisAdmission) {
        this.saisAdmission = saisAdmission;
    }

    public LocalDate getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDate submitted) {
        this.submitted = submitted;
    }

    public LocalDate getSaisChanged() {
        return saisChanged;
    }

    public void setSaisChanged(LocalDate saisChanged) {
        this.saisChanged = saisChanged;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getForeignIdcode() {
        return foreignIdcode;
    }

    public void setForeignIdcode(String foreignIdcode) {
        this.foreignIdcode = foreignIdcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Classifier getSex() {
        return sex;
    }

    public void setSex(Classifier sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Classifier getFin() {
        return fin;
    }

    public void setFin(Classifier fin) {
        this.fin = fin;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public String getApplicationNr() {
        return applicationNr;
    }

    public void setApplicationNr(String applicationNr) {
        this.applicationNr = applicationNr;
    }

    public Classifier getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Classifier citizenship) {
        this.citizenship = citizenship;
    }

    public Classifier getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(Classifier studyLoad) {
        this.studyLoad = studyLoad;
    }

    public Classifier getResidenceCountry() {
        return residenceCountry;
    }

    public void setResidenceCountry(Classifier residenceCountry) {
        this.residenceCountry = residenceCountry;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
    }

    public String getSaisId() {
        return saisId;
    }

    public void setSaisId(String saisId) {
        this.saisId = saisId;
    }

    public String getAddressAds() {
        return addressAds;
    }

    public void setAddressAds(String addressAds) {
        this.addressAds = addressAds;
    }

    public Set<SaisApplicationGraduatedSchool> getGraduatedSchools() {
        return graduatedSchools;
    }

    public void setGraduatedSchools(Set<SaisApplicationGraduatedSchool> graduatedSchools) {
        this.graduatedSchools = graduatedSchools;
    }

    public Set<SaisApplicationGrade> getGrades() {
        return grades;
    }

    public void setGrades(Set<SaisApplicationGrade> grades) {
        this.grades = grades;
    }

    public Set<SaisApplicationOtherData> getOtherData() {
        return otherData;
    }

    public void setOtherData(Set<SaisApplicationOtherData> otherData) {
        this.otherData = otherData;
    }
}
