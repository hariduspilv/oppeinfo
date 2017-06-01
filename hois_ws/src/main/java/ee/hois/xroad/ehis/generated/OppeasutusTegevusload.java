
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppeasutusTegevusload complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppeasutusTegevusload"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="regNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegevusload" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tegevusload" minOccurs="0"/&gt;
 *         &lt;element name="tegevusnaitajad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tegevusnaitajad" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppeasutusTegevusload", propOrder = {
    "id",
    "regNr",
    "nimetus",
    "tegevusload",
    "tegevusnaitajad"
})
public class OppeasutusTegevusload {

    @XmlElement(required = true)
    protected BigInteger id;
    @XmlElement(required = true)
    protected String regNr;
    @XmlElementRef(name = "nimetus", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nimetus;
    protected Tegevusload tegevusload;
    protected Tegevusnaitajad tegevusnaitajad;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the regNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegNr() {
        return regNr;
    }

    /**
     * Sets the value of the regNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegNr(String value) {
        this.regNr = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNimetus(JAXBElement<String> value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the tegevusload property.
     * 
     * @return
     *     possible object is
     *     {@link Tegevusload }
     *     
     */
    public Tegevusload getTegevusload() {
        return tegevusload;
    }

    /**
     * Sets the value of the tegevusload property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tegevusload }
     *     
     */
    public void setTegevusload(Tegevusload value) {
        this.tegevusload = value;
    }

    /**
     * Gets the value of the tegevusnaitajad property.
     * 
     * @return
     *     possible object is
     *     {@link Tegevusnaitajad }
     *     
     */
    public Tegevusnaitajad getTegevusnaitajad() {
        return tegevusnaitajad;
    }

    /**
     * Sets the value of the tegevusnaitajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tegevusnaitajad }
     *     
     */
    public void setTegevusnaitajad(Tegevusnaitajad value) {
        this.tegevusnaitajad = value;
    }

}
