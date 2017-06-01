
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pedagoogTasemekoolitusId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogTasemekoolitusId"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tasemeOppeas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klKvalDok" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klHaridustase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogTasemekoolitusId", propOrder = {
    "tasemeOppeas",
    "klKvalDok",
    "klHaridustase"
})
public class PedagoogTasemekoolitusId {

    @XmlElement(required = true)
    protected String tasemeOppeas;
    protected String klKvalDok;
    protected String klHaridustase;

    /**
     * Gets the value of the tasemeOppeas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTasemeOppeas() {
        return tasemeOppeas;
    }

    /**
     * Sets the value of the tasemeOppeas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTasemeOppeas(String value) {
        this.tasemeOppeas = value;
    }

    /**
     * Gets the value of the klKvalDok property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKvalDok() {
        return klKvalDok;
    }

    /**
     * Sets the value of the klKvalDok property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKvalDok(String value) {
        this.klKvalDok = value;
    }

    /**
     * Gets the value of the klHaridustase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlHaridustase() {
        return klHaridustase;
    }

    /**
     * Sets the value of the klHaridustase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlHaridustase(String value) {
        this.klHaridustase = value;
    }

}
