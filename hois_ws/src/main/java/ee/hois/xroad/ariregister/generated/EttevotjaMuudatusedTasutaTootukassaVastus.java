
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatused_tasuta_tootukassa_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatused_tasuta_tootukassa_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kanded" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_tootukassa_kanded" minOccurs="0"/&gt;
 *         &lt;element name="kanded_arv" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatused_tasuta_tootukassa_vastus", propOrder = {
    "kanded",
    "kandedArv"
})
public class EttevotjaMuudatusedTasutaTootukassaVastus {

    protected EttevotjaMuudatusTootukassaKanded kanded;
    @XmlElement(name = "kanded_arv")
    protected Integer kandedArv;

    /**
     * Gets the value of the kanded property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaMuudatusTootukassaKanded }
     *     
     */
    public EttevotjaMuudatusTootukassaKanded getKanded() {
        return kanded;
    }

    /**
     * Sets the value of the kanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaMuudatusTootukassaKanded }
     *     
     */
    public void setKanded(EttevotjaMuudatusTootukassaKanded value) {
        this.kanded = value;
    }

    /**
     * Gets the value of the kandedArv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandedArv() {
        return kandedArv;
    }

    /**
     * Sets the value of the kandedArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandedArv(Integer value) {
        this.kandedArv = value;
    }

}
