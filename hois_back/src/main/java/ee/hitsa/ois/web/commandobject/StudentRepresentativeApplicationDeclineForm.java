package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentRepresentativeApplicationDeclineForm extends VersionedCommand {

    @NotNull
    @Size(min = 1, max = 4000)
    private String rejectReason;

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
