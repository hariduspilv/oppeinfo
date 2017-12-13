package ee.hitsa.ois.service.security;

public class MobileIdSession {

    private Integer errorCode;
    
    private Integer sesscode;
    private String userIDCode;
    private String userGivenname;
    private String userSurname;
    private String challengeID;

    public Integer getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    
    public Integer getSesscode() {
        return sesscode;
    }
    public void setSesscode(Integer sesscode) {
        this.sesscode = sesscode;
    }

    public String getUserIDCode() {
        return userIDCode;
    }
    public void setUserIDCode(String userIDCode) {
        this.userIDCode = userIDCode;
    }
    
    public String getUserGivenname() {
        return userGivenname;
    }
    public void setUserGivenname(String userGivenname) {
        this.userGivenname = userGivenname;
    }
    
    public String getUserSurname() {
        return userSurname;
    }
    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
    
    public String getChallengeID() {
        return challengeID;
    }
    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }
    
}
