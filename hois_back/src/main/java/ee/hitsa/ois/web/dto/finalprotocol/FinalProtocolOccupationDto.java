package ee.hitsa.ois.web.dto.finalprotocol;

import java.time.LocalDate;

public class FinalProtocolOccupationDto {
    
    private Long id;
    private String code;
    private String nameEt;
    private String nameEn;
    private Boolean isOccupationGrant;
    private LocalDate validFrom;
    private LocalDate validThru;
    
    public FinalProtocolOccupationDto(Long id, String code, String nameEt, String nameEn, Boolean isOccupationGrant, LocalDate validFrom, LocalDate validThru) {
        this.id = id;
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.isOccupationGrant = isOccupationGrant;
        this.validFrom = validFrom;
        this.validThru = validThru;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getNameEt() {
        return nameEt;
    }
    
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public String getNameEn() {
        return nameEn;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Boolean getIsOccupationGrant() {
        return isOccupationGrant;
    }

    public void setIsOccupationGrant(Boolean isOccupationGrant) {
        this.isOccupationGrant = isOccupationGrant;
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
