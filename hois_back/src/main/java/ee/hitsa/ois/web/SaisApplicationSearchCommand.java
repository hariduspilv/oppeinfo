package ee.hitsa.ois.web;

import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class SaisApplicationSearchCommand {

    private List<String> code;

    @ClassifierRestriction(MainClassCode.SAIS_AVALDUSESTAATUS)
    private List<String> status;
    private String name;
    private String idcode;
    private Boolean showRevoked;
    private Boolean addedToDirective;

    public List<String> getCode() {
        return code;
    }
    public void setCode(List<String> code) {
        this.code = code;
    }
    public List<String> getStatus() {
        return status;
    }
    public void setStatus(List<String> status) {
        this.status = status;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    public Boolean getShowRevoked() {
        return showRevoked;
    }
    public void setShowRevoked(Boolean showRevoked) {
        this.showRevoked = showRevoked;
    }
    public Boolean getAddedToDirective() {
        return addedToDirective;
    }
    public void setAddedToDirective(Boolean addedToDirective) {
        this.addedToDirective = addedToDirective;
    }

}
