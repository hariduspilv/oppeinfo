
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailandmed_v5_esindusoiguse_normaalregulatsioon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_esindusoiguse_normaalregulatsioon"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="roll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_esindusoiguse_normaalregulatsioon", propOrder = {
    "roll",
    "sisu"
})
public class DetailandmedV5EsindusoiguseNormaalregulatsioon {

    protected String roll;
    protected String sisu;

    /**
     * Gets the value of the roll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoll() {
        return roll;
    }

    /**
     * Sets the value of the roll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoll(String value) {
        this.roll = value;
    }

    /**
     * Gets the value of the sisu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSisu() {
        return sisu;
    }

    /**
     * Sets the value of the sisu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSisu(String value) {
        this.sisu = value;
    }

}
