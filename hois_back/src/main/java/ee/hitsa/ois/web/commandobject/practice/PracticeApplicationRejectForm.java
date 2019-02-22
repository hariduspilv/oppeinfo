package ee.hitsa.ois.web.commandobject.practice;

import ee.hitsa.ois.validation.Required;

public class PracticeApplicationRejectForm {

    @Required
    private String rejectReason;

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
    
}
