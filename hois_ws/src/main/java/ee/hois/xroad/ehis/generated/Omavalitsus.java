
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for omavalitsus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="omavalitsus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klfId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="asulad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}asulad" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "omavalitsus", propOrder = {
    "klfId",
    "nimetus",
    "asulad"
})
public class Omavalitsus {

    @XmlElement(required = true)
    protected BigInteger klfId;
    @XmlElement(required = true)
    protected String nimetus;
    protected Asulad asulad;

    /**
     * Gets the value of the klfId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlfId() {
        return klfId;
    }

    /**
     * Sets the value of the klfId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlfId(BigInteger value) {
        this.klfId = value;
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
     * Gets the value of the asulad property.
     * 
     * @return
     *     possible object is
     *     {@link Asulad }
     *     
     */
    public Asulad getAsulad() {
        return asulad;
    }

    /**
     * Sets the value of the asulad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Asulad }
     *     
     */
    public void setAsulad(Asulad value) {
        this.asulad = value;
    }

}
