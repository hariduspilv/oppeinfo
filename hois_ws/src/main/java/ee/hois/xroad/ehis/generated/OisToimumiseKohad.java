
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisToimumiseKohad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisToimumiseKohad"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="toimumiseKohtEHAK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisToimumiseKohad", propOrder = {
    "toimumiseKohtEHAK"
})
public class OisToimumiseKohad {

    @XmlElement(required = true)
    protected String toimumiseKohtEHAK;

    /**
     * Gets the value of the toimumiseKohtEHAK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToimumiseKohtEHAK() {
        return toimumiseKohtEHAK;
    }

    /**
     * Sets the value of the toimumiseKohtEHAK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToimumiseKohtEHAK(String value) {
        this.toimumiseKohtEHAK = value;
    }

}
