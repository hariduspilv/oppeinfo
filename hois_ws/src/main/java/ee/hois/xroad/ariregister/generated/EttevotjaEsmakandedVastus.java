
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_esmakanded_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_esmakanded_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotja_esmakanded" type="{http://arireg.x-road.eu/producer/}ettevotja_esmakanne" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_esmakanded_vastus", propOrder = {
    "ettevotjaEsmakanded"
})
public class EttevotjaEsmakandedVastus {

    @XmlElement(name = "ettevotja_esmakanded")
    protected List<EttevotjaEsmakanne> ettevotjaEsmakanded;

    /**
     * Gets the value of the ettevotjaEsmakanded property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ettevotjaEsmakanded property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEttevotjaEsmakanded().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EttevotjaEsmakanne }
     * 
     * 
     */
    public List<EttevotjaEsmakanne> getEttevotjaEsmakanded() {
        if (ettevotjaEsmakanded == null) {
            ettevotjaEsmakanded = new ArrayList<EttevotjaEsmakanne>();
        }
        return this.ettevotjaEsmakanded;
    }

}
