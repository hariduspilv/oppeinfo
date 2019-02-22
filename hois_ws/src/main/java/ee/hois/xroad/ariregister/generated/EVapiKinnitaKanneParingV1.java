
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiKinnitaKanneParing_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiKinnitaKanneParing_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="partner_auth" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ekanded_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="viide_manusele" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiKinnitaKanneParing_v1", propOrder = {
    "partnerAuth",
    "ekandedId",
    "viideManusele"
})
public class EVapiKinnitaKanneParingV1 {

    @XmlElement(name = "partner_auth", required = true)
    protected String partnerAuth;
    @XmlElement(name = "ekanded_id", required = true)
    protected BigInteger ekandedId;
    @XmlElement(name = "viide_manusele", required = true, type = String.class)
    @XmlAttachmentRef
    @XmlSchemaType(name = "anyURI")
    protected DataHandler viideManusele;

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
     * Gets the value of the ekandedId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEkandedId() {
        return ekandedId;
    }

    /**
     * Sets the value of the ekandedId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEkandedId(BigInteger value) {
        this.ekandedId = value;
    }

    /**
     * Gets the value of the viideManusele property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getViideManusele() {
        return viideManusele;
    }

    /**
     * Sets the value of the viideManusele property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViideManusele(DataHandler value) {
        this.viideManusele = value;
    }

}
