
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for majandusaastaAruanneteLoetelu_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="majandusaastaAruanneteLoetelu_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}majandusaasta_aruannete_loetelu_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}majandusaasta_aruannete_loetelu_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "majandusaastaAruanneteLoetelu_v1Response", propOrder = {
    "paring",
    "keha"
})
public class MajandusaastaAruanneteLoeteluV1Response {

    @XmlElement(required = true)
    protected MajandusaastaAruanneteLoeteluParing paring;
    @XmlElement(required = true)
    protected MajandusaastaAruanneteLoeteluVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link MajandusaastaAruanneteLoeteluParing }
     *     
     */
    public MajandusaastaAruanneteLoeteluParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link MajandusaastaAruanneteLoeteluParing }
     *     
     */
    public void setParing(MajandusaastaAruanneteLoeteluParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link MajandusaastaAruanneteLoeteluVastus }
     *     
     */
    public MajandusaastaAruanneteLoeteluVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link MajandusaastaAruanneteLoeteluVastus }
     *     
     */
    public void setKeha(MajandusaastaAruanneteLoeteluVastus value) {
        this.keha = value;
    }

}
