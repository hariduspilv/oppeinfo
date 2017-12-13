package ee.hois.soap.dds.service;

public class GetMobileAuthenticateStatusResponse {

    private String status;
    private String signature;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
}
