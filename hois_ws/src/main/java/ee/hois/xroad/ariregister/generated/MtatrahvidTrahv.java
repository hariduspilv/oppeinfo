
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mtatrahvid_trahv complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtatrahvid_trahv"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="trahvi_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ark" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="otsuse_liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oleku_muutm_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liignimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="synnipaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="otsused_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtatrahvid_trahv", propOrder = {
    "trahviId",
    "ark",
    "otsuseLiik",
    "otsuseKpv",
    "olek",
    "olekuMuutmKpv",
    "eesnimi",
    "liignimi",
    "isikukood",
    "synnipaev",
    "otsusedId"
})
public class MtatrahvidTrahv {

    @XmlElement(name = "trahvi_id", required = true)
    protected BigInteger trahviId;
    @XmlElement(required = true)
    protected BigInteger ark;
    @XmlElement(name = "otsuse_liik", required = true)
    protected String otsuseLiik;
    @XmlElement(name = "otsuse_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar otsuseKpv;
    @XmlElement(required = true)
    protected String olek;
    @XmlElement(name = "oleku_muutm_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar olekuMuutmKpv;
    @XmlElement(required = true, nillable = true)
    protected String eesnimi;
    @XmlElement(required = true, nillable = true)
    protected String liignimi;
    @XmlElement(required = true, nillable = true)
    protected String isikukood;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synnipaev;
    @XmlElement(name = "otsused_id", required = true)
    protected BigInteger otsusedId;

    /**
     * Gets the value of the trahviId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTrahviId() {
        return trahviId;
    }

    /**
     * Sets the value of the trahviId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTrahviId(BigInteger value) {
        this.trahviId = value;
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
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the liignimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiignimi() {
        return liignimi;
    }

    /**
     * Sets the value of the liignimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiignimi(String value) {
        this.liignimi = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the synnipaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynnipaev() {
        return synnipaev;
    }

    /**
     * Sets the value of the synnipaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynnipaev(XMLGregorianCalendar value) {
        this.synnipaev = value;
    }

    /**
     * Gets the value of the otsusedId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOtsusedId() {
        return otsusedId;
    }

    /**
     * Sets the value of the otsusedId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOtsusedId(BigInteger value) {
        this.otsusedId = value;
    }

}
