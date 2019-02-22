
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailandmed_v5_esindusoiguse_normaalregulatsioonid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_esindusoiguse_normaalregulatsioonid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="item" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_esindusoiguse_normaalregulatsioon" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_esindusoiguse_normaalregulatsioonid", propOrder = {
    "item"
})
public class DetailandmedV5EsindusoiguseNormaalregulatsioonid {

    protected List<DetailandmedV5EsindusoiguseNormaalregulatsioon> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetailandmedV5EsindusoiguseNormaalregulatsioon }
     * 
     * 
     */
    public List<DetailandmedV5EsindusoiguseNormaalregulatsioon> getItem() {
        if (item == null) {
            item = new ArrayList<DetailandmedV5EsindusoiguseNormaalregulatsioon>();
        }
        return this.item;
    }

}