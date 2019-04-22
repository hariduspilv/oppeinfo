package ee.hitsa.ois.web.dto;

public class ContractToEkisMessageDto {
    
    private Long id;
    private String student;
    private String contractNr;
    private String enterprise;
    private String message;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStudent() {
        return student;
    }
    public void setStudent(String student) {
        this.student = student;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getContractNr() {
        return contractNr;
    }
    public void setContractNr(String contractNr) {
        this.contractNr = contractNr;
    }
    public String getEnterprise() {
        return enterprise;
    }
    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

}
