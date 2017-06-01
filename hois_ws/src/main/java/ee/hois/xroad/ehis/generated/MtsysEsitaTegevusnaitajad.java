
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="operatsioon" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aruandeId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="menetlusKommentaar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "operatsioon",
    "aruandeId",
    "menetlusKommentaar"
})
@XmlRootElement(name = "mtsysEsitaTegevusnaitajad")
public class MtsysEsitaTegevusnaitajad {

    @XmlElement(required = true)
    protected String operatsioon;
    @XmlElement(required = true)
    protected BigInteger aruandeId;
    @XmlElementRef(name = "menetlusKommentaar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> menetlusKommentaar;

    /**
     * Gets the value of the operatsioon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatsioon() {
        return operatsioon;
    }

    /**
     * Sets the value of the operatsioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatsioon(String value) {
        this.operatsioon = value;
    }

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
     * Gets the value of the menetlusKommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMenetlusKommentaar() {
        return menetlusKommentaar;
    }

    /**
     * Sets the value of the menetlusKommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMenetlusKommentaar(JAXBElement<String> value) {
        this.menetlusKommentaar = value;
    }

}
