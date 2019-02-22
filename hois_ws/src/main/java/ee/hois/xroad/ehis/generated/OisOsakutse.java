
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisOsakutse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisOsakutse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="osakutseReaId" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisOsakutse", propOrder = {
    "osakutseReaId"
})
public class OisOsakutse {

    @XmlElement(required = true)
    protected List<BigInteger> osakutseReaId;

    /**
     * Gets the value of the osakutseReaId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the osakutseReaId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOsakutseReaId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getOsakutseReaId() {
        if (osakutseReaId == null) {
            osakutseReaId = new ArrayList<BigInteger>();
        }
        return this.osakutseReaId;
    }

}
