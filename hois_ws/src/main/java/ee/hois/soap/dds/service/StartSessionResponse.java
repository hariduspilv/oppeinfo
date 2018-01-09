package ee.hois.soap.dds.service;

public class StartSessionResponse {

    private String status;
    private Integer sesscode;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getSesscode() {
        return sesscode;
    }
    public void setSesscode(Integer sesscode) {
        this.sesscode = sesscode;
    }
    
}
