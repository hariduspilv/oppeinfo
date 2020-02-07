
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for iaOppeaine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iaOppeaine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeaineId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klOppeaine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kommentaar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iaOppeaine", propOrder = {
    "nimetus",
    "oppeaineId",
    "klOppeaine",
    "kommentaar"
})
public class IaOppeaine {

    @XmlElement(required = true)
    protected String nimetus;
    protected BigInteger oppeaineId;
    protected String klOppeaine;
    protected String kommentaar;

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
     * Gets the value of the oppeaineId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeaineId() {
        return oppeaineId;
    }

    /**
     * Sets the value of the oppeaineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeaineId(BigInteger value) {
        this.oppeaineId = value;
    }

    /**
     * Gets the value of the klOppeaine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppeaine() {
        return klOppeaine;
    }

    /**
     * Sets the value of the klOppeaine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppeaine(String value) {
        this.klOppeaine = value;
    }

    /**
     * Gets the value of the kommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommentaar() {
        return kommentaar;
    }

    /**
     * Sets the value of the kommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommentaar(String value) {
        this.kommentaar = value;
    }

}
