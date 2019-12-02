package ee.hitsa.ois.web.dto;

import java.util.List;

public class ContractEkisDto {
    
    private List<ContractToEkisMessageDto> successful;
    private List<ContractToEkisMessageDto> failed;

    public List<ContractToEkisMessageDto> getSuccessful() {
        return successful;
    }

    public List<ContractToEkisMessageDto> getFailed() {
        return failed;
    }
    
    public void setFailed(List<ContractToEkisMessageDto> failed) {
        this.failed = failed;
    }
    
    public void setSuccessful(List<ContractToEkisMessageDto> successful) {
        this.successful = successful;
    }
}
