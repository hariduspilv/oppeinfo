
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mtamajpaevik_paevik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtamajpaevik_paevik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rea_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="piirk" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ark" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="maj_aasta" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="maj_aastalopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kontr_olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oleku_muutm_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtamajpaevik_paevik", propOrder = {
    "reaId",
    "piirk",
    "ark",
    "majAasta",
    "majAastalopp",
    "kontrOlek",
    "olekuMuutmKpv"
})
public class MtamajpaevikPaevik {

    @XmlElement(name = "rea_id", required = true)
    protected BigInteger reaId;
    @XmlElement(required = true)
    protected BigInteger piirk;
    @XmlElement(required = true)
    protected BigInteger ark;
    @XmlElement(name = "maj_aasta", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majAasta;
    @XmlElement(name = "maj_aastalopp", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majAastalopp;
    @XmlElement(name = "kontr_olek", required = true)
    protected String kontrOlek;
    @XmlElement(name = "oleku_muutm_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar olekuMuutmKpv;

    /**
     * Gets the value of the reaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getReaId() {
        return reaId;
    }

    /**
     * Sets the value of the reaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setReaId(BigInteger value) {
        this.reaId = value;
    }

    /**
     * Gets the value of the piirk property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPiirk() {
        return piirk;
    }

    /**
     * Sets the value of the piirk property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPiirk(BigInteger value) {
        this.piirk = value;
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
     * Gets the value of the majAasta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajAasta() {
        return majAasta;
    }

    /**
     * Sets the value of the majAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajAasta(XMLGregorianCalendar value) {
        this.majAasta = value;
    }

    /**
     * Gets the value of the majAastalopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajAastalopp() {
        return majAastalopp;
    }

    /**
     * Sets the value of the majAastalopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajAastalopp(XMLGregorianCalendar value) {
        this.majAastalopp = value;
    }

    /**
     * Gets the value of the kontrOlek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKontrOlek() {
        return kontrOlek;
    }

    /**
     * Sets the value of the kontrOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKontrOlek(String value) {
        this.kontrOlek = value;
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

}
