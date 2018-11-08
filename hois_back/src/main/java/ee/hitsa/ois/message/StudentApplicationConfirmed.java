package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.application.Application;

public class StudentApplicationConfirmed extends StudentMessage {

    private final String applicationType;
    
    public StudentApplicationConfirmed() {
        applicationType = null;
    }
    
    public StudentApplicationConfirmed(Application application) {
        super(application.getStudent());
        
        applicationType = application.getType().getNameEt();
    }
    
    public String getAvalduseLiik() {
        return applicationType;
    }
}
