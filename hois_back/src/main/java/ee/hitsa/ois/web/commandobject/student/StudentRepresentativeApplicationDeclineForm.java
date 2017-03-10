package ee.hitsa.ois.web.commandobject.student;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StudentRepresentativeApplicationDeclineForm extends VersionedCommand {

    @NotEmpty
    @Size(max = 4000)
    private String rejectReason;

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
