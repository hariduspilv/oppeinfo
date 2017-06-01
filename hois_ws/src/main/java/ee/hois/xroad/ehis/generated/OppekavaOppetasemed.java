
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppekavaOppetasemed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppekavaOppetasemed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppekavaOppetase" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisKlassifikaator" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppekavaOppetasemed", propOrder = {
    "oppekavaOppetase"
})
public class OppekavaOppetasemed {

    @XmlElement(nillable = true)
    protected List<EhisKlassifikaator> oppekavaOppetase;

    /**
     * Gets the value of the oppekavaOppetase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppekavaOppetase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppekavaOppetase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EhisKlassifikaator }
     * 
     * 
     */
    public List<EhisKlassifikaator> getOppekavaOppetase() {
        if (oppekavaOppetase == null) {
            oppekavaOppetase = new ArrayList<EhisKlassifikaator>();
        }
        return this.oppekavaOppetase;
    }

}
