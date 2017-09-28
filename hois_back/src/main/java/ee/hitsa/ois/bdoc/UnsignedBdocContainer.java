package ee.hitsa.ois.bdoc;

import java.io.Serializable;

import org.digidoc4j.Container;
import org.digidoc4j.DataToSign;

import ee.hitsa.ois.util.CertificateUtil;

public class UnsignedBdocContainer implements Serializable {

    private Container container;
    private DataToSign dataToSign;

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public DataToSign getDataToSign() {
        return dataToSign;
    }

    public void setDataToSign(DataToSign dataToSign) {
        this.dataToSign = dataToSign;
    }

    public String getDigestToSign() {
        return CertificateUtil.digestToHex(getDataToSign().getDigestToSign());
    }

}
