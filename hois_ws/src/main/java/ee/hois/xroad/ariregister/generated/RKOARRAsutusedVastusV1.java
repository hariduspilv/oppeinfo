
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RKOARRAsutusedVastus_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RKOARRAsutusedVastus_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asutus" type="{http://arireg.x-road.eu/producer/}RKOARRAsutused_v1_Asutus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RKOARRAsutusedVastus_v1", propOrder = {
    "asutus"
})
public class RKOARRAsutusedVastusV1 {

    protected List<RKOARRAsutusedV1Asutus> asutus;

    /**
     * Gets the value of the asutus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asutus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsutus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RKOARRAsutusedV1Asutus }
     * 
     * 
     */
    public List<RKOARRAsutusedV1Asutus> getAsutus() {
        if (asutus == null) {
            asutus = new ArrayList<RKOARRAsutusedV1Asutus>();
        }
        return this.asutus;
    }

}
