package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.application.Application;

public class StudentApplicationChosenCommitteeMessage extends StudentMessage {

    private final String applicationType;
    
    public StudentApplicationChosenCommitteeMessage() {
        applicationType = null;
    }
    
    public StudentApplicationChosenCommitteeMessage(Application application) {
        super(application.getStudent());
        
        applicationType = application.getType().getNameEt();
    }
    
    public String getAvalduseLiik() {
        return applicationType;
    }
}
