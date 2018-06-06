package ee.hitsa.ois.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.school.School;

@Entity
public class Form extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    
    private String code;
    @Column(nullable = false)
    private Long numeral;
    @Column(nullable = false)
    private String fullCode;
    
    private LocalDate printed;
    private LocalDate defected;
    private String defectedBy;
    private String defectReason;
    
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
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
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    
    public Long getNumeral() {
        return numeral;
    }
    public void setNumeral(Long numeral) {
        this.numeral = numeral;
    }
    
    public String getFullCode() {
        return fullCode;
    }
    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }
    
    public LocalDate getPrinted() {
        return printed;
    }
    public void setPrinted(LocalDate printed) {
        this.printed = printed;
    }
    
    public LocalDate getDefected() {
        return defected;
    }
    public void setDefected(LocalDate defected) {
        this.defected = defected;
    }
    
    public String getDefectedBy() {
        return defectedBy;
    }
    public void setDefectedBy(String defectedBy) {
        this.defectedBy = defectedBy;
    }

    public String getDefectReason() {
        return defectReason;
    }
    public void setDefectReason(String defectReason) {
        this.defectReason = defectReason;
    }
    
}
