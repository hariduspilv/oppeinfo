package ee.hitsa.ois.service.security;

public class MobileIdSession {

    private String idcode;
    private String verificationCode;
    private String mobileNumber;
    private String authenticationHash;

    public MobileIdSession(String idcode, String mobileNumber, String authenticationHash) {
        this.idcode = idcode;
        this.mobileNumber = mobileNumber;
        this.authenticationHash = authenticationHash;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAuthenticationHash() {
        return authenticationHash;
    }

    public void setAuthenticationHash(String authenticationHash) {
        this.authenticationHash = authenticationHash;
    }
}
