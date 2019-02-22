
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EVapiMenetlusinfoParing_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiMenetlusinfoParing_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="partner_auth" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="algus_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lopp_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiMenetlusinfoParing_v1", propOrder = {
    "partnerAuth",
    "algusKuupaev",
    "loppKuupaev"
})
public class EVapiMenetlusinfoParingV1 {

    @XmlElement(name = "partner_auth", required = true)
    protected String partnerAuth;
    @XmlElement(name = "algus_kuupaev", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKuupaev;
    @XmlElement(name = "lopp_kuupaev", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKuupaev;

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
     * Gets the value of the algusKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKuupaev() {
        return algusKuupaev;
    }

    /**
     * Sets the value of the algusKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKuupaev(XMLGregorianCalendar value) {
        this.algusKuupaev = value;
    }

    /**
     * Gets the value of the loppKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKuupaev() {
        return loppKuupaev;
    }

    /**
     * Sets the value of the loppKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKuupaev(XMLGregorianCalendar value) {
        this.loppKuupaev = value;
    }

}
