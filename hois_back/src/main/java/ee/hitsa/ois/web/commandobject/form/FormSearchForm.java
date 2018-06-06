package ee.hitsa.ois.web.commandobject.form;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class FormSearchForm {

    @ClassifierRestriction(MainClassCode.LOPUBLANKETT)
    private String type;
    @ClassifierRestriction(MainClassCode.LOPUBLANKETT_STAATUS)
    private String status;
    private Long from;
    private Long thru;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getFrom() {
        return from;
    }
    public void setFrom(Long from) {
        this.from = from;
    }
    
    public Long getThru() {
        return thru;
    }
    public void setThru(Long thru) {
        this.thru = thru;
    }
    
}
