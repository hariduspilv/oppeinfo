
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rkoarr_asutused_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rkoarr_asutused_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}RKOARRAsutusedParing_v1"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}RKOARRAsutusedVastus_v1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rkoarr_asutused_v1Response", propOrder = {
    "paring",
    "keha"
})
public class RkoarrAsutusedV1Response {

    @XmlElement(required = true)
    protected RKOARRAsutusedParingV1 paring;
    @XmlElement(required = true)
    protected RKOARRAsutusedVastusV1 keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link RKOARRAsutusedParingV1 }
     *     
     */
    public RKOARRAsutusedParingV1 getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link RKOARRAsutusedParingV1 }
     *     
     */
    public void setParing(RKOARRAsutusedParingV1 value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link RKOARRAsutusedVastusV1 }
     *     
     */
    public RKOARRAsutusedVastusV1 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link RKOARRAsutusedVastusV1 }
     *     
     */
    public void setKeha(RKOARRAsutusedVastusV1 value) {
        this.keha = value;
    }

}
