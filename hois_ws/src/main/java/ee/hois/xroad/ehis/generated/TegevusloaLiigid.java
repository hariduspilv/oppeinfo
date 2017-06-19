
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tegevusloaLiigid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tegevusloaLiigid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tegevusloaLiik" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisKlassifikaator" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tegevusloaLiigid", propOrder = {
    "tegevusloaLiik"
})
public class TegevusloaLiigid {

    @XmlElement(nillable = true)
    protected List<EhisKlassifikaator> tegevusloaLiik;

    /**
     * Gets the value of the tegevusloaLiik property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tegevusloaLiik property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTegevusloaLiik().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EhisKlassifikaator }
     * 
     * 
     */
    public List<EhisKlassifikaator> getTegevusloaLiik() {
        if (tegevusloaLiik == null) {
            tegevusloaLiik = new ArrayList<EhisKlassifikaator>();
        }
        return this.tegevusloaLiik;
    }

}