
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for majandusaasta_aruanded_rida complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="majandusaasta_aruanded_rida"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rea_nr" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="rea_nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="majandusaasta_aruanded_veerud" type="{http://arireg.x-road.eu/producer/}majandusaasta_aruanded_rea_veerg" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "majandusaasta_aruanded_rida", propOrder = {
    "reaNr",
    "reaNimetus",
    "majandusaastaAruandedVeerud"
})
public class MajandusaastaAruandedRida {

    @XmlElement(name = "rea_nr")
    protected int reaNr;
    @XmlElement(name = "rea_nimetus", required = true)
    protected String reaNimetus;
    @XmlElement(name = "majandusaasta_aruanded_veerud")
    protected List<MajandusaastaAruandedReaVeerg> majandusaastaAruandedVeerud;

    /**
     * Gets the value of the reaNr property.
     * 
     */
    public int getReaNr() {
        return reaNr;
    }

    /**
     * Sets the value of the reaNr property.
     * 
     */
    public void setReaNr(int value) {
        this.reaNr = value;
    }

    /**
     * Gets the value of the reaNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReaNimetus() {
        return reaNimetus;
    }

    /**
     * Sets the value of the reaNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReaNimetus(String value) {
        this.reaNimetus = value;
    }

    /**
     * Gets the value of the majandusaastaAruandedVeerud property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the majandusaastaAruandedVeerud property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMajandusaastaAruandedVeerud().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MajandusaastaAruandedReaVeerg }
     * 
     * 
     */
    public List<MajandusaastaAruandedReaVeerg> getMajandusaastaAruandedVeerud() {
        if (majandusaastaAruandedVeerud == null) {
            majandusaastaAruandedVeerud = new ArrayList<MajandusaastaAruandedReaVeerg>();
        }
        return this.majandusaastaAruandedVeerud;
    }

}
