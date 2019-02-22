
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatus_maarused complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus_maarused"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="maarus" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus_maarus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatus_maarused", propOrder = {
    "maarus"
})
public class EttevotjaMuudatusMaarused {

    protected List<EttevotjaMuudatusMaarus> maarus;

    /**
     * Gets the value of the maarus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maarus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaarus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EttevotjaMuudatusMaarus }
     * 
     * 
     */
    public List<EttevotjaMuudatusMaarus> getMaarus() {
        if (maarus == null) {
            maarus = new ArrayList<EttevotjaMuudatusMaarus>();
        }
        return this.maarus;
    }

}
