
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="regNr" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseAndmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppeasutusDetail"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "oppeasutuseId",
    "regNr",
    "nimetus",
    "oppeasutuseAndmed"
})
@XmlRootElement(name = "mtsysLaeOppeasutus")
public class MtsysLaeOppeasutus {

    protected BigInteger oppeasutuseId;
    @XmlElement(required = true)
    protected BigInteger regNr;
    protected String nimetus;
    @XmlElement(required = true)
    protected OppeasutusDetail oppeasutuseAndmed;

    /**
     * Gets the value of the oppeasutuseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeasutuseId() {
        return oppeasutuseId;
    }

    /**
     * Sets the value of the oppeasutuseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeasutuseId(BigInteger value) {
        this.oppeasutuseId = value;
    }

    /**
     * Gets the value of the regNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRegNr() {
        return regNr;
    }

    /**
     * Sets the value of the regNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRegNr(BigInteger value) {
        this.regNr = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the oppeasutuseAndmed property.
     * 
     * @return
     *     possible object is
     *     {@link OppeasutusDetail }
     *     
     */
    public OppeasutusDetail getOppeasutuseAndmed() {
        return oppeasutuseAndmed;
    }

    /**
     * Sets the value of the oppeasutuseAndmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link OppeasutusDetail }
     *     
     */
    public void setOppeasutuseAndmed(OppeasutusDetail value) {
        this.oppeasutuseAndmed = value;
    }

}
