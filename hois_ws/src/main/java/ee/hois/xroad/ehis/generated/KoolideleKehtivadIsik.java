
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for koolideleKehtivadIsik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="koolideleKehtivadIsik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}koolideleIsik"/&gt;
 *         &lt;element name="oppimine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}koolOppimine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "koolideleKehtivadIsik", propOrder = {
    "isikuandmed",
    "oppimine"
})
public class KoolideleKehtivadIsik {

    @XmlElement(required = true)
    protected KoolideleIsik isikuandmed;
    protected List<KoolOppimine> oppimine;

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link KoolideleIsik }
     *     
     */
    public KoolideleIsik getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link KoolideleIsik }
     *     
     */
    public void setIsikuandmed(KoolideleIsik value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the oppimine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppimine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppimine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KoolOppimine }
     * 
     * 
     */
    public List<KoolOppimine> getOppimine() {
        if (oppimine == null) {
            oppimine = new ArrayList<KoolOppimine>();
        }
        return this.oppimine;
    }

}
