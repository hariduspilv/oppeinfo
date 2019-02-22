
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for evportaalDigiteeriToimik_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="evportaalDigiteeriToimik_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}DigiteeriToimikParing_v1"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}DigiteeriToimikVastus_v1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evportaalDigiteeriToimik_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EvportaalDigiteeriToimikV1Response {

    @XmlElement(required = true)
    protected DigiteeriToimikParingV1 paring;
    @XmlElement(required = true)
    protected DigiteeriToimikVastusV1 keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link DigiteeriToimikParingV1 }
     *     
     */
    public DigiteeriToimikParingV1 getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigiteeriToimikParingV1 }
     *     
     */
    public void setParing(DigiteeriToimikParingV1 value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link DigiteeriToimikVastusV1 }
     *     
     */
    public DigiteeriToimikVastusV1 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigiteeriToimikVastusV1 }
     *     
     */
    public void setKeha(DigiteeriToimikVastusV1 value) {
        this.keha = value;
    }

}