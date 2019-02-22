
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ariregistriToimik_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ariregistriToimik_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}ariregistri_toimik_Request"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}ariregistri_toimik_Response"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ariregistriToimik_v1Response", propOrder = {
    "paring",
    "keha"
})
public class AriregistriToimikV1Response {

    @XmlElement(required = true)
    protected AriregistriToimikRequest paring;
    @XmlElement(required = true)
    protected AriregistriToimikResponse keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link AriregistriToimikRequest }
     *     
     */
    public AriregistriToimikRequest getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link AriregistriToimikRequest }
     *     
     */
    public void setParing(AriregistriToimikRequest value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link AriregistriToimikResponse }
     *     
     */
    public AriregistriToimikResponse getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link AriregistriToimikResponse }
     *     
     */
    public void setKeha(AriregistriToimikResponse value) {
        this.keha = value;
    }

}
