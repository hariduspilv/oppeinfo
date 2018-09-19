package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.application.Application;

public class StudentApplicationCreated extends StudentMessage {

    private final String applicationType;
    
    public StudentApplicationCreated() {
        applicationType = null;
    }
    
    public StudentApplicationCreated(Application application) {
        super(application.getStudent());
        
        applicationType = application.getType().getNameEt();
    }
    
    public String getAvalduseLiik() {
        return applicationType;
    }
}
