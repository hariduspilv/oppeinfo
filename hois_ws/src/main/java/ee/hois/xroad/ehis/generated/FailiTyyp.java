
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for failiTyyp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="failiTyyp"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klOkLiik" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="klFailTyyp" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="kohustuslik" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="okLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="failTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "failiTyyp", propOrder = {
    "klOkLiik",
    "klFailTyyp",
    "kohustuslik",
    "okLiik",
    "failTyyp"
})
public class FailiTyyp {

    @XmlElement(required = true)
    protected BigInteger klOkLiik;
    @XmlElement(required = true)
    protected BigInteger klFailTyyp;
    @XmlElement(required = true)
    protected BigInteger kohustuslik;
    @XmlElement(required = true)
    protected String okLiik;
    @XmlElement(required = true)
    protected String failTyyp;

    /**
     * Gets the value of the klOkLiik property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlOkLiik() {
        return klOkLiik;
    }

    /**
     * Sets the value of the klOkLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlOkLiik(BigInteger value) {
        this.klOkLiik = value;
    }

    /**
     * Gets the value of the klFailTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlFailTyyp() {
        return klFailTyyp;
    }

    /**
     * Sets the value of the klFailTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlFailTyyp(BigInteger value) {
        this.klFailTyyp = value;
    }

    /**
     * Gets the value of the kohustuslik property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKohustuslik() {
        return kohustuslik;
    }

    /**
     * Sets the value of the kohustuslik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKohustuslik(BigInteger value) {
        this.kohustuslik = value;
    }

    /**
     * Gets the value of the okLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOkLiik() {
        return okLiik;
    }

    /**
     * Sets the value of the okLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOkLiik(String value) {
        this.okLiik = value;
    }

    /**
     * Gets the value of the failTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailTyyp() {
        return failTyyp;
    }

    /**
     * Sets the value of the failTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailTyyp(String value) {
        this.failTyyp = value;
    }

}
