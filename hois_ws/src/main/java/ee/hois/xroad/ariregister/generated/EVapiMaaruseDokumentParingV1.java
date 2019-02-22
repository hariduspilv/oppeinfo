
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiMaaruseDokumentParing_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiMaaruseDokumentParing_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="partner_auth" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="maaruse_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiMaaruseDokumentParing_v1", propOrder = {
    "partnerAuth",
    "maaruseId"
})
public class EVapiMaaruseDokumentParingV1 {

    @XmlElement(name = "partner_auth", required = true)
    protected String partnerAuth;
    @XmlElement(name = "maaruse_id", required = true)
    protected BigInteger maaruseId;

    /**
     * Gets the value of the partnerAuth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerAuth() {
        return partnerAuth;
    }

    /**
     * Sets the value of the partnerAuth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerAuth(String value) {
        this.partnerAuth = value;
    }

    /**
     * Gets the value of the maaruseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaaruseId() {
        return maaruseId;
    }

    /**
     * Sets the value of the maaruseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaaruseId(BigInteger value) {
        this.maaruseId = value;
    }

}
