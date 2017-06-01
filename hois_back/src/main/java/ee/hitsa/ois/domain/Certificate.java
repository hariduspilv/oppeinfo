package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;

@Entity
public class Certificate extends BaseEntityWithId {

    private String headline;
    private String content;
    private String whom;
    private String certificateNr;
    private String signatoryName;
    private String signatoryIdcode;
    private String otherName;
    private String otherIdcode;
    private String wdUrl;
    private Long wdId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getOtherIdcode() {
        return otherIdcode;
    }

    public void setOtherIdcode(String otherIdcode) {
        this.otherIdcode = otherIdcode;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public String getCertificateNr() {
        return certificateNr;
    }

    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }

    public String getSignatoryName() {
        return signatoryName;
    }

    public void setSignatoryName(String signatoryName) {
        this.signatoryName = signatoryName;
    }

    public String getSignatoryIdcode() {
        return signatoryIdcode;
    }

    public void setSignatoryIdcode(String signatoryIdcode) {
        this.signatoryIdcode = signatoryIdcode;
    }

    public String getWdUrl() {
        return wdUrl;
    }

    public void setWdUrl(String wdUrl) {
        this.wdUrl = wdUrl;
    }

    public Long getWdId() {
        return wdId;
    }

    public void setWdId(Long wdId) {
        this.wdId = wdId;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
