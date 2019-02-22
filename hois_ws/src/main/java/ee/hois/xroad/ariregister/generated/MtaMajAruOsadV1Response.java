
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtaMajAruOsad_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtaMajAruOsad_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}mtamaruosad_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}mtamaruosad_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtaMajAruOsad_v1Response", propOrder = {
    "paring",
    "keha"
})
public class MtaMajAruOsadV1Response {

    @XmlElement(required = true)
    protected MtamaruosadParing paring;
    @XmlElement(required = true)
    protected MtamaruosadVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link MtamaruosadParing }
     *     
     */
    public MtamaruosadParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtamaruosadParing }
     *     
     */
    public void setParing(MtamaruosadParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link MtamaruosadVastus }
     *     
     */
    public MtamaruosadVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtamaruosadVastus }
     *     
     */
    public void setKeha(MtamaruosadVastus value) {
        this.keha = value;
    }

}
