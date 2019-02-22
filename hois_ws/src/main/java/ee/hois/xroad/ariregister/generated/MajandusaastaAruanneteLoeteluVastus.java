
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for majandusaasta_aruannete_loetelu_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="majandusaasta_aruannete_loetelu_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="majandusaasta_aruanded" type="{http://arireg.x-road.eu/producer/}majandusaasta_aruanne" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "majandusaasta_aruannete_loetelu_vastus", propOrder = {
    "majandusaastaAruanded"
})
public class MajandusaastaAruanneteLoeteluVastus {

    @XmlElement(name = "majandusaasta_aruanded")
    protected List<MajandusaastaAruanne> majandusaastaAruanded;

    /**
     * Gets the value of the majandusaastaAruanded property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the majandusaastaAruanded property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMajandusaastaAruanded().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MajandusaastaAruanne }
     * 
     * 
     */
    public List<MajandusaastaAruanne> getMajandusaastaAruanded() {
        if (majandusaastaAruanded == null) {
            majandusaastaAruanded = new ArrayList<MajandusaastaAruanne>();
        }
        return this.majandusaastaAruanded;
    }

}
