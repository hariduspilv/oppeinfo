package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.Student;

public class ContractSupervisorMessage extends StudentMessage {
    
    public ContractSupervisorMessage() {
        url = null;
    }

    public ContractSupervisorMessage(Student student, String url) {
        super(student);

        this.url = url;
    }
    private final String url;
    
    public String getKysitlusUrl() {
        return url;
    }

}
