
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlVoorkeel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlVoorkeel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klKeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klOskustase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlVoorkeel", propOrder = {
    "klKeel",
    "klOskustase"
})
public class YhlVoorkeel {

    @XmlElement(required = true)
    protected String klKeel;
    @XmlElement(required = true)
    protected String klOskustase;

    /**
     * Gets the value of the klKeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKeel() {
        return klKeel;
    }

    /**
     * Sets the value of the klKeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKeel(String value) {
        this.klKeel = value;
    }

    /**
     * Gets the value of the klOskustase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOskustase() {
        return klOskustase;
    }

    /**
     * Sets the value of the klOskustase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOskustase(String value) {
        this.klOskustase = value;
    }

}
