
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for paringarikeelud_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringarikeelud_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fyysilise_isiku_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fyysilise_isiku_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fyysilise_isiku_perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fyysilise_isiku_synniaeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringarikeelud_paring", propOrder = {
    "fyysiliseIsikuKood",
    "fyysiliseIsikuEesnimi",
    "fyysiliseIsikuPerenimi",
    "fyysiliseIsikuSynniaeg"
})
public class ParingarikeeludParing {

    @XmlElement(name = "fyysilise_isiku_kood", required = true, nillable = true)
    protected String fyysiliseIsikuKood;
    @XmlElement(name = "fyysilise_isiku_eesnimi", required = true, nillable = true)
    protected String fyysiliseIsikuEesnimi;
    @XmlElement(name = "fyysilise_isiku_perenimi", required = true, nillable = true)
    protected String fyysiliseIsikuPerenimi;
    @XmlElement(name = "fyysilise_isiku_synniaeg", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fyysiliseIsikuSynniaeg;

    /**
     * Gets the value of the fyysiliseIsikuKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuKood() {
        return fyysiliseIsikuKood;
    }

    /**
     * Sets the value of the fyysiliseIsikuKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuKood(String value) {
        this.fyysiliseIsikuKood = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuEesnimi() {
        return fyysiliseIsikuEesnimi;
    }

    /**
     * Sets the value of the fyysiliseIsikuEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuEesnimi(String value) {
        this.fyysiliseIsikuEesnimi = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuPerenimi() {
        return fyysiliseIsikuPerenimi;
    }

    /**
     * Sets the value of the fyysiliseIsikuPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuPerenimi(String value) {
        this.fyysiliseIsikuPerenimi = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuSynniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFyysiliseIsikuSynniaeg() {
        return fyysiliseIsikuSynniaeg;
    }

    /**
     * Sets the value of the fyysiliseIsikuSynniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFyysiliseIsikuSynniaeg(XMLGregorianCalendar value) {
        this.fyysiliseIsikuSynniaeg = value;
    }

}
