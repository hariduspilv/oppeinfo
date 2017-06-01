
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for khlVOTAArr complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlVOTAArr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VOTA" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlVOTA" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlVOTAArr", propOrder = {
    "vota"
})
public class KhlVOTAArr {

    @XmlElement(name = "VOTA", required = true)
    protected List<KhlVOTA> vota;

    /**
     * Gets the value of the vota property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vota property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVOTA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KhlVOTA }
     * 
     * 
     */
    public List<KhlVOTA> getVOTA() {
        if (vota == null) {
            vota = new ArrayList<KhlVOTA>();
        }
        return this.vota;
    }

}
