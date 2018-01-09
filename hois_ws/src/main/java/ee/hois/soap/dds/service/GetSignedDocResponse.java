package ee.hois.soap.dds.service;

public class GetSignedDocResponse {

    private String status;
    private String signedDocData;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSignedDocData() {
        return signedDocData;
    }
    public void setSignedDocData(String signedDocData) {
        this.signedDocData = signedDocData;
    }
    
}
