
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevottega_seotud_isikud_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevottega_seotud_isikud_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="seosed" type="{http://arireg.x-road.eu/producer/}seos" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevottega_seotud_isikud_vastus", propOrder = {
    "seosed"
})
public class EttevottegaSeotudIsikudVastus {

    protected List<Seos> seosed;

    /**
     * Gets the value of the seosed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the seosed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeosed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Seos }
     * 
     * 
     */
    public List<Seos> getSeosed() {
        if (seosed == null) {
            seosed = new ArrayList<Seos>();
        }
        return this.seosed;
    }

}
