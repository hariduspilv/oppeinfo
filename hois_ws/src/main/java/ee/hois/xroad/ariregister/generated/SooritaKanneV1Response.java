
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sooritaKanne_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sooritaKanne_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}SooritaKanne_v4"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}SooritaKanneVastus_v4"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sooritaKanne_v1Response", propOrder = {
    "paring",
    "keha"
})
public class SooritaKanneV1Response {

    @XmlElement(required = true)
    protected SooritaKanneV4 paring;
    @XmlElement(required = true)
    protected SooritaKanneVastusV4 keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link SooritaKanneV4 }
     *     
     */
    public SooritaKanneV4 getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link SooritaKanneV4 }
     *     
     */
    public void setParing(SooritaKanneV4 value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link SooritaKanneVastusV4 }
     *     
     */
    public SooritaKanneVastusV4 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link SooritaKanneVastusV4 }
     *     
     */
    public void setKeha(SooritaKanneVastusV4 value) {
        this.keha = value;
    }

}
