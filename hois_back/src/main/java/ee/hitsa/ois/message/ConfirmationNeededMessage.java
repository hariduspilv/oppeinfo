package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.application.Application;

public class ConfirmationNeededMessage extends StudentMessage {

    private final String type;

    public ConfirmationNeededMessage() {
        type = null;
    }

    public ConfirmationNeededMessage(Application application) {
        super(application.getStudent());

        type = application.getType().getNameEt();
    }

    public String getAvalduseLiik() {
        return type;
    }
}
