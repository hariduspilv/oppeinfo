
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for debugAdapter_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="debugAdapter_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}debugAdapter_v1_IN"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}debugAdapter_v1_OUT"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "debugAdapter_v1Response", propOrder = {
    "paring",
    "keha"
})
public class DebugAdapterV1Response {

    @XmlElement(required = true)
    protected DebugAdapterV1IN paring;
    @XmlElement(required = true)
    protected DebugAdapterV1OUT keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link DebugAdapterV1IN }
     *     
     */
    public DebugAdapterV1IN getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebugAdapterV1IN }
     *     
     */
    public void setParing(DebugAdapterV1IN value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link DebugAdapterV1OUT }
     *     
     */
    public DebugAdapterV1OUT getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebugAdapterV1OUT }
     *     
     */
    public void setKeha(DebugAdapterV1OUT value) {
        this.keha = value;
    }

}
