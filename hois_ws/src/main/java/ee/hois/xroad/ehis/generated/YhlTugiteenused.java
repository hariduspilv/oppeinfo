
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlTugiteenused complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlTugiteenused"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tugiteenus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlTugiteenus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlTugiteenused", propOrder = {
    "tugiteenus"
})
public class YhlTugiteenused {

    protected List<YhlTugiteenus> tugiteenus;

    /**
     * Gets the value of the tugiteenus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tugiteenus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTugiteenus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link YhlTugiteenus }
     * 
     * 
     */
    public List<YhlTugiteenus> getTugiteenus() {
        if (tugiteenus == null) {
            tugiteenus = new ArrayList<YhlTugiteenus>();
        }
        return this.tugiteenus;
    }

}