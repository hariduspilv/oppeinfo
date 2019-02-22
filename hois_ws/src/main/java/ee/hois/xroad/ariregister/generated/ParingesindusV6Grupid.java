
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringesindus_v6_grupid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringesindus_v6_grupid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="grupp" type="{http://arireg.x-road.eu/producer/}paringesindus_v6_grupp" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringesindus_v6_grupid", propOrder = {
    "grupp"
})
public class ParingesindusV6Grupid {

    protected List<ParingesindusV6Grupp> grupp;

    /**
     * Gets the value of the grupp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grupp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrupp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParingesindusV6Grupp }
     * 
     * 
     */
    public List<ParingesindusV6Grupp> getGrupp() {
        if (grupp == null) {
            grupp = new ArrayList<ParingesindusV6Grupp>();
        }
        return this.grupp;
    }

}
