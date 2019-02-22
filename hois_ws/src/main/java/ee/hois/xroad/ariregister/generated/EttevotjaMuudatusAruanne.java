
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ettevotja_muudatus_aruanne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus_aruanne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "ettevotja_muudatus_aruanne", propOrder = {
    "ettevotjaId",
    "ettevotjaAriregistriKood",
    "ettevotjaArinimi",
    "ettevotjaOiguslikVorm",
    "majandusaastaAlgus",
    "majandusaastaLopp"
})
public class EttevotjaMuudatusAruanne {

    @XmlElement(name = "ettevotja_id")
    protected String ettevotjaId;
    @XmlElement(name = "ettevotja_ariregistri_kood")
    protected Integer ettevotjaAriregistriKood;
    @XmlElement(name = "ettevotja_arinimi")
    protected String ettevotjaArinimi;
    @XmlElement(name = "ettevotja_oiguslik_vorm")
    protected String ettevotjaOiguslikVorm;
    @XmlElement(name = "majandusaasta_algus")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaAlgus;
    @XmlElement(name = "majandusaasta_lopp")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majandusaastaLopp;

    /**
     * Gets the value of the ettevotjaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaId() {
        return ettevotjaId;
    }

    /**
     * Sets the value of the ettevotjaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaId(String value) {
        this.ettevotjaId = value;
    }

    /**
     * Gets the value of the ettevotjaAriregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEttevotjaAriregistriKood() {
        return ettevotjaAriregistriKood;
    }

    /**
     * Sets the value of the ettevotjaAriregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEttevotjaAriregistriKood(Integer value) {
        this.ettevotjaAriregistriKood = value;
    }

    /**
     * Gets the value of the ettevotjaArinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaArinimi() {
        return ettevotjaArinimi;
    }

    /**
     * Sets the value of the ettevotjaArinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaArinimi(String value) {
        this.ettevotjaArinimi = value;
    }

    /**
     * Gets the value of the ettevotjaOiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaOiguslikVorm() {
        return ettevotjaOiguslikVorm;
    }

    /**
     * Sets the value of the ettevotjaOiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaOiguslikVorm(String value) {
        this.ettevotjaOiguslikVorm = value;
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
