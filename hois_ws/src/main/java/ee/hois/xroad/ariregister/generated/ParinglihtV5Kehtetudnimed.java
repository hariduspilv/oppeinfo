
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringliht_v5_kehtetudnimed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringliht_v5_kehtetudnimed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="evnimi" type="{http://arireg.x-road.eu/producer/}paringliht_v5_kehtetunimi" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringliht_v5_kehtetudnimed", propOrder = {
    "evnimi"
})
public class ParinglihtV5Kehtetudnimed {

    protected List<ParinglihtV5Kehtetunimi> evnimi;

    /**
     * Gets the value of the evnimi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the evnimi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEvnimi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParinglihtV5Kehtetunimi }
     * 
     * 
     */
    public List<ParinglihtV5Kehtetunimi> getEvnimi() {
        if (evnimi == null) {
            evnimi = new ArrayList<ParinglihtV5Kehtetunimi>();
        }
        return this.evnimi;
    }

}
