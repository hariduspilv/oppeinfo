package ee.hois.soap.dds.service;

public class MobileSignResponse {

    private String status;
    private String statusCode;
    private String challengeID;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    
    public String getChallengeID() {
        return challengeID;
    }
    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }
    
}
