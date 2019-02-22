
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtaotsused_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtaotsused_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="otsused" type="{http://arireg.x-road.eu/producer/}mtaotsused_otsused"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtaotsused_vastus", propOrder = {
    "otsused"
})
public class MtaotsusedVastus {

    @XmlElement(required = true)
    protected MtaotsusedOtsused otsused;

    /**
     * Gets the value of the otsused property.
     * 
     * @return
     *     possible object is
     *     {@link MtaotsusedOtsused }
     *     
     */
    public MtaotsusedOtsused getOtsused() {
        return otsused;
    }

    /**
     * Sets the value of the otsused property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtaotsusedOtsused }
     *     
     */
    public void setOtsused(MtaotsusedOtsused value) {
        this.otsused = value;
    }

}
