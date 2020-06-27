package ee.hitsa.ois.web.commandobject.finalprotocol;

public class FinalHigherProtocolSignForm extends FinalHigherProtocolSaveForm {

    private String certificate;
    private String signerMobileNumber;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getSignerMobileNumber() {
        return signerMobileNumber;
    }

    public void setSignerMobileNumber(String signerMobileNumber) {
        this.signerMobileNumber = signerMobileNumber;
    }
}
