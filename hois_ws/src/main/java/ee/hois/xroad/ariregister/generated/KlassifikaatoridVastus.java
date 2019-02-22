
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for klassifikaatorid_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="klassifikaatorid_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klassifikaator" type="{http://arireg.x-road.eu/producer/}klassifikaator" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "klassifikaatorid_vastus", propOrder = {
    "klassifikaator"
})
public class KlassifikaatoridVastus {

    protected List<Klassifikaator> klassifikaator;

    /**
     * Gets the value of the klassifikaator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the klassifikaator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKlassifikaator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Klassifikaator }
     * 
     * 
     */
    public List<Klassifikaator> getKlassifikaator() {
        if (klassifikaator == null) {
            klassifikaator = new ArrayList<Klassifikaator>();
        }
        return this.klassifikaator;
    }

}
