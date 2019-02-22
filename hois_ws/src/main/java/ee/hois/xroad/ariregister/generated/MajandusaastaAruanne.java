
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for majandusaasta_aruanne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="majandusaasta_aruanne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aruande_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aruande_nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aruande_aasta" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_algus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_lopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "majandusaasta_aruanne", propOrder = {
    "aruandeKood",
    "aruandeNimetus",
    "aruandeAasta",
    "majandusaastaAlgus",
    "majandusaastaLopp"
})
public class MajandusaastaAruanne {

    @XmlElement(name = "aruande_kood")
    protected String aruandeKood;
    @XmlElement(name = "aruande_nimetus")
    protected String aruandeNimetus;
    @XmlElement(name = "aruande_aasta")
    protected Integer aruandeAasta;
    @XmlElement(name = "majandusaasta_algus")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaAlgus;
    @XmlElement(name = "majandusaasta_lopp")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaLopp;

    /**
     * Gets the value of the aruandeKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAruandeKood() {
        return aruandeKood;
    }

    /**
     * Sets the value of the aruandeKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAruandeKood(String value) {
        this.aruandeKood = value;
    }

    /**
     * Gets the value of the aruandeNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAruandeNimetus() {
        return aruandeNimetus;
    }

    /**
     * Sets the value of the aruandeNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAruandeNimetus(String value) {
        this.aruandeNimetus = value;
    }

    /**
     * Gets the value of the aruandeAasta property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAruandeAasta() {
        return aruandeAasta;
    }

    /**
     * Sets the value of the aruandeAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAruandeAasta(Integer value) {
        this.aruandeAasta = value;
    }

    /**
     * Gets the value of the majandusaastaAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajandusaastaAlgus() {
        return majandusaastaAlgus;
    }

    /**
     * Sets the value of the majandusaastaAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajandusaastaAlgus(XMLGregorianCalendar value) {
        this.majandusaastaAlgus = value;
    }

    /**
     * Gets the value of the majandusaastaLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajandusaastaLopp() {
        return majandusaastaLopp;
    }

    /**
     * Sets the value of the majandusaastaLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajandusaastaLopp(XMLGregorianCalendar value) {
        this.majandusaastaLopp = value;
    }

}
