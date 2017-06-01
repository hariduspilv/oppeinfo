
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tkTegevusload complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tkTegevusload"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tegevusluba" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkTegevusluba" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tkTegevusload", propOrder = {
    "tegevusluba"
})
public class TkTegevusload {

    protected List<TkTegevusluba> tegevusluba;

    /**
     * Gets the value of the tegevusluba property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tegevusluba property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTegevusluba().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TkTegevusluba }
     * 
     * 
     */
    public List<TkTegevusluba> getTegevusluba() {
        if (tegevusluba == null) {
            tegevusluba = new ArrayList<TkTegevusluba>();
        }
        return this.tegevusluba;
    }

}
