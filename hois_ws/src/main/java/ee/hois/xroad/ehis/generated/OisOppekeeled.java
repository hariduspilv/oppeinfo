
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisOppekeeled complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisOppekeeled"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppekeeleKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisOppekeeled", propOrder = {
    "oppekeeleKood"
})
public class OisOppekeeled {

    @XmlElement(required = true)
    protected String oppekeeleKood;

    /**
     * Gets the value of the oppekeeleKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekeeleKood() {
        return oppekeeleKood;
    }

    /**
     * Sets the value of the oppekeeleKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekeeleKood(String value) {
        this.oppekeeleKood = value;
    }

}
