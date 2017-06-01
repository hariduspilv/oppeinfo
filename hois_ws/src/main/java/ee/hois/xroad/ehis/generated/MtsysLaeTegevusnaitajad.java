
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="aruandeId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="aasta" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="naitajad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}naitajad" minOccurs="0"/&gt;
 *         &lt;element name="fail" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
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
    "aruandeId",
    "oppeasutusId",
    "aasta",
    "naitajad",
    "fail"
})
@XmlRootElement(name = "mtsysLaeTegevusnaitajad")
public class MtsysLaeTegevusnaitajad {

    protected BigInteger aruandeId;
    protected BigInteger oppeasutusId;
    protected BigInteger aasta;
    protected Naitajad naitajad;
    protected byte[] fail;

    /**
     * Gets the value of the aruandeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAruandeId() {
        return aruandeId;
    }

    /**
     * Sets the value of the aruandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAruandeId(BigInteger value) {
        this.aruandeId = value;
    }

    /**
     * Gets the value of the oppeasutusId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeasutusId() {
        return oppeasutusId;
    }

    /**
     * Sets the value of the oppeasutusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeasutusId(BigInteger value) {
        this.oppeasutusId = value;
    }

    /**
     * Gets the value of the aasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAasta() {
        return aasta;
    }

    /**
     * Sets the value of the aasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAasta(BigInteger value) {
        this.aasta = value;
    }

    /**
     * Gets the value of the naitajad property.
     * 
     * @return
     *     possible object is
     *     {@link Naitajad }
     *     
     */
    public Naitajad getNaitajad() {
        return naitajad;
    }

    /**
     * Sets the value of the naitajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Naitajad }
     *     
     */
    public void setNaitajad(Naitajad value) {
        this.naitajad = value;
    }

    /**
     * Gets the value of the fail property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFail() {
        return fail;
    }

    /**
     * Sets the value of the fail property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFail(byte[] value) {
        this.fail = value;
    }

}
