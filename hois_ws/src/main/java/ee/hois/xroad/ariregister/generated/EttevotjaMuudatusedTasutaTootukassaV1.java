
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotjaMuudatusedTasutaTootukassa_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotjaMuudatusedTasutaTootukassa_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatused_tasuta_tootukassa_paring"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotjaMuudatusedTasutaTootukassa_v1", propOrder = {
    "keha"
})
public class EttevotjaMuudatusedTasutaTootukassaV1 {

    @XmlElement(required = true)
    protected EttevotjaMuudatusedTasutaTootukassaParing keha;

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaMuudatusedTasutaTootukassaParing }
     *     
     */
    public EttevotjaMuudatusedTasutaTootukassaParing getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaMuudatusedTasutaTootukassaParing }
     *     
     */
    public void setKeha(EttevotjaMuudatusedTasutaTootukassaParing value) {
        this.keha = value;
    }

}
