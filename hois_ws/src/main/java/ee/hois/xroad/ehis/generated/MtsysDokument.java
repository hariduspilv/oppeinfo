
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="taotlusId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="dokumentId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "taotlusId",
    "dokumentId"
})
@XmlRootElement(name = "mtsysDokument")
public class MtsysDokument {

    protected int taotlusId;
    protected int dokumentId;

    /**
     * Gets the value of the taotlusId property.
     * 
     */
    public int getTaotlusId() {
        return taotlusId;
    }

    /**
     * Sets the value of the taotlusId property.
     * 
     */
    public void setTaotlusId(int value) {
        this.taotlusId = value;
    }

    /**
     * Gets the value of the dokumentId property.
     * 
     */
    public int getDokumentId() {
        return dokumentId;
    }

    /**
     * Sets the value of the dokumentId property.
     * 
     */
    public void setDokumentId(int value) {
        this.dokumentId = value;
    }

}
