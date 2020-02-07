
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for huviKlOppekeeled complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="huviKlOppekeeled"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klOppekeel" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisRiikType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "huviKlOppekeeled", propOrder = {
    "klOppekeel"
})
public class HuviKlOppekeeled {

    @XmlElement(required = true)
    protected List<String> klOppekeel;

    /**
     * Gets the value of the klOppekeel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the klOppekeel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKlOppekeel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKlOppekeel() {
        if (klOppekeel == null) {
            klOppekeel = new ArrayList<String>();
        }
        return this.klOppekeel;
    }

}
