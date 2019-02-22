
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for evapiLooKanne_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="evapiLooKanne_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneParing_v1"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneVastus_v1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evapiLooKanne_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EvapiLooKanneV1Response {

    @XmlElement(required = true)
    protected EVapiLooKanneParingV1 paring;
    @XmlElement(required = true)
    protected EVapiLooKanneVastusV1 keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiLooKanneParingV1 }
     *     
     */
    public EVapiLooKanneParingV1 getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiLooKanneParingV1 }
     *     
     */
    public void setParing(EVapiLooKanneParingV1 value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiLooKanneVastusV1 }
     *     
     */
    public EVapiLooKanneVastusV1 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiLooKanneVastusV1 }
     *     
     */
    public void setKeha(EVapiLooKanneVastusV1 value) {
        this.keha = value;
    }

}
