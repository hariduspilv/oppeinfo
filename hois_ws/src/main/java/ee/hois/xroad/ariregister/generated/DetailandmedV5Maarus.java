
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_maarus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_maarus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="maaruse_nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="maaruse_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="maaruse_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="maaruse_liik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="maaruse_olek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="maaruse_olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="joustumise_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="joust_olek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="joust_olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisatahtaeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kandeliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kandeliik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_maarus", propOrder = {
    "maaruseNr",
    "maaruseKpv",
    "maaruseLiik",
    "maaruseLiikTekstina",
    "maaruseOlek",
    "maaruseOlekTekstina",
    "kandeKpv",
    "joustumiseKpv",
    "joustOlek",
    "joustOlekTekstina",
    "lisatahtaeg",
    "kandeliik",
    "kandeliikTekstina"
})
public class DetailandmedV5Maarus {

    @XmlElement(name = "maaruse_nr")
    protected String maaruseNr;
    @XmlElement(name = "maaruse_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maaruseKpv;
    @XmlElement(name = "maaruse_liik")
    protected String maaruseLiik;
    @XmlElement(name = "maaruse_liik_tekstina")
    protected String maaruseLiikTekstina;
    @XmlElement(name = "maaruse_olek")
    protected String maaruseOlek;
    @XmlElement(name = "maaruse_olek_tekstina")
    protected String maaruseOlekTekstina;
    @XmlElement(name = "kande_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kandeKpv;
    @XmlElement(name = "joustumise_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar joustumiseKpv;
    @XmlElement(name = "joust_olek")
    protected String joustOlek;
    @XmlElement(name = "joust_olek_tekstina")
    protected String joustOlekTekstina;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lisatahtaeg;
    protected String kandeliik;
    @XmlElement(name = "kandeliik_tekstina")
    protected String kandeliikTekstina;

    /**
     * Gets the value of the maaruseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseNr() {
        return maaruseNr;
    }

    /**
     * Sets the value of the maaruseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseNr(String value) {
        this.maaruseNr = value;
    }

    /**
     * Gets the value of the maaruseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaaruseKpv() {
        return maaruseKpv;
    }

    /**
     * Sets the value of the maaruseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaaruseKpv(XMLGregorianCalendar value) {
        this.maaruseKpv = value;
    }

    /**
     * Gets the value of the maaruseLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseLiik() {
        return maaruseLiik;
    }

    /**
     * Sets the value of the maaruseLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseLiik(String value) {
        this.maaruseLiik = value;
    }

    /**
     * Gets the value of the maaruseLiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseLiikTekstina() {
        return maaruseLiikTekstina;
    }

    /**
     * Sets the value of the maaruseLiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseLiikTekstina(String value) {
        this.maaruseLiikTekstina = value;
    }

    /**
     * Gets the value of the maaruseOlek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseOlek() {
        return maaruseOlek;
    }

    /**
     * Sets the value of the maaruseOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseOlek(String value) {
        this.maaruseOlek = value;
    }

    /**
     * Gets the value of the maaruseOlekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseOlekTekstina() {
        return maaruseOlekTekstina;
    }

    /**
     * Sets the value of the maaruseOlekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseOlekTekstina(String value) {
        this.maaruseOlekTekstina = value;
    }

    /**
     * Gets the value of the kandeKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKandeKpv() {
        return kandeKpv;
    }

    /**
     * Sets the value of the kandeKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKandeKpv(XMLGregorianCalendar value) {
        this.kandeKpv = value;
    }

    /**
     * Gets the value of the joustumiseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getJoustumiseKpv() {
        return joustumiseKpv;
    }

    /**
     * Sets the value of the joustumiseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setJoustumiseKpv(XMLGregorianCalendar value) {
        this.joustumiseKpv = value;
    }

    /**
     * Gets the value of the joustOlek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoustOlek() {
        return joustOlek;
    }

    /**
     * Sets the value of the joustOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoustOlek(String value) {
        this.joustOlek = value;
    }

    /**
     * Gets the value of the joustOlekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoustOlekTekstina() {
        return joustOlekTekstina;
    }

    /**
     * Sets the value of the joustOlekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoustOlekTekstina(String value) {
        this.joustOlekTekstina = value;
    }

    /**
     * Gets the value of the lisatahtaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLisatahtaeg() {
        return lisatahtaeg;
    }

    /**
     * Sets the value of the lisatahtaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLisatahtaeg(XMLGregorianCalendar value) {
        this.lisatahtaeg = value;
    }

    /**
     * Gets the value of the kandeliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeliik() {
        return kandeliik;
    }

    /**
     * Sets the value of the kandeliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeliik(String value) {
        this.kandeliik = value;
    }

    /**
     * Gets the value of the kandeliikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeliikTekstina() {
        return kandeliikTekstina;
    }

    /**
     * Sets the value of the kandeliikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeliikTekstina(String value) {
        this.kandeliikTekstina = value;
    }

}
