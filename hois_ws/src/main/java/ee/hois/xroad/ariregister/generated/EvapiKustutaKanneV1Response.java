
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for evapiKustutaKanne_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="evapiKustutaKanne_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}EVapiKustutaKanneParing_v1"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}EVapiKustutaKanneVastus_v1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evapiKustutaKanne_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EvapiKustutaKanneV1Response {

    @XmlElement(required = true)
    protected EVapiKustutaKanneParingV1 paring;
    @XmlElement(required = true)
    protected EVapiKustutaKanneVastusV1 keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiKustutaKanneParingV1 }
     *     
     */
    public EVapiKustutaKanneParingV1 getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiKustutaKanneParingV1 }
     *     
     */
    public void setParing(EVapiKustutaKanneParingV1 value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiKustutaKanneVastusV1 }
     *     
     */
    public EVapiKustutaKanneVastusV1 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiKustutaKanneVastusV1 }
     *     
     */
    public void setKeha(EVapiKustutaKanneVastusV1 value) {
        this.keha = value;
    }

}
