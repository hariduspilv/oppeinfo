package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

public class ProtocolCalculateCommand {
    
    @NotEmpty
    private Set<Long> protocolStudents;

    public Set<Long> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(Set<Long> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }
}
