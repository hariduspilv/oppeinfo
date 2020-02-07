
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eisAmetikohadV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eisAmetikohadV2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ametikoht" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eisAmetikohtV2" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eisAmetikohadV2", propOrder = {
    "ametikoht"
})
public class EisAmetikohadV2 {

    protected List<EisAmetikohtV2> ametikoht;

    /**
     * Gets the value of the ametikoht property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ametikoht property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmetikoht().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EisAmetikohtV2 }
     * 
     * 
     */
    public List<EisAmetikohtV2> getAmetikoht() {
        if (ametikoht == null) {
            ametikoht = new ArrayList<EisAmetikohtV2>();
        }
        return this.ametikoht;
    }

}
