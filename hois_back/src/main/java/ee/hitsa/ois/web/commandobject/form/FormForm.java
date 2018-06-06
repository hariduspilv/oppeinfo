package ee.hitsa.ois.web.commandobject.form;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;

public class FormForm {

    @Required
    @ClassifierRestriction(MainClassCode.LOPUBLANKETT)
    private String type;
    @Required
    private String from;
    @Required
    private String thru;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getThru() {
        return thru;
    }
    public void setThru(String thru) {
        this.thru = thru;
    }
    
}
