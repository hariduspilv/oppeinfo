package ee.hitsa.ois.web.dto;

public class SupervisorDto {
    private Long id;
    private String supervisorName;
    private String supervisorEmail;
    private String supervisorPhone;
    
    public String getSupervisorName() {
        return supervisorName;
    }
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
    public String getSupervisorEmail() {
        return supervisorEmail;
    }
    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }
    public String getSupervisorPhone() {
        return supervisorPhone;
    }
    public void setSupervisorPhone(String supervisorPhone) {
        this.supervisorPhone = supervisorPhone;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
