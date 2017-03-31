package ee.hitsa.ois.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class SaisApplicationGraduatedSchool extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    //TODO: fix name in DB
    @JoinColumn(name="sais_applucation_id", nullable = false, insertable = false, updatable = false)
    private SaisApplication saisApplication;

    @Size(max = 255)
    private String name;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Size(max = 20)
    private String regCode;

    @Column(nullable = false)
    private Boolean isAbroad = Boolean.FALSE;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier studyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyForm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SaisApplication getSaisApplication() {
        return saisApplication;
    }

    public void setSaisApplication(SaisApplication saisApplication) {
        this.saisApplication = saisApplication;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public Boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(Boolean isAbroad) {
        this.isAbroad = isAbroad;
    }

    public Classifier getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(Classifier studyLevel) {
        this.studyLevel = studyLevel;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }


}
