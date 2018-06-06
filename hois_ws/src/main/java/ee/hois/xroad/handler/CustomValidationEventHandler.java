package ee.hois.xroad.handler;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class CustomValidationEventHandler implements ValidationEventHandler {

    @Override
    public boolean handleEvent(ValidationEvent event) {
        String msg = event.getMessage();
        return msg != null && msg.startsWith("unexpected element");
    }
}
