package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.NotEmpty;

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
