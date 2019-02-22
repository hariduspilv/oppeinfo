
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EVK_kanded_v1_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVK_kanded_v1_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="alguskpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="loppkpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="evkood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="syndmus" type="{http://arireg.x-road.eu/producer/}x_evk_syndmus" minOccurs="0"/&gt;
 *         &lt;element name="keel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVK_kanded_v1_paring", propOrder = {
    "alguskpv",
    "loppkpv",
    "evkood",
    "syndmus",
    "keel"
})
public class EVKKandedV1Paring {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar alguskpv;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppkpv;
    protected BigInteger evkood;
    @XmlSchemaType(name = "string")
    protected XEvkSyndmus syndmus;
    protected String keel;

    /**
     * Gets the value of the alguskpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlguskpv() {
        return alguskpv;
    }

    /**
     * Sets the value of the alguskpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlguskpv(XMLGregorianCalendar value) {
        this.alguskpv = value;
    }

    /**
     * Gets the value of the loppkpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppkpv() {
        return loppkpv;
    }

    /**
     * Sets the value of the loppkpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppkpv(XMLGregorianCalendar value) {
        this.loppkpv = value;
    }

    /**
     * Gets the value of the evkood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEvkood() {
        return evkood;
    }

    /**
     * Sets the value of the evkood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEvkood(BigInteger value) {
        this.evkood = value;
    }

    /**
     * Gets the value of the syndmus property.
     * 
     * @return
     *     possible object is
     *     {@link XEvkSyndmus }
     *     
     */
    public XEvkSyndmus getSyndmus() {
        return syndmus;
    }

    /**
     * Sets the value of the syndmus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XEvkSyndmus }
     *     
     */
    public void setSyndmus(XEvkSyndmus value) {
        this.syndmus = value;
    }

    /**
     * Gets the value of the keel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeel() {
        return keel;
    }

    /**
     * Sets the value of the keel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeel(String value) {
        this.keel = value;
    }

}
