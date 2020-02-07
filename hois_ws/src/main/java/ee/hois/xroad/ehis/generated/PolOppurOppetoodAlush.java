
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for polOppurOppetoodAlush complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="polOppurOppetoodAlush"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="alush" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alushOppetoo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "polOppurOppetoodAlush", propOrder = {
    "alush"
})
public class PolOppurOppetoodAlush {

    protected List<AlushOppetoo> alush;

    /**
     * Gets the value of the alush property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alush property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlush().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlushOppetoo }
     * 
     * 
     */
    public List<AlushOppetoo> getAlush() {
        if (alush == null) {
            alush = new ArrayList<AlushOppetoo>();
        }
        return this.alush;
    }

}
