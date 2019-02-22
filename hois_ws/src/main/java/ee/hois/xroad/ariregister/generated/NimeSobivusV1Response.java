
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nimeSobivus_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nimeSobivus_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}paringnimesobivus_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}paringnimesobivus_v2_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nimeSobivus_v1Response", propOrder = {
    "paring",
    "keha"
})
public class NimeSobivusV1Response {

    @XmlElement(required = true)
    protected ParingnimesobivusParing paring;
    @XmlElement(required = true)
    protected ParingnimesobivusV2Vastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link ParingnimesobivusParing }
     *     
     */
    public ParingnimesobivusParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingnimesobivusParing }
     *     
     */
    public void setParing(ParingnimesobivusParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link ParingnimesobivusV2Vastus }
     *     
     */
    public ParingnimesobivusV2Vastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingnimesobivusV2Vastus }
     *     
     */
    public void setKeha(ParingnimesobivusV2Vastus value) {
        this.keha = value;
    }

}
