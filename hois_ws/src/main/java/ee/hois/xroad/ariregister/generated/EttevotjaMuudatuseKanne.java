
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ettevotja_muudatuse_kanne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatuse_kanne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="registrikaardi_piirkond" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="registrikaardi_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registrikaardi_nr" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kande_nr" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kande_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kande_liik" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kande_liik_tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikuandmed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatuse_kanne", propOrder = {
    "registrikaardiPiirkond",
    "registrikaardiTyyp",
    "registrikaardiNr",
    "kandeNr",
    "kandeKuupaev",
    "kandeLiik",
    "kandeLiikTekst",
    "isikuandmed"
})
public class EttevotjaMuudatuseKanne {

    @XmlElement(name = "registrikaardi_piirkond")
    protected Integer registrikaardiPiirkond;
    @XmlElement(name = "registrikaardi_tyyp")
    protected String registrikaardiTyyp;
    @XmlElement(name = "registrikaardi_nr")
    protected Integer registrikaardiNr;
    @XmlElement(name = "kande_nr")
    protected Integer kandeNr;
    @XmlElement(name = "kande_kuupaev")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kandeKuupaev;
    @XmlElement(name = "kande_liik")
    protected Integer kandeLiik;
    @XmlElement(name = "kande_liik_tekst")
    protected String kandeLiikTekst;
    protected String isikuandmed;

    /**
     * Gets the value of the registrikaardiPiirkond property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRegistrikaardiPiirkond() {
        return registrikaardiPiirkond;
    }

    /**
     * Sets the value of the registrikaardiPiirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRegistrikaardiPiirkond(Integer value) {
        this.registrikaardiPiirkond = value;
    }

    /**
     * Gets the value of the registrikaardiTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrikaardiTyyp() {
        return registrikaardiTyyp;
    }

    /**
     * Sets the value of the registrikaardiTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrikaardiTyyp(String value) {
        this.registrikaardiTyyp = value;
    }

    /**
     * Gets the value of the registrikaardiNr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRegistrikaardiNr() {
        return registrikaardiNr;
    }

    /**
     * Sets the value of the registrikaardiNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRegistrikaardiNr(Integer value) {
        this.registrikaardiNr = value;
    }

    /**
     * Gets the value of the kandeNr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandeNr() {
        return kandeNr;
    }

    /**
     * Sets the value of the kandeNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandeNr(Integer value) {
        this.kandeNr = value;
    }

    /**
     * Gets the value of the kandeKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKandeKuupaev() {
        return kandeKuupaev;
    }

    /**
     * Sets the value of the kandeKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKandeKuupaev(XMLGregorianCalendar value) {
        this.kandeKuupaev = value;
    }

    /**
     * Gets the value of the kandeLiik property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandeLiik() {
        return kandeLiik;
    }

    /**
     * Sets the value of the kandeLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandeLiik(Integer value) {
        this.kandeLiik = value;
    }

    /**
     * Gets the value of the kandeLiikTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeLiikTekst() {
        return kandeLiikTekst;
    }

    /**
     * Sets the value of the kandeLiikTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeLiikTekst(String value) {
        this.kandeLiikTekst = value;
    }

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuandmed(String value) {
        this.isikuandmed = value;
    }

}
