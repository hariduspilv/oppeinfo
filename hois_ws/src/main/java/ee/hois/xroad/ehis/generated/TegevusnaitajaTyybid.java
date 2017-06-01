
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tegevusnaitajaTyybid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tegevusnaitajaTyybid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tegevusnaitajaTyyp" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisKlassifikaator" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tegevusnaitajaTyybid", propOrder = {
    "tegevusnaitajaTyyp"
})
public class TegevusnaitajaTyybid {

    @XmlElement(nillable = true)
    protected List<EhisKlassifikaator> tegevusnaitajaTyyp;

    /**
     * Gets the value of the tegevusnaitajaTyyp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tegevusnaitajaTyyp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTegevusnaitajaTyyp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EhisKlassifikaator }
     * 
     * 
     */
    public List<EhisKlassifikaator> getTegevusnaitajaTyyp() {
        if (tegevusnaitajaTyyp == null) {
            tegevusnaitajaTyyp = new ArrayList<EhisKlassifikaator>();
        }
        return this.tegevusnaitajaTyyp;
    }

}
