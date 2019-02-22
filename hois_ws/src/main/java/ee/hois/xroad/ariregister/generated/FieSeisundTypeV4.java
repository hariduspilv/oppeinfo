
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for fie_seisundType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fie_seisundType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tegevus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="elemtyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="alguskpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="loppkpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="periood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fie_seisundType_v4", propOrder = {
    "tegevus",
    "elemtyyp",
    "alguskpv",
    "loppkpv",
    "periood"
})
public class FieSeisundTypeV4 {

    @XmlElement(required = true)
    protected String tegevus;
    @XmlElement(required = true)
    protected String elemtyyp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar alguskpv;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppkpv;
    @XmlElement(required = true)
    protected String periood;

    /**
     * Gets the value of the tegevus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevus() {
        return tegevus;
    }

    /**
     * Sets the value of the tegevus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevus(String value) {
        this.tegevus = value;
    }

    /**
     * Gets the value of the elemtyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElemtyyp() {
        return elemtyyp;
    }

    /**
     * Sets the value of the elemtyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElemtyyp(String value) {
        this.elemtyyp = value;
    }

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
     * Gets the value of the periood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriood() {
        return periood;
    }

    /**
     * Sets the value of the periood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriood(String value) {
        this.periood = value;
    }

}
