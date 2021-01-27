package ee.hitsa.ois.web.dto.curriculumVersion;

import java.math.BigDecimal;

public class CurriculumVersionNominalCapacityDto {
    
    private Long id;
    
    private Long specialityId;
    
    private BigDecimal credits;
    
    private Long periodNr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeriodNr() {
        return periodNr;
    }

    public void setPeriodNr(Long periodNr) {
        this.periodNr = periodNr;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }
    
    
}
