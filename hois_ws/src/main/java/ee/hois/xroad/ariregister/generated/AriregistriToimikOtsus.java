
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ariregistri_toimik_otsus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ariregistri_toimik_otsus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="otsuse_number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="otsuse_liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_liik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="joust_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kande_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lisatahtaeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kandeliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kandeliik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="maaruse_tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ariregistri_toimik_otsus", propOrder = {
    "otsuseNumber",
    "otsuseKpv",
    "otsuseLiik",
    "otsuseLiikTekstina",
    "otsuseOlek",
    "otsuseOlekTekstina",
    "joustKpv",
    "kandeKpv",
    "lisatahtaeg",
    "kandeliik",
    "kandeliikTekstina",
    "maaruseTekst"
})
public class AriregistriToimikOtsus {

    @XmlElement(name = "otsuse_number", required = true)
    protected String otsuseNumber;
    @XmlElement(name = "otsuse_kpv", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar otsuseKpv;
    @XmlElement(name = "otsuse_liik", required = true, nillable = true)
    protected String otsuseLiik;
    @XmlElement(name = "otsuse_liik_tekstina", required = true, nillable = true)
    protected String otsuseLiikTekstina;
    @XmlElement(name = "otsuse_olek", required = true, nillable = true)
    protected String otsuseOlek;
    @XmlElement(name = "otsuse_olek_tekstina", required = true, nillable = true)
    protected String otsuseOlekTekstina;
    @XmlElement(name = "joust_kpv", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar joustKpv;
    @XmlElement(name = "kande_kpv", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kandeKpv;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lisatahtaeg;
    @XmlElement(required = true, nillable = true)
    protected String kandeliik;
    @XmlElement(name = "kandeliik_tekstina", required = true, nillable = true)
    protected String kandeliikTekstina;
    @XmlElement(name = "maaruse_tekst", required = true, nillable = true)
    protected String maaruseTekst;

    /**
     * Gets the value of the otsuseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsuseNumber() {
        return otsuseNumber;
    }

    /**
     * Sets the value of the otsuseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsuseNumber(String value) {
        this.otsuseNumber = value;
    }

    /**
     * Gets the value of the otsuseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOtsuseKpv() {
        return otsuseKpv;
    }

    /**
     * Sets the value of the otsuseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOtsuseKpv(XMLGregorianCalendar value) {
        this.otsuseKpv = value;
    }

    /**
     * Gets the value of the otsuseLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsuseLiik() {
        return otsuseLiik;
    }

    /**
     * Sets the value of the otsuseLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsuseLiik(String value) {
        this.otsuseLiik = value;
    }

    /**
     * Gets the value of the otsuseLiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsuseLiikTekstina() {
        return otsuseLiikTekstina;
    }

    /**
     * Sets the value of the otsuseLiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsuseLiikTekstina(String value) {
        this.otsuseLiikTekstina = value;
    }

    /**
     * Gets the value of the otsuseOlek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsuseOlek() {
        return otsuseOlek;
    }

    /**
     * Sets the value of the otsuseOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsuseOlek(String value) {
        this.otsuseOlek = value;
    }

    /**
     * Gets the value of the otsuseOlekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsuseOlekTekstina() {
        return otsuseOlekTekstina;
    }

    /**
     * Sets the value of the otsuseOlekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsuseOlekTekstina(String value) {
        this.otsuseOlekTekstina = value;
    }

    /**
     * Gets the value of the joustKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getJoustKpv() {
        return joustKpv;
    }

    /**
     * Sets the value of the joustKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setJoustKpv(XMLGregorianCalendar value) {
        this.joustKpv = value;
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

    /**
     * Gets the value of the maaruseTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseTekst() {
        return maaruseTekst;
    }

    /**
     * Sets the value of the maaruseTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseTekst(String value) {
        this.maaruseTekst = value;
    }

}
