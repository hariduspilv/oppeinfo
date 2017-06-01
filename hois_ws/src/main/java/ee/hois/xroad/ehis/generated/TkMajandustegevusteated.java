
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tkMajandustegevusteated complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tkMajandustegevusteated"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="majandustegevusteade" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkMajandustegevusteade" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tkMajandustegevusteated", propOrder = {
    "majandustegevusteade"
})
public class TkMajandustegevusteated {

    protected List<TkMajandustegevusteade> majandustegevusteade;

    /**
     * Gets the value of the majandustegevusteade property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the majandustegevusteade property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMajandustegevusteade().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TkMajandustegevusteade }
     * 
     * 
     */
    public List<TkMajandustegevusteade> getMajandustegevusteade() {
        if (majandustegevusteade == null) {
            majandustegevusteade = new ArrayList<TkMajandustegevusteade>();
        }
        return this.majandustegevusteade;
    }

}
