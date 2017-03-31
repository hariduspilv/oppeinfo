package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.SaisApplication;
import ee.hitsa.ois.util.EntityUtil;

public class SaisApplicationSearchDto {

    private Long id;
    private String applicationNr;
    private String idcode;
    private String firstname;
    private String lastname;
    private String saisAdmissionCode;
    private String status;
    private Boolean addedToDirective = Boolean.FALSE;

    public static SaisApplicationSearchDto of(SaisApplication saisApplication) {
        SaisApplicationSearchDto dto = EntityUtil.bindToDto(saisApplication, new SaisApplicationSearchDto());
        dto.setSaisAdmissionCode(saisApplication.getSaisAdmission().getCode());
        return dto;
    }

    
    public String getApplicationNr() {
        return applicationNr;
    }

    public void setApplicationNr(String applicationNr) {
        this.applicationNr = applicationNr;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
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

    public String getSaisAdmissionCode() {
        return saisAdmissionCode;
    }

    public void setSaisAdmissionCode(String saisAdmissionCode) {
        this.saisAdmissionCode = saisAdmissionCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getAddedToDirective() {
        return addedToDirective;
    }

    public void setAddedToDirective(Boolean addedToDirective) {
        this.addedToDirective = addedToDirective;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

}
