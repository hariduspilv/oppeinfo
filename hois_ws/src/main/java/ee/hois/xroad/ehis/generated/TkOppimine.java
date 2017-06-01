
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for tkOppimine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tkOppimine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oasRegKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oasNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="algusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="loppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaTase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppimiseIntensiivsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tkOppimine", propOrder = {
    "oasRegKood",
    "oasNimetus",
    "algusKp",
    "loppKp",
    "oppekavaTase",
    "oppevorm",
    "oppimiseIntensiivsus"
})
public class TkOppimine {

    @XmlElement(required = true)
    protected String oasRegKood;
    @XmlElement(required = true)
    protected String oasNimetus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKp;
    protected String oppekavaTase;
    protected String oppevorm;
    protected String oppimiseIntensiivsus;

    /**
     * Gets the value of the oasRegKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasRegKood() {
        return oasRegKood;
    }

    /**
     * Sets the value of the oasRegKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasRegKood(String value) {
        this.oasRegKood = value;
    }

    /**
     * Gets the value of the oasNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasNimetus() {
        return oasNimetus;
    }

    /**
     * Sets the value of the oasNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasNimetus(String value) {
        this.oasNimetus = value;
    }

    /**
     * Gets the value of the algusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKp() {
        return algusKp;
    }

    /**
     * Sets the value of the algusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKp(XMLGregorianCalendar value) {
        this.algusKp = value;
    }

    /**
     * Gets the value of the loppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKp() {
        return loppKp;
    }

    /**
     * Sets the value of the loppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKp(XMLGregorianCalendar value) {
        this.loppKp = value;
    }

    /**
     * Gets the value of the oppekavaTase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaTase() {
        return oppekavaTase;
    }

    /**
     * Sets the value of the oppekavaTase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaTase(String value) {
        this.oppekavaTase = value;
    }

    /**
     * Gets the value of the oppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppevorm() {
        return oppevorm;
    }

    /**
     * Sets the value of the oppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppevorm(String value) {
        this.oppevorm = value;
    }

    /**
     * Gets the value of the oppimiseIntensiivsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppimiseIntensiivsus() {
        return oppimiseIntensiivsus;
    }

    /**
     * Sets the value of the oppimiseIntensiivsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppimiseIntensiivsus(String value) {
        this.oppimiseIntensiivsus = value;
    }

}
