
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppeasutuseOmandivormid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppeasutuseOmandivormid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseOmandivorm" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisKlassifikaator" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppeasutuseOmandivormid", propOrder = {
    "oppeasutuseOmandivorm"
})
public class OppeasutuseOmandivormid {

    @XmlElement(nillable = true)
    protected List<EhisKlassifikaator> oppeasutuseOmandivorm;

    /**
     * Gets the value of the oppeasutuseOmandivorm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppeasutuseOmandivorm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppeasutuseOmandivorm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EhisKlassifikaator }
     * 
     * 
     */
    public List<EhisKlassifikaator> getOppeasutuseOmandivorm() {
        if (oppeasutuseOmandivorm == null) {
            oppeasutuseOmandivorm = new ArrayList<EhisKlassifikaator>();
        }
        return this.oppeasutuseOmandivorm;
    }

}
