
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="taotlusId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="regNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "regNr"
})
@XmlRootElement(name = "mtsysKustutaTegevusluba")
public class MtsysKustutaTegevusluba {

    @XmlElement(required = true)
    protected BigInteger taotlusId;
    @XmlElement(required = true)
    protected String regNr;

    /**
     * Gets the value of the taotlusId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTaotlusId() {
        return taotlusId;
    }

    /**
     * Sets the value of the taotlusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTaotlusId(BigInteger value) {
        this.taotlusId = value;
    }

    /**
     * Gets the value of the regNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegNr() {
        return regNr;
    }

    /**
     * Sets the value of the regNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegNr(String value) {
        this.regNr = value;
    }

}
