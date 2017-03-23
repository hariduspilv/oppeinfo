package ee.hitsa.ois.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.NotEmpty;

@Entity
public class SaisApplication extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SaisAdmission saisAdmission;

    @Column(nullable = false)
    private LocalDateTime submitted;
    private LocalDateTime saisChanged;

    @NotEmpty
    @Size(max = 100)
    private String firstname;

    @NotEmpty
    @Size(max = 100)
    private String lastname;

    @Column(nullable = false)
    private LocalDate birthdate;

    //TODO in DB size is 20?
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

    @NotEmpty
    @Size(max = 100)
    private String applicationNr;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier citizenship;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier studyLoad;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier residenceCountry;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier language;

    @Size(max = 50)
    private String saisId;

    public SaisAdmission getSaisAdmission() {
        return saisAdmission;
    }

    public void setSaisAdmission(SaisAdmission saisAdmission) {
        this.saisAdmission = saisAdmission;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public LocalDateTime getSaisChanged() {
        return saisChanged;
    }

    public void setSaisChanged(LocalDateTime saisChanged) {
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

}
