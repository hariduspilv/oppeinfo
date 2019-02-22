
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtatrahvid_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtatrahvid_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="trahvid" type="{http://arireg.x-road.eu/producer/}mtatrahvid_trahvid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtatrahvid_vastus", propOrder = {
    "trahvid"
})
public class MtatrahvidVastus {

    @XmlElement(required = true)
    protected MtatrahvidTrahvid trahvid;

    /**
     * Gets the value of the trahvid property.
     * 
     * @return
     *     possible object is
     *     {@link MtatrahvidTrahvid }
     *     
     */
    public MtatrahvidTrahvid getTrahvid() {
        return trahvid;
    }

    /**
     * Sets the value of the trahvid property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtatrahvidTrahvid }
     *     
     */
    public void setTrahvid(MtatrahvidTrahvid value) {
        this.trahvid = value;
    }

}
