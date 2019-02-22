
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtaBilSisuFail_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtaBilSisuFail_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}mtabilsisuv2_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}mtabilsisufv2_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtaBilSisuFail_v1Response", propOrder = {
    "paring",
    "keha"
})
public class MtaBilSisuFailV1Response {

    @XmlElement(required = true)
    protected Mtabilsisuv2Paring paring;
    @XmlElement(required = true)
    protected Mtabilsisufv2Vastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link Mtabilsisuv2Paring }
     *     
     */
    public Mtabilsisuv2Paring getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mtabilsisuv2Paring }
     *     
     */
    public void setParing(Mtabilsisuv2Paring value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link Mtabilsisufv2Vastus }
     *     
     */
    public Mtabilsisufv2Vastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mtabilsisufv2Vastus }
     *     
     */
    public void setKeha(Mtabilsisufv2Vastus value) {
        this.keha = value;
    }

}
