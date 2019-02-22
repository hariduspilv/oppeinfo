
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatus_tootukassa_kanded complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus_tootukassa_kanded"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kanne" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_tootukassa_kanne" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatus_tootukassa_kanded", propOrder = {
    "kanne"
})
public class EttevotjaMuudatusTootukassaKanded {

    protected List<EttevotjaMuudatusTootukassaKanne> kanne;

    /**
     * Gets the value of the kanne property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kanne property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKanne().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EttevotjaMuudatusTootukassaKanne }
     * 
     * 
     */
    public List<EttevotjaMuudatusTootukassaKanne> getKanne() {
        if (kanne == null) {
            kanne = new ArrayList<EttevotjaMuudatusTootukassaKanne>();
        }
        return this.kanne;
    }

}