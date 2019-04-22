package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.ContractSupervisor;
import ee.hitsa.ois.util.EntityUtil;

public class ContractSupervisorDto {
    
    private Long id;
    private String supervisorName;
    private String supervisorEmail;
    private String supervisorPhone;
    private String supervisorUrl;
    
    private String supervisorFirstname;
    private String supervisorLastname;
    
    public static ContractSupervisorDto of(ContractSupervisor supervisor) {
        if (supervisor == null) {
            return null;
        }
        return EntityUtil.bindToDto(supervisor, new ContractSupervisorDto());
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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

    public String getSupervisorUrl() {
        return supervisorUrl;
    }

    public void setSupervisorUrl(String supervisorUrl) {
        this.supervisorUrl = supervisorUrl;
    }

    public String getSupervisorFirstname() {
        return supervisorFirstname;
    }

    public void setSupervisorFirstname(String supervisorFirstname) {
        this.supervisorFirstname = supervisorFirstname;
    }

    public String getSupervisorLastname() {
        return supervisorLastname;
    }

    public void setSupervisorLastname(String supervisorLastname) {
        this.supervisorLastname = supervisorLastname;
    }
}
