
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mtahoiatused_hoiatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtahoiatused_hoiatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hoiat_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ark" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oleku_muutm_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="maj_paevik_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="paevik_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtahoiatused_hoiatus", propOrder = {
    "hoiatId",
    "ark",
    "liik",
    "kuupaev",
    "olek",
    "olekuMuutmKpv",
    "majPaevikId",
    "paevikId"
})
public class MtahoiatusedHoiatus {

    @XmlElement(name = "hoiat_id", required = true)
    protected BigInteger hoiatId;
    @XmlElement(required = true, nillable = true)
    protected BigInteger ark;
    @XmlElement(required = true)
    protected String liik;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuupaev;
    @XmlElement(required = true)
    protected String olek;
    @XmlElement(name = "oleku_muutm_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar olekuMuutmKpv;
    @XmlElement(name = "maj_paevik_id", required = true, nillable = true)
    protected BigInteger majPaevikId;
    @XmlElement(name = "paevik_id", required = true, nillable = true)
    protected BigInteger paevikId;

    /**
     * Gets the value of the hoiatId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHoiatId() {
        return hoiatId;
    }

    /**
     * Sets the value of the hoiatId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHoiatId(BigInteger value) {
        this.hoiatId = value;
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
     * Gets the value of the kuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKuupaev() {
        return kuupaev;
    }

    /**
     * Sets the value of the kuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKuupaev(XMLGregorianCalendar value) {
        this.kuupaev = value;
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
     * Gets the value of the olekuMuutmKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOlekuMuutmKpv() {
        return olekuMuutmKpv;
    }

    /**
     * Sets the value of the olekuMuutmKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOlekuMuutmKpv(XMLGregorianCalendar value) {
        this.olekuMuutmKpv = value;
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

}
