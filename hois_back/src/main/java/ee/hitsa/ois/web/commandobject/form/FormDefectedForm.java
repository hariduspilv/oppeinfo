package ee.hitsa.ois.web.commandobject.form;

import ee.hitsa.ois.validation.Required;

public class FormDefectedForm extends FormForm {

    @Required
    private String reason;

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
