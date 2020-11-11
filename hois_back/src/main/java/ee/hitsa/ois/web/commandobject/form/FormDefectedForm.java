package ee.hitsa.ois.web.commandobject.form;

import ee.hitsa.ois.validation.Required;

import javax.validation.constraints.Size;

public class FormDefectedForm extends FormForm {

    @Required
    @Size(max = 255)
    private String reason;

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
