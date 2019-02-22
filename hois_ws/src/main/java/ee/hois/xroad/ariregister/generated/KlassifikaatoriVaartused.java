
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for klassifikaatori_vaartused complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="klassifikaatori_vaartused"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klassifikaatori_vaartus" type="{http://arireg.x-road.eu/producer/}klassifikaatori_vaartus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "klassifikaatori_vaartused", propOrder = {
    "klassifikaatoriVaartus"
})
public class KlassifikaatoriVaartused {

    @XmlElement(name = "klassifikaatori_vaartus")
    protected List<KlassifikaatoriVaartus> klassifikaatoriVaartus;

    /**
     * Gets the value of the klassifikaatoriVaartus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the klassifikaatoriVaartus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKlassifikaatoriVaartus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KlassifikaatoriVaartus }
     * 
     * 
     */
    public List<KlassifikaatoriVaartus> getKlassifikaatoriVaartus() {
        if (klassifikaatoriVaartus == null) {
            klassifikaatoriVaartus = new ArrayList<KlassifikaatoriVaartus>();
        }
        return this.klassifikaatoriVaartus;
    }

}
