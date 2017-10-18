
package ee.hois.xroad.ehis.generated;

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
 *         &lt;element name="regNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kasutajaIk" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisOppekavadStaatus"/&gt;
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
    "regNumber",
    "kasutajaIk",
    "oppekavad"
})
@XmlRootElement(name = "oisOppekavaStaatus")
public class OisOppekavaStaatus {

    @XmlElement(required = true)
    protected String regNumber;
    @XmlElement(required = true)
    protected String kasutajaIk;
    @XmlElement(required = true)
    protected OisOppekavadStaatus oppekavad;

    /**
     * Gets the value of the regNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegNumber() {
        return regNumber;
    }

    /**
     * Sets the value of the regNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegNumber(String value) {
        this.regNumber = value;
    }

    /**
     * Gets the value of the kasutajaIk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKasutajaIk() {
        return kasutajaIk;
    }

    /**
     * Sets the value of the kasutajaIk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKasutajaIk(String value) {
        this.kasutajaIk = value;
    }

    /**
     * Gets the value of the oppekavad property.
     * 
     * @return
     *     possible object is
     *     {@link OisOppekavadStaatus }
     *     
     */
    public OisOppekavadStaatus getOppekavad() {
        return oppekavad;
    }

    /**
     * Sets the value of the oppekavad property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisOppekavadStaatus }
     *     
     */
    public void setOppekavad(OisOppekavadStaatus value) {
        this.oppekavad = value;
    }

}
