
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esindus_v2Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esindus_v2Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}paringesindus_v6_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}paringesindus_v6_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esindus_v2Response", propOrder = {
    "paring",
    "keha"
})
public class EsindusV2Response {

    @XmlElement(required = true)
    protected ParingesindusV6Paring paring;
    @XmlElement(required = true)
    protected ParingesindusV6Vastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusV6Paring }
     *     
     */
    public ParingesindusV6Paring getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusV6Paring }
     *     
     */
    public void setParing(ParingesindusV6Paring value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusV6Vastus }
     *     
     */
    public ParingesindusV6Vastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusV6Vastus }
     *     
     */
    public void setKeha(ParingesindusV6Vastus value) {
        this.keha = value;
    }

}
