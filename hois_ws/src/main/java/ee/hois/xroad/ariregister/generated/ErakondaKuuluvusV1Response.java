
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for erakondaKuuluvus_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="erakondaKuuluvus_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}erakondakuuluvus_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}erakondakuuluvus_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "erakondaKuuluvus_v1Response", propOrder = {
    "paring",
    "keha"
})
public class ErakondaKuuluvusV1Response {

    @XmlElement(required = true)
    protected ErakondakuuluvusParing paring;
    @XmlElement(required = true)
    protected ErakondakuuluvusVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link ErakondakuuluvusParing }
     *     
     */
    public ErakondakuuluvusParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErakondakuuluvusParing }
     *     
     */
    public void setParing(ErakondakuuluvusParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link ErakondakuuluvusVastus }
     *     
     */
    public ErakondakuuluvusVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErakondakuuluvusVastus }
     *     
     */
    public void setKeha(ErakondakuuluvusVastus value) {
        this.keha = value;
    }

}
