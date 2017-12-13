package ee.hois.soap.dds.service;

public class GetMobileAuthenticateStatusRequest {

    private int sesscode;
    private boolean waitSignature;
    
    public int getSesscode() {
        return sesscode;
    }
    public void setSesscode(int sesscode) {
        this.sesscode = sesscode;
    }
    
    public boolean isWaitSignature() {
        return waitSignature;
    }
    public void setWaitSignature(boolean waitSignature) {
        this.waitSignature = waitSignature;
    }
    
}
