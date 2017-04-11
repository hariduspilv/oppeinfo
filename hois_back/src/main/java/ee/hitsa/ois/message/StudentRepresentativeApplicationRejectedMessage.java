package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.StudentRepresentativeApplication;

public class StudentRepresentativeApplicationRejectedMessage extends StudentMessage {

    private final String reason;

    public StudentRepresentativeApplicationRejectedMessage() {
        reason = null;
    }

    public StudentRepresentativeApplicationRejectedMessage(StudentRepresentativeApplication application) {
        super(application.getStudent());

        reason = application.getRejectReason();
    }

    public String getReason() {
        return reason;
    }

    public String getPohjendus() {
        return reason;
    }
}
