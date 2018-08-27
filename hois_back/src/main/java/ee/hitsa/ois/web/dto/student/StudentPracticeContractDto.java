package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

public class StudentPracticeContractDto {
    
    private Long id;
    private String contractNr;
    private LocalDate startDate;
    private LocalDate endDate;
    private String enterprise;
    private String supervisor;
    private String schoolSupervisor;
    private LocalDate contractDate;
    private String status;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNr() {
        return contractNr;
    }
    
    public void setContractNr(String contractNr) {
        this.contractNr = contractNr;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getEnterprise() {
        return enterprise;
    }
    
    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
    
    public String getSupervisor() {
        return supervisor;
    }
    
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
    
    public String getSchoolSupervisor() {
        return schoolSupervisor;
    }
    
    public void setSchoolSupervisor(String schoolSupervisor) {
        this.schoolSupervisor = schoolSupervisor;
    }
    
    public LocalDate getContractDate() {
        return contractDate;
    }
    
    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
}
