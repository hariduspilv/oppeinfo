
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mtapaevik_paevik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtapaevik_paevik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paevik_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="piirkond" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="rahuldatud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="markus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dok_liik" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="kandeliik" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="esit_kp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="ark" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="arinimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "mtapaevik_paevik", propOrder = {
    "paevikId",
    "piirkond",
    "rahuldatud",
    "olek",
    "markus",
    "dokLiik",
    "kandeliik",
    "esitKp",
    "ark",
    "arinimi",
    "muutus",
    "mkuup"
})
public class MtapaevikPaevik {

    @XmlElement(name = "paevik_id", required = true)
    protected BigInteger paevikId;
    @XmlElement(required = true)
    protected BigInteger piirkond;
    @XmlElement(required = true, nillable = true)
    protected String rahuldatud;
    @XmlElement(required = true)
    protected String olek;
    @XmlElement(required = true, nillable = true)
    protected String markus;
    @XmlElement(name = "dok_liik", required = true)
    protected BigInteger dokLiik;
    @XmlElement(required = true, nillable = true)
    protected BigInteger kandeliik;
    @XmlElement(name = "esit_kp", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitKp;
    @XmlElement(required = true)
    protected BigInteger ark;
    @XmlElement(required = true)
    protected String arinimi;
    @XmlElement(required = true)
    protected String muutus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar mkuup;

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
     * Gets the value of the rahuldatud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRahuldatud() {
        return rahuldatud;
    }

    /**
     * Sets the value of the rahuldatud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRahuldatud(String value) {
        this.rahuldatud = value;
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
     * Gets the value of the markus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarkus() {
        return markus;
    }

    /**
     * Sets the value of the markus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarkus(String value) {
        this.markus = value;
    }

    /**
     * Gets the value of the dokLiik property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDokLiik() {
        return dokLiik;
    }

    /**
     * Sets the value of the dokLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDokLiik(BigInteger value) {
        this.dokLiik = value;
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
     * Gets the value of the esitKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitKp() {
        return esitKp;
    }

    /**
     * Sets the value of the esitKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitKp(XMLGregorianCalendar value) {
        this.esitKp = value;
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
     * Gets the value of the arinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArinimi() {
        return arinimi;
    }

    /**
     * Sets the value of the arinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArinimi(String value) {
        this.arinimi = value;
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
