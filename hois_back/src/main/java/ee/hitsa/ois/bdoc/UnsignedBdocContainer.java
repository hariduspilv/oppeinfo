package ee.hitsa.ois.bdoc;

import java.io.Serializable;

import org.apache.commons.codec.binary.Hex;
import org.digidoc4j.Container;
import org.digidoc4j.DataToSign;

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
        return Hex.encodeHexString(getDataToSign().getDigestToSign());
    }

}
