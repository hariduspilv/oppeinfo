
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtabilsisuv2_aruanne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtabilsisuv2_aruanne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bil_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="maketi_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="naitaja_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="bil_veerg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vaartus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtabilsisuv2_aruanne", propOrder = {
    "bilId",
    "maketiId",
    "naitajaId",
    "bilVeerg",
    "vaartus"
})
public class Mtabilsisuv2Aruanne {

    @XmlElement(name = "bil_id", required = true)
    protected BigInteger bilId;
    @XmlElement(name = "maketi_id", required = true)
    protected String maketiId;
    @XmlElement(name = "naitaja_id", required = true)
    protected BigInteger naitajaId;
    @XmlElement(name = "bil_veerg", required = true)
    protected String bilVeerg;
    @XmlElement(required = true)
    protected String vaartus;

    /**
     * Gets the value of the bilId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBilId() {
        return bilId;
    }

    /**
     * Sets the value of the bilId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBilId(BigInteger value) {
        this.bilId = value;
    }

    /**
     * Gets the value of the maketiId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaketiId() {
        return maketiId;
    }

    /**
     * Sets the value of the maketiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaketiId(String value) {
        this.maketiId = value;
    }

    /**
     * Gets the value of the naitajaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNaitajaId() {
        return naitajaId;
    }

    /**
     * Sets the value of the naitajaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNaitajaId(BigInteger value) {
        this.naitajaId = value;
    }

    /**
     * Gets the value of the bilVeerg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBilVeerg() {
        return bilVeerg;
    }

    /**
     * Sets the value of the bilVeerg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBilVeerg(String value) {
        this.bilVeerg = value;
    }

    /**
     * Gets the value of the vaartus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaartus() {
        return vaartus;
    }

    /**
     * Sets the value of the vaartus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaartus(String value) {
        this.vaartus = value;
    }

}
