
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ariregistri_toimik_hoiatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ariregistri_toimik_hoiatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hoiatuse_number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hoiatuse_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="hoiatuse_liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hoiatuse_liik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hoiatuse_olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hoiatuse_olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tahtaeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="menetleja_id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="menetleja_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hoiatuse_tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ariregistri_toimik_hoiatus", propOrder = {
    "hoiatuseNumber",
    "hoiatuseKpv",
    "hoiatuseLiik",
    "hoiatuseLiikTekstina",
    "hoiatuseOlek",
    "hoiatuseOlekTekstina",
    "tahtaeg",
    "menetlejaId",
    "menetlejaNimi",
    "hoiatuseTekst"
})
public class AriregistriToimikHoiatus {

    @XmlElement(name = "hoiatuse_number", required = true)
    protected String hoiatuseNumber;
    @XmlElement(name = "hoiatuse_kpv", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar hoiatuseKpv;
    @XmlElement(name = "hoiatuse_liik", required = true, nillable = true)
    protected String hoiatuseLiik;
    @XmlElement(name = "hoiatuse_liik_tekstina", required = true, nillable = true)
    protected String hoiatuseLiikTekstina;
    @XmlElement(name = "hoiatuse_olek", required = true, nillable = true)
    protected String hoiatuseOlek;
    @XmlElement(name = "hoiatuse_olek_tekstina", required = true, nillable = true)
    protected String hoiatuseOlekTekstina;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tahtaeg;
    @XmlElement(name = "menetleja_id", required = true, type = Integer.class, nillable = true)
    protected Integer menetlejaId;
    @XmlElement(name = "menetleja_nimi", required = true, nillable = true)
    protected String menetlejaNimi;
    @XmlElement(name = "hoiatuse_tekst", required = true, nillable = true)
    protected String hoiatuseTekst;

    /**
     * Gets the value of the hoiatuseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatuseNumber() {
        return hoiatuseNumber;
    }

    /**
     * Sets the value of the hoiatuseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatuseNumber(String value) {
        this.hoiatuseNumber = value;
    }

    /**
     * Gets the value of the hoiatuseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHoiatuseKpv() {
        return hoiatuseKpv;
    }

    /**
     * Sets the value of the hoiatuseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHoiatuseKpv(XMLGregorianCalendar value) {
        this.hoiatuseKpv = value;
    }

    /**
     * Gets the value of the hoiatuseLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatuseLiik() {
        return hoiatuseLiik;
    }

    /**
     * Sets the value of the hoiatuseLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatuseLiik(String value) {
        this.hoiatuseLiik = value;
    }

    /**
     * Gets the value of the hoiatuseLiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatuseLiikTekstina() {
        return hoiatuseLiikTekstina;
    }

    /**
     * Sets the value of the hoiatuseLiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatuseLiikTekstina(String value) {
        this.hoiatuseLiikTekstina = value;
    }

    /**
     * Gets the value of the hoiatuseOlek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatuseOlek() {
        return hoiatuseOlek;
    }

    /**
     * Sets the value of the hoiatuseOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatuseOlek(String value) {
        this.hoiatuseOlek = value;
    }

    /**
     * Gets the value of the hoiatuseOlekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatuseOlekTekstina() {
        return hoiatuseOlekTekstina;
    }

    /**
     * Sets the value of the hoiatuseOlekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatuseOlekTekstina(String value) {
        this.hoiatuseOlekTekstina = value;
    }

    /**
     * Gets the value of the tahtaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTahtaeg() {
        return tahtaeg;
    }

    /**
     * Sets the value of the tahtaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTahtaeg(XMLGregorianCalendar value) {
        this.tahtaeg = value;
    }

    /**
     * Gets the value of the menetlejaId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMenetlejaId() {
        return menetlejaId;
    }

    /**
     * Sets the value of the menetlejaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMenetlejaId(Integer value) {
        this.menetlejaId = value;
    }

    /**
     * Gets the value of the menetlejaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenetlejaNimi() {
        return menetlejaNimi;
    }

    /**
     * Sets the value of the menetlejaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenetlejaNimi(String value) {
        this.menetlejaNimi = value;
    }

    /**
     * Gets the value of the hoiatuseTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoiatuseTekst() {
        return hoiatuseTekst;
    }

    /**
     * Sets the value of the hoiatuseTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoiatuseTekst(String value) {
        this.hoiatuseTekst = value;
    }

}
