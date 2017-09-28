package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

public class ApplicationRejectForm {

    @Size(max = 4000)
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
