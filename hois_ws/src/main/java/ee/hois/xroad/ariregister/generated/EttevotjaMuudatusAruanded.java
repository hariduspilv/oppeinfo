
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatus_aruanded complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus_aruanded"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aruanne" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_aruanne" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatus_aruanded", propOrder = {
    "aruanne"
})
public class EttevotjaMuudatusAruanded {

    protected List<EttevotjaMuudatusAruanne> aruanne;

    /**
     * Gets the value of the aruanne property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aruanne property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAruanne().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EttevotjaMuudatusAruanne }
     * 
     * 
     */
    public List<EttevotjaMuudatusAruanne> getAruanne() {
        if (aruanne == null) {
            aruanne = new ArrayList<EttevotjaMuudatusAruanne>();
        }
        return this.aruanne;
    }

}
