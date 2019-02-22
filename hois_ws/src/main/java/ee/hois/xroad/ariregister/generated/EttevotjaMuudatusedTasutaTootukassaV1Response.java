
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotjaMuudatusedTasutaTootukassa_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotjaMuudatusedTasutaTootukassa_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatused_tasuta_tootukassa_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatused_tasuta_tootukassa_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotjaMuudatusedTasutaTootukassa_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EttevotjaMuudatusedTasutaTootukassaV1Response {

    @XmlElement(required = true)
    protected EttevotjaMuudatusedTasutaTootukassaParing paring;
    @XmlElement(required = true)
    protected EttevotjaMuudatusedTasutaTootukassaVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaMuudatusedTasutaTootukassaParing }
     *     
     */
    public EttevotjaMuudatusedTasutaTootukassaParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaMuudatusedTasutaTootukassaParing }
     *     
     */
    public void setParing(EttevotjaMuudatusedTasutaTootukassaParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaMuudatusedTasutaTootukassaVastus }
     *     
     */
    public EttevotjaMuudatusedTasutaTootukassaVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaMuudatusedTasutaTootukassaVastus }
     *     
     */
    public void setKeha(EttevotjaMuudatusedTasutaTootukassaVastus value) {
        this.keha = value;
    }

}
