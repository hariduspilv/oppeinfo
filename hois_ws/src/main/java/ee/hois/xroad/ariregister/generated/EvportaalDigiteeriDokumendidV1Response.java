
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for evportaalDigiteeriDokumendid_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="evportaalDigiteeriDokumendid_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}DigiteeriDokumendidParing_v1"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}DigiteeriDokumendidVastus_v1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evportaalDigiteeriDokumendid_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EvportaalDigiteeriDokumendidV1Response {

    @XmlElement(required = true)
    protected DigiteeriDokumendidParingV1 paring;
    @XmlElement(required = true)
    protected DigiteeriDokumendidVastusV1 keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link DigiteeriDokumendidParingV1 }
     *     
     */
    public DigiteeriDokumendidParingV1 getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigiteeriDokumendidParingV1 }
     *     
     */
    public void setParing(DigiteeriDokumendidParingV1 value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link DigiteeriDokumendidVastusV1 }
     *     
     */
    public DigiteeriDokumendidVastusV1 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigiteeriDokumendidVastusV1 }
     *     
     */
    public void setKeha(DigiteeriDokumendidVastusV1 value) {
        this.keha = value;
    }

}
