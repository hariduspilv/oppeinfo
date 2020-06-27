package ee.hitsa.ois.bdoc;

import org.digidoc4j.Container;
import org.digidoc4j.DataToSign;

public class MobileIdSigningSession {

    private String sessionID;
    private String verificationCode;
    private DataToSign dataToSign;
    private Container container;

    public MobileIdSigningSession(String sessionID, DataToSign dataToSign, Container container) {
        this.sessionID = sessionID;
        this.dataToSign = dataToSign;
        this.container = container;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public DataToSign getDataToSign() {
        return dataToSign;
    }

    public void setDataToSign(DataToSign dataToSign) {
        this.dataToSign = dataToSign;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
