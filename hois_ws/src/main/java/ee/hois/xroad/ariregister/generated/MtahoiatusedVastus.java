
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtahoiatused_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtahoiatused_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hoiatused" type="{http://arireg.x-road.eu/producer/}mtahoiatused_hoiatused"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtahoiatused_vastus", propOrder = {
    "hoiatused"
})
public class MtahoiatusedVastus {

    @XmlElement(required = true)
    protected MtahoiatusedHoiatused hoiatused;

    /**
     * Gets the value of the hoiatused property.
     * 
     * @return
     *     possible object is
     *     {@link MtahoiatusedHoiatused }
     *     
     */
    public MtahoiatusedHoiatused getHoiatused() {
        return hoiatused;
    }

    /**
     * Sets the value of the hoiatused property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtahoiatusedHoiatused }
     *     
     */
    public void setHoiatused(MtahoiatusedHoiatused value) {
        this.hoiatused = value;
    }

}
