package ee.hitsa.ois.web.dto;

import javax.validation.constraints.NotNull;

public class StudentGroupContractSearchCommand {
    
    @NotNull
    private Long studentGroup;
    @NotNull
    private Boolean active;
    
    public Long getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}
