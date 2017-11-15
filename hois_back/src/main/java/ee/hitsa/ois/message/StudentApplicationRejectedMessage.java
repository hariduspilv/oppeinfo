package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.application.Application;

public class StudentApplicationRejectedMessage extends StudentMessage {

    private final String type;
    private final String reason;

    public StudentApplicationRejectedMessage() {
        type = null;
        reason = null;
    }

    public StudentApplicationRejectedMessage(Application application) {
        super(application.getStudent());

        type = application.getType().getNameEt();
        reason = application.getRejectReason();
    }

    public String getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public String getAvalduseLiik() {
        return type;
    }

    public String getPohjendus() {
        return reason;
    }
}
