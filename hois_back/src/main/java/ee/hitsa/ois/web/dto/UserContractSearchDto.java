package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

public class UserContractSearchDto {
    
    private Long id;
    private String name;
    private String idcode;
    private String group;
    private LocalDateTime agreed;
    private String contractText;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public LocalDateTime getAgreed() {
        return agreed;
    }

    public void setAgreed(LocalDateTime agreed) {
        this.agreed = agreed;
    }

    public String getContractText() {
        return contractText;
    }

    public void setContractText(String contractText) {
        this.contractText = contractText;
    }

}
