package ee.hitsa.ois.service.security;

public class MobileIdSessionResponse {
    
    private Integer errorCode;
    private String challengeID;

    public Integer getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getChallengeID() {
        return challengeID;
    }
    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }

}
