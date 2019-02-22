
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mtaotsused_otsus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtaotsused_otsus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="otsuse_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ark" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="otsuse_liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piirkond" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="otsuse_olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lisatahtaeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kandeliik" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="joust" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="joust_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="paevik_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="maj_paevik_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="muutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mkuup" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtaotsused_otsus", propOrder = {
    "otsuseId",
    "ark",
    "otsuseLiik",
    "otsuseNr",
    "piirkond",
    "otsuseOlek",
    "otsuseKpv",
    "lisatahtaeg",
    "kandeliik",
    "joust",
    "joustKpv",
    "paevikId",
    "majPaevikId",
    "muutus",
    "mkuup"
})
public class MtaotsusedOtsus {

    @XmlElement(name = "otsuse_id", required = true)
    protected BigInteger otsuseId;
    @XmlElement(required = true, nillable = true)
    protected BigInteger ark;
    @XmlElement(name = "otsuse_liik", required = true, nillable = true)
    protected String otsuseLiik;
    @XmlElement(name = "otsuse_nr", required = true, nillable = true)
    protected String otsuseNr;
    @XmlElement(required = true, nillable = true)
    protected BigInteger piirkond;
    @XmlElement(name = "otsuse_olek", required = true, nillable = true)
    protected String otsuseOlek;
    @XmlElement(name = "otsuse_kpv", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar otsuseKpv;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lisatahtaeg;
    @XmlElement(required = true, nillable = true)
    protected BigInteger kandeliik;
    @XmlElement(required = true, nillable = true)
    protected String joust;
    @XmlElement(name = "joust_kpv", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar joustKpv;
    @XmlElement(name = "paevik_id", required = true, nillable = true)
    protected BigInteger paevikId;
    @XmlElement(name = "maj_paevik_id", required = true, nillable = true)
    protected BigInteger majPaevikId;
    @XmlElement(required = true, nillable = true)
    protected String muutus;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar mkuup;

    /**
     * Gets the value of the otsuseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOtsuseId() {
        return otsuseId;
    }

    /**
     * Sets the value of the otsuseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOtsuseId(BigInteger value) {
        this.otsuseId = value;
    }

    /**
     * Gets the value of the ark property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getArk() {
        return ark;
    }

    /**
     * Sets the value of the ark property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setArk(BigInteger value) {
        this.ark = value;
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
     * Gets the value of the otsuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsuseNr() {
        return otsuseNr;
    }

    /**
     * Sets the value of the otsuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsuseNr(String value) {
        this.otsuseNr = value;
    }

    /**
     * Gets the value of the piirkond property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPiirkond() {
        return piirkond;
    }

    /**
     * Sets the value of the piirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPiirkond(BigInteger value) {
        this.piirkond = value;
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
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKandeliik() {
        return kandeliik;
    }

    /**
     * Sets the value of the kandeliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKandeliik(BigInteger value) {
        this.kandeliik = value;
    }

    /**
     * Gets the value of the joust property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoust() {
        return joust;
    }

    /**
     * Sets the value of the joust property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoust(String value) {
        this.joust = value;
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
     * Gets the value of the paevikId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPaevikId() {
        return paevikId;
    }

    /**
     * Sets the value of the paevikId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPaevikId(BigInteger value) {
        this.paevikId = value;
    }

    /**
     * Gets the value of the majPaevikId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMajPaevikId() {
        return majPaevikId;
    }

    /**
     * Sets the value of the majPaevikId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMajPaevikId(BigInteger value) {
        this.majPaevikId = value;
    }

    /**
     * Gets the value of the muutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuutus() {
        return muutus;
    }

    /**
     * Sets the value of the muutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuutus(String value) {
        this.muutus = value;
    }

    /**
     * Gets the value of the mkuup property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMkuup() {
        return mkuup;
    }

    /**
     * Sets the value of the mkuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMkuup(XMLGregorianCalendar value) {
        this.mkuup = value;
    }

}
