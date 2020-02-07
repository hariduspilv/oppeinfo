
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlAbistajad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlAbistajad"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="abistaja" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlAbistaja" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlAbistajad", propOrder = {
    "abistaja"
})
public class YhlAbistajad {

    protected List<YhlAbistaja> abistaja;

    /**
     * Gets the value of the abistaja property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abistaja property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbistaja().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link YhlAbistaja }
     * 
     * 
     */
    public List<YhlAbistaja> getAbistaja() {
        if (abistaja == null) {
            abistaja = new ArrayList<YhlAbistaja>();
        }
        return this.abistaja;
    }

}
