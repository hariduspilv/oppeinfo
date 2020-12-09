package ee.hitsa.ois.domain.student;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class StudentOccupationCertificate extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    private String certificateNr;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier occupation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier partOccupation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier speciality;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String issuer;
    private LocalDate issueDate;
    private String language;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="language_code")
    private Classifier languageCode;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCertificateNr() {
        return certificateNr;
    }

    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }

    public Classifier getOccupation() {
        return occupation;
    }

    public void setOccupation(Classifier occupation) {
        this.occupation = occupation;
    }

    public Classifier getPartOccupation() {
        return partOccupation;
    }

    public void setPartOccupation(Classifier partOccupation) {
        this.partOccupation = partOccupation;
    }

    public Classifier getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Classifier speciality) {
        this.speciality = speciality;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Classifier getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(Classifier languageCode) {
        this.languageCode = languageCode;
    }
}
