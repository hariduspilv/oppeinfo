
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for rekvisiidid_maj_aruanne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rekvisiidid_maj_aruanne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aasta" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_algus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_lopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="aruande_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esitamise_aeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rekvisiidid_maj_aruanne", propOrder = {
    "aasta",
    "majandusaastaAlgus",
    "majandusaastaLopp",
    "aruandeLiik",
    "esitamiseAeg"
})
public class RekvisiididMajAruanne {

    protected Integer aasta;
    @XmlElement(name = "majandusaasta_algus")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaAlgus;
    @XmlElement(name = "majandusaasta_lopp")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaLopp;
    @XmlElement(name = "aruande_liik")
    protected String aruandeLiik;
    @XmlElement(name = "esitamise_aeg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitamiseAeg;

    /**
     * Gets the value of the aasta property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAasta() {
        return aasta;
    }

    /**
     * Sets the value of the aasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAasta(Integer value) {
        this.aasta = value;
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

    /**
     * Gets the value of the aruandeLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAruandeLiik() {
        return aruandeLiik;
    }

    /**
     * Sets the value of the aruandeLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAruandeLiik(String value) {
        this.aruandeLiik = value;
    }

    /**
     * Gets the value of the esitamiseAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitamiseAeg() {
        return esitamiseAeg;
    }

    /**
     * Sets the value of the esitamiseAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitamiseAeg(XMLGregorianCalendar value) {
        this.esitamiseAeg = value;
    }

}
