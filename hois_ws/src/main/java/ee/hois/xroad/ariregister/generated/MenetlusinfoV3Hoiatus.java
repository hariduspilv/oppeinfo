
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for menetlusinfo_v3_hoiatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="menetlusinfo_v3_hoiatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hoiat_numb" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paevikukande_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paevikukande_id_vana" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hoiat_kuup" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="hoiat_nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hoiat_tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menetlusinfo_v3_hoiatus", propOrder = {
    "hoiatNumb",
    "paevikukandeId",
    "paevikukandeIdVana",
    "hoiatKuup",
    "hoiatNimetus",
    "hoiatTekst"
})
public class MenetlusinfoV3Hoiatus {

    @XmlElement(name = "hoiat_numb", required = true)
    protected String hoiatNumb;
    @XmlElement(name = "paevikukande_id", required = true)
    protected String paevikukandeId;
    @XmlElement(name = "paevikukande_id_vana", required = true)
    protected String paevikukandeIdVana;
    @XmlElement(name = "hoiat_kuup", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar hoiatKuup;
    @XmlElement(name = "hoiat_nimetus", required = true)
    protected String hoiatNimetus;
    @XmlElement(name = "hoiat_tekst")
    protected String hoiatTekst;

    /**
     * Gets the value of the hoiatNumb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatNumb() {
        return hoiatNumb;
    }

    /**
     * Sets the value of the hoiatNumb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatNumb(String value) {
        this.hoiatNumb = value;
    }

    /**
     * Gets the value of the paevikukandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaevikukandeId() {
        return paevikukandeId;
    }

    /**
     * Sets the value of the paevikukandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaevikukandeId(String value) {
        this.paevikukandeId = value;
    }

    /**
     * Gets the value of the paevikukandeIdVana property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaevikukandeIdVana() {
        return paevikukandeIdVana;
    }

    /**
     * Sets the value of the paevikukandeIdVana property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaevikukandeIdVana(String value) {
        this.paevikukandeIdVana = value;
    }

    /**
     * Gets the value of the hoiatKuup property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHoiatKuup() {
        return hoiatKuup;
    }

    /**
     * Sets the value of the hoiatKuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHoiatKuup(XMLGregorianCalendar value) {
        this.hoiatKuup = value;
    }

    /**
     * Gets the value of the hoiatNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatNimetus() {
        return hoiatNimetus;
    }

    /**
     * Sets the value of the hoiatNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatNimetus(String value) {
        this.hoiatNimetus = value;
    }

    /**
     * Gets the value of the hoiatTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatTekst() {
        return hoiatTekst;
    }

    /**
     * Sets the value of the hoiatTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatTekst(String value) {
        this.hoiatTekst = value;
    }

}
