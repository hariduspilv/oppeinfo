
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ettevotja_dokument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_dokument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dokumendi_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="dokumendi_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_suurus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_seisu_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_kehtivus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aruande_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aruandeaasta" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_dokument", propOrder = {
    "dokumendiId",
    "ariregistriKood",
    "dokumendiLiik",
    "dokumendiNimetus",
    "dokumendiSuurus",
    "dokumendiSeisuKuupaev",
    "dokumendiKehtivus",
    "aruandeLiik",
    "aruandeaasta",
    "dokumendiUrl"
})
public class EttevotjaDokument {

    @XmlElement(name = "dokumendi_id", required = true)
    protected BigInteger dokumendiId;
    @XmlElement(name = "ariregistri_kood")
    protected int ariregistriKood;
    @XmlElement(name = "dokumendi_liik")
    protected String dokumendiLiik;
    @XmlElement(name = "dokumendi_nimetus")
    protected String dokumendiNimetus;
    @XmlElement(name = "dokumendi_suurus")
    protected Integer dokumendiSuurus;
    @XmlElement(name = "dokumendi_seisu_kuupaev")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dokumendiSeisuKuupaev;
    @XmlElement(name = "dokumendi_kehtivus")
    protected String dokumendiKehtivus;
    @XmlElement(name = "aruande_liik")
    protected String aruandeLiik;
    protected Integer aruandeaasta;
    @XmlElement(name = "dokumendi_url")
    protected String dokumendiUrl;

    /**
     * Gets the value of the dokumendiId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDokumendiId() {
        return dokumendiId;
    }

    /**
     * Sets the value of the dokumendiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDokumendiId(BigInteger value) {
        this.dokumendiId = value;
    }

    /**
     * Gets the value of the ariregistriKood property.
     * 
     */
    public int getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     */
    public void setAriregistriKood(int value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the dokumendiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiLiik() {
        return dokumendiLiik;
    }

    /**
     * Sets the value of the dokumendiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiLiik(String value) {
        this.dokumendiLiik = value;
    }

    /**
     * Gets the value of the dokumendiNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiNimetus() {
        return dokumendiNimetus;
    }

    /**
     * Sets the value of the dokumendiNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiNimetus(String value) {
        this.dokumendiNimetus = value;
    }

    /**
     * Gets the value of the dokumendiSuurus property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDokumendiSuurus() {
        return dokumendiSuurus;
    }

    /**
     * Sets the value of the dokumendiSuurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDokumendiSuurus(Integer value) {
        this.dokumendiSuurus = value;
    }

    /**
     * Gets the value of the dokumendiSeisuKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDokumendiSeisuKuupaev() {
        return dokumendiSeisuKuupaev;
    }

    /**
     * Sets the value of the dokumendiSeisuKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDokumendiSeisuKuupaev(XMLGregorianCalendar value) {
        this.dokumendiSeisuKuupaev = value;
    }

    /**
     * Gets the value of the dokumendiKehtivus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiKehtivus() {
        return dokumendiKehtivus;
    }

    /**
     * Sets the value of the dokumendiKehtivus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiKehtivus(String value) {
        this.dokumendiKehtivus = value;
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
     * Gets the value of the aruandeaasta property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAruandeaasta() {
        return aruandeaasta;
    }

    /**
     * Sets the value of the aruandeaasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAruandeaasta(Integer value) {
        this.aruandeaasta = value;
    }

    /**
     * Gets the value of the dokumendiUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiUrl() {
        return dokumendiUrl;
    }

    /**
     * Sets the value of the dokumendiUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiUrl(String value) {
        this.dokumendiUrl = value;
    }

}
