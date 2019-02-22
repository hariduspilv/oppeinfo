
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for menetlusinfo_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="menetlusinfo_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}menetlusinfo_v3_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}menetlusinfo_v3_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menetlusinfo_v1Response", propOrder = {
    "paring",
    "keha"
})
public class MenetlusinfoV1Response {

    @XmlElement(required = true)
    protected MenetlusinfoV3Paring paring;
    @XmlElement(required = true)
    protected MenetlusinfoV3Vastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link MenetlusinfoV3Paring }
     *     
     */
    public MenetlusinfoV3Paring getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link MenetlusinfoV3Paring }
     *     
     */
    public void setParing(MenetlusinfoV3Paring value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link MenetlusinfoV3Vastus }
     *     
     */
    public MenetlusinfoV3Vastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link MenetlusinfoV3Vastus }
     *     
     */
    public void setKeha(MenetlusinfoV3Vastus value) {
        this.keha = value;
    }

}
