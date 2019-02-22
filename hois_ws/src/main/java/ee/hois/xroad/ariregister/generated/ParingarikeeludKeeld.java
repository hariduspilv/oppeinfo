
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for paringarikeelud_keeld complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringarikeelud_keeld"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fyysilise_isiku_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fyysilise_isiku_valiskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fyysilise_isiku_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fyysilise_isiku_perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fyysilise_isiku_synniaeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kehtivuse_algus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kehtivuse_lopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ulatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringarikeelud_keeld", propOrder = {
    "fyysiliseIsikuKood",
    "fyysiliseIsikuValiskood",
    "fyysiliseIsikuEesnimi",
    "fyysiliseIsikuPerenimi",
    "fyysiliseIsikuSynniaeg",
    "kehtivuseAlgus",
    "kehtivuseLopp",
    "ulatus",
    "liik",
    "liikTekstina",
    "olek",
    "olekTekstina"
})
public class ParingarikeeludKeeld {

    @XmlElement(name = "fyysilise_isiku_kood", required = true, nillable = true)
    protected String fyysiliseIsikuKood;
    @XmlElement(name = "fyysilise_isiku_valiskood", required = true, nillable = true)
    protected String fyysiliseIsikuValiskood;
    @XmlElement(name = "fyysilise_isiku_eesnimi", required = true, nillable = true)
    protected String fyysiliseIsikuEesnimi;
    @XmlElement(name = "fyysilise_isiku_perenimi", required = true, nillable = true)
    protected String fyysiliseIsikuPerenimi;
    @XmlElement(name = "fyysilise_isiku_synniaeg", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fyysiliseIsikuSynniaeg;
    @XmlElement(name = "kehtivuse_algus", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kehtivuseAlgus;
    @XmlElement(name = "kehtivuse_lopp", required = true, nillable = true)
    protected String kehtivuseLopp;
    @XmlElement(required = true, nillable = true)
    protected String ulatus;
    @XmlElement(required = true, nillable = true)
    protected String liik;
    @XmlElement(name = "liik_tekstina", required = true, nillable = true)
    protected String liikTekstina;
    @XmlElement(required = true, nillable = true)
    protected String olek;
    @XmlElement(name = "olek_tekstina", required = true, nillable = true)
    protected String olekTekstina;

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
     * Gets the value of the fyysiliseIsikuValiskood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuValiskood() {
        return fyysiliseIsikuValiskood;
    }

    /**
     * Sets the value of the fyysiliseIsikuValiskood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuValiskood(String value) {
        this.fyysiliseIsikuValiskood = value;
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

    /**
     * Gets the value of the kehtivuseAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKehtivuseAlgus() {
        return kehtivuseAlgus;
    }

    /**
     * Sets the value of the kehtivuseAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKehtivuseAlgus(XMLGregorianCalendar value) {
        this.kehtivuseAlgus = value;
    }

    /**
     * Gets the value of the kehtivuseLopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivuseLopp() {
        return kehtivuseLopp;
    }

    /**
     * Sets the value of the kehtivuseLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivuseLopp(String value) {
        this.kehtivuseLopp = value;
    }

    /**
     * Gets the value of the ulatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUlatus() {
        return ulatus;
    }

    /**
     * Sets the value of the ulatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUlatus(String value) {
        this.ulatus = value;
    }

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiik(String value) {
        this.liik = value;
    }

    /**
     * Gets the value of the liikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiikTekstina() {
        return liikTekstina;
    }

    /**
     * Sets the value of the liikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiikTekstina(String value) {
        this.liikTekstina = value;
    }

    /**
     * Gets the value of the olek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOlek() {
        return olek;
    }

    /**
     * Sets the value of the olek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOlek(String value) {
        this.olek = value;
    }

    /**
     * Gets the value of the olekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOlekTekstina() {
        return olekTekstina;
    }

    /**
     * Sets the value of the olekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOlekTekstina(String value) {
        this.olekTekstina = value;
    }

}
