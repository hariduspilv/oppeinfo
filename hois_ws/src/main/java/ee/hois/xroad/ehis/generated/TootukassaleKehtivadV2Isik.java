
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tootukassaleKehtivadV2Isik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tootukassaleKehtivadV2Isik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="oppimine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkV2Oppimine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="viga" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkViga" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tootukassaleKehtivadV2Isik", propOrder = {
    "isikukood",
    "oppimine",
    "viga"
})
public class TootukassaleKehtivadV2Isik {

    @XmlElement(required = true)
    protected String isikukood;
    protected List<TkV2Oppimine> oppimine;
    protected TkViga viga;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
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
     * {@link TkV2Oppimine }
     * 
     * 
     */
    public List<TkV2Oppimine> getOppimine() {
        if (oppimine == null) {
            oppimine = new ArrayList<TkV2Oppimine>();
        }
        return this.oppimine;
    }

    /**
     * Gets the value of the viga property.
     * 
     * @return
     *     possible object is
     *     {@link TkViga }
     *     
     */
    public TkViga getViga() {
        return viga;
    }

    /**
     * Sets the value of the viga property.
     * 
     * @param value
     *     allowed object is
     *     {@link TkViga }
     *     
     */
    public void setViga(TkViga value) {
        this.viga = value;
    }

}
