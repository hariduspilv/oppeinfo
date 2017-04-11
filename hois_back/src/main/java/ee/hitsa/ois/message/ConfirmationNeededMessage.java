package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.application.Application;

public class ConfirmationNeededMessage extends StudentMessage {

    public ConfirmationNeededMessage() {
    }

    public ConfirmationNeededMessage(Application application) {
        super(application.getStudent());
    }
}
