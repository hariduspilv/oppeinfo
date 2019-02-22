
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatused_tasuline_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatused_tasuline_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotja_muudatused" type="{http://arireg.x-road.eu/producer/}ettevotja_muudatus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="tulemuste_arv" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatused_tasuline_vastus", propOrder = {
    "ettevotjaMuudatused",
    "tulemusteArv"
})
public class EttevotjaMuudatusedTasulineVastus {

    @XmlElement(name = "ettevotja_muudatused")
    protected List<EttevotjaMuudatus> ettevotjaMuudatused;
    @XmlElement(name = "tulemuste_arv")
    protected Integer tulemusteArv;

    /**
     * Gets the value of the ettevotjaMuudatused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ettevotjaMuudatused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEttevotjaMuudatused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EttevotjaMuudatus }
     * 
     * 
     */
    public List<EttevotjaMuudatus> getEttevotjaMuudatused() {
        if (ettevotjaMuudatused == null) {
            ettevotjaMuudatused = new ArrayList<EttevotjaMuudatus>();
        }
        return this.ettevotjaMuudatused;
    }

    /**
     * Gets the value of the tulemusteArv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTulemusteArv() {
        return tulemusteArv;
    }

    /**
     * Sets the value of the tulemusteArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTulemusteArv(Integer value) {
        this.tulemusteArv = value;
    }

}
