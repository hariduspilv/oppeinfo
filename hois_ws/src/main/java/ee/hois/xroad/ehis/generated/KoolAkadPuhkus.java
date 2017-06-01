
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for koolAkadPuhkus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="koolAkadPuhkus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="algusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="eeldatavLoppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="tegelikLoppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "koolAkadPuhkus", propOrder = {
    "algusKp",
    "eeldatavLoppKp",
    "tegelikLoppKp"
})
public class KoolAkadPuhkus {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar eeldatavLoppKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tegelikLoppKp;

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
     * Gets the value of the eeldatavLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEeldatavLoppKp() {
        return eeldatavLoppKp;
    }

    /**
     * Sets the value of the eeldatavLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEeldatavLoppKp(XMLGregorianCalendar value) {
        this.eeldatavLoppKp = value;
    }

    /**
     * Gets the value of the tegelikLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTegelikLoppKp() {
        return tegelikLoppKp;
    }

    /**
     * Sets the value of the tegelikLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTegelikLoppKp(XMLGregorianCalendar value) {
        this.tegelikLoppKp = value;
    }

}
