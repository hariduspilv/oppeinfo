
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisInfoteade complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisInfoteade"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppekavaNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisInfoteade", propOrder = {
    "oppekavaNimetus",
    "oppekavaKood",
    "oppekavaStaatus",
    "teade",
    "veakood"
})
public class OisInfoteade {

    protected String oppekavaNimetus;
    protected BigInteger oppekavaKood;
    protected String oppekavaStaatus;
    @XmlElement(required = true)
    protected String teade;
    @XmlElement(required = true)
    protected BigInteger veakood;

    /**
     * Gets the value of the oppekavaNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaNimetus() {
        return oppekavaNimetus;
    }

    /**
     * Sets the value of the oppekavaNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaNimetus(String value) {
        this.oppekavaNimetus = value;
    }

    /**
     * Gets the value of the oppekavaKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekavaKood() {
        return oppekavaKood;
    }

    /**
     * Sets the value of the oppekavaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekavaKood(BigInteger value) {
        this.oppekavaKood = value;
    }

    /**
     * Gets the value of the oppekavaStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaStaatus() {
        return oppekavaStaatus;
    }

    /**
     * Sets the value of the oppekavaStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaStaatus(String value) {
        this.oppekavaStaatus = value;
    }

    /**
     * Gets the value of the teade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeade() {
        return teade;
    }

    /**
     * Sets the value of the teade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeade(String value) {
        this.teade = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

}
