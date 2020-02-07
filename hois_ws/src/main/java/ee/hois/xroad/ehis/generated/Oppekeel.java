
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppekeel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppekeel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppekeelNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekeelId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppekeel", propOrder = {
    "oppekeelNimetus",
    "oppekeelId"
})
public class Oppekeel {

    @XmlElement(required = true, nillable = true)
    protected String oppekeelNimetus;
    @XmlElement(required = true, nillable = true)
    protected BigInteger oppekeelId;

    /**
     * Gets the value of the oppekeelNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekeelNimetus() {
        return oppekeelNimetus;
    }

    /**
     * Sets the value of the oppekeelNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekeelNimetus(String value) {
        this.oppekeelNimetus = value;
    }

    /**
     * Gets the value of the oppekeelId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekeelId() {
        return oppekeelId;
    }

    /**
     * Sets the value of the oppekeelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekeelId(BigInteger value) {
        this.oppekeelId = value;
    }

}
