package ee.hitsa.ois.web.dto.practice;

import java.time.LocalDate;

public class PracticeApplicationPeriodSearchDto {
    
    private Long id;
    private String enterpriseName;
    private String enterpriseRegCode;
    private Short places;
    private LocalDate validFrom;
    private LocalDate validThru;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEnterpriseName() {
        return enterpriseName;
    }
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    public String getEnterpriseRegCode() {
        return enterpriseRegCode;
    }
    public void setEnterpriseRegCode(String enterpriseRegCode) {
        this.enterpriseRegCode = enterpriseRegCode;
    }
    public Short getPlaces() {
        return places;
    }
    public void setPlaces(Short places) {
        this.places = places;
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
}
