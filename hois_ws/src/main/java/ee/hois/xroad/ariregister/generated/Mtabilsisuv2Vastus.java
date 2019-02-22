
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtabilsisuv2_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtabilsisuv2_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aruanded" type="{http://arireg.x-road.eu/producer/}mtabilsisuv2_aruanded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtabilsisuv2_vastus", propOrder = {
    "aruanded"
})
public class Mtabilsisuv2Vastus {

    @XmlElement(required = true)
    protected Mtabilsisuv2Aruanded aruanded;

    /**
     * Gets the value of the aruanded property.
     * 
     * @return
     *     possible object is
     *     {@link Mtabilsisuv2Aruanded }
     *     
     */
    public Mtabilsisuv2Aruanded getAruanded() {
        return aruanded;
    }

    /**
     * Sets the value of the aruanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mtabilsisuv2Aruanded }
     *     
     */
    public void setAruanded(Mtabilsisuv2Aruanded value) {
        this.aruanded = value;
    }

}
