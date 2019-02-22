
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for evapiRiigiloivuViitenumber_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="evapiRiigiloivuViitenumber_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}EVapiRiigiloivuViitenumberParing_v1"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}EVapiRiigiloivuViitenumberVastus_v1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evapiRiigiloivuViitenumber_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EvapiRiigiloivuViitenumberV1Response {

    @XmlElement(required = true)
    protected EVapiRiigiloivuViitenumberParingV1 paring;
    @XmlElement(required = true)
    protected EVapiRiigiloivuViitenumberVastusV1 keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiRiigiloivuViitenumberParingV1 }
     *     
     */
    public EVapiRiigiloivuViitenumberParingV1 getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiRiigiloivuViitenumberParingV1 }
     *     
     */
    public void setParing(EVapiRiigiloivuViitenumberParingV1 value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiRiigiloivuViitenumberVastusV1 }
     *     
     */
    public EVapiRiigiloivuViitenumberVastusV1 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiRiigiloivuViitenumberVastusV1 }
     *     
     */
    public void setKeha(EVapiRiigiloivuViitenumberVastusV1 value) {
        this.keha = value;
    }

}
