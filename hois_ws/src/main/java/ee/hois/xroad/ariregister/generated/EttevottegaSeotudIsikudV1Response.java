
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevottegaSeotudIsikud_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevottegaSeotudIsikud_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}ettevottega_seotud_isikud_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}ettevottega_seotud_isikud_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevottegaSeotudIsikud_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EttevottegaSeotudIsikudV1Response {

    @XmlElement(required = true)
    protected EttevottegaSeotudIsikudParing paring;
    @XmlElement(required = true)
    protected EttevottegaSeotudIsikudVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link EttevottegaSeotudIsikudParing }
     *     
     */
    public EttevottegaSeotudIsikudParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevottegaSeotudIsikudParing }
     *     
     */
    public void setParing(EttevottegaSeotudIsikudParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EttevottegaSeotudIsikudVastus }
     *     
     */
    public EttevottegaSeotudIsikudVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevottegaSeotudIsikudVastus }
     *     
     */
    public void setKeha(EttevottegaSeotudIsikudVastus value) {
        this.keha = value;
    }

}
