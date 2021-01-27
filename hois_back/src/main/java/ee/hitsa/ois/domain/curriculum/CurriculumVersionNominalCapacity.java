package ee.hitsa.ois.domain.curriculum;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class CurriculumVersionNominalCapacity extends BaseEntityWithId {
    
    private static final long serialVersionUID = -1079247830844321207L;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumVersion curriculumVersion;
    
    private BigDecimal credits;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumVersionSpeciality curriculumVersionSpeciality;
    
    private Long periodNr;

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public CurriculumVersionSpeciality getCurriculumVersionSpeciality() {
        return curriculumVersionSpeciality;
    }

    public void setCurriculumVersionSpeciality(CurriculumVersionSpeciality curriculumVersionSpeciality) {
        this.curriculumVersionSpeciality = curriculumVersionSpeciality;
    }

    public Long getPeriodNr() {
        return periodNr;
    }

    public void setPeriodNr(Long periodNr) {
        this.periodNr = periodNr;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

}
