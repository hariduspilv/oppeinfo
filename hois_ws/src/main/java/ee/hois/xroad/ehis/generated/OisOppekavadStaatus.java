
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisOppekavadStaatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisOppekavadStaatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oisOppekava" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekavaStaatusOis" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisOppekavadStaatus", propOrder = {
    "oisOppekava"
})
public class OisOppekavadStaatus {

    @XmlElement(required = true)
    protected List<OppekavaStaatusOis> oisOppekava;

    /**
     * Gets the value of the oisOppekava property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oisOppekava property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOisOppekava().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OppekavaStaatusOis }
     * 
     * 
     */
    public List<OppekavaStaatusOis> getOisOppekava() {
        if (oisOppekava == null) {
            oisOppekava = new ArrayList<OppekavaStaatusOis>();
        }
        return this.oisOppekava;
    }

}
