package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.apelapplication.ApelApplication;

public class ApelApplicationCreated extends StudentMessage {

    public ApelApplicationCreated() {
    }
    
    public ApelApplicationCreated(ApelApplication application) {
        super(application.getStudent());
    }
}
