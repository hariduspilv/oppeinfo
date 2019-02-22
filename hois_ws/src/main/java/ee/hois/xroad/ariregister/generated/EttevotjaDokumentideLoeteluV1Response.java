
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotjaDokumentideLoetelu_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotjaDokumentideLoetelu_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}ettevotja_dokumentide_loetelu_paring_v2"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}ettevotja_dokumentide_loetelu_vastus_v2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotjaDokumentideLoetelu_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EttevotjaDokumentideLoeteluV1Response {

    @XmlElement(required = true)
    protected EttevotjaDokumentideLoeteluParingV2 paring;
    @XmlElement(required = true)
    protected EttevotjaDokumentideLoeteluVastusV2 keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaDokumentideLoeteluParingV2 }
     *     
     */
    public EttevotjaDokumentideLoeteluParingV2 getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaDokumentideLoeteluParingV2 }
     *     
     */
    public void setParing(EttevotjaDokumentideLoeteluParingV2 value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaDokumentideLoeteluVastusV2 }
     *     
     */
    public EttevotjaDokumentideLoeteluVastusV2 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaDokumentideLoeteluVastusV2 }
     *     
     */
    public void setKeha(EttevotjaDokumentideLoeteluVastusV2 value) {
        this.keha = value;
    }

}
