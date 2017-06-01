
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esrOppeasutusedList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esrOppeasutusedList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}esrOppeasutus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esrOppeasutusedList", propOrder = {
    "oppeasutus"
})
public class EsrOppeasutusedList {

    protected List<EsrOppeasutus> oppeasutus;

    /**
     * Gets the value of the oppeasutus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppeasutus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppeasutus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsrOppeasutus }
     * 
     * 
     */
    public List<EsrOppeasutus> getOppeasutus() {
        if (oppeasutus == null) {
            oppeasutus = new ArrayList<EsrOppeasutus>();
        }
        return this.oppeasutus;
    }

}
