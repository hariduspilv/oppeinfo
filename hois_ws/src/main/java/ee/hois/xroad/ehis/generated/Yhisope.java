
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhisope complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhisope"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asutusKood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="asutusNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="valisoppeasutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhisope", propOrder = {
    "asutusKood",
    "asutusNimetus",
    "valisoppeasutus"
})
public class Yhisope {

    @XmlElementRef(name = "asutusKood", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> asutusKood;
    @XmlElement(required = true, nillable = true)
    protected String asutusNimetus;
    @XmlElement(required = true, nillable = true)
    protected String valisoppeasutus;

    /**
     * Gets the value of the asutusKood property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getAsutusKood() {
        return asutusKood;
    }

    /**
     * Sets the value of the asutusKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setAsutusKood(JAXBElement<BigInteger> value) {
        this.asutusKood = value;
    }

    /**
     * Gets the value of the asutusNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsutusNimetus() {
        return asutusNimetus;
    }

    /**
     * Sets the value of the asutusNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsutusNimetus(String value) {
        this.asutusNimetus = value;
    }

    /**
     * Gets the value of the valisoppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValisoppeasutus() {
        return valisoppeasutus;
    }

    /**
     * Sets the value of the valisoppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValisoppeasutus(String value) {
        this.valisoppeasutus = value;
    }

}
