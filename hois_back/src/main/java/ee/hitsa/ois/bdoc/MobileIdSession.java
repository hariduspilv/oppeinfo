package ee.hitsa.ois.bdoc;

public class MobileIdSession {

    private Integer sesscode;
    private String challengeID;

    public Integer getSesscode() {
        return sesscode;
    }
    public void setSesscode(Integer sesscode) {
        this.sesscode = sesscode;
    }

    public String getChallengeID() {
        return challengeID;
    }
    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }
    
}
