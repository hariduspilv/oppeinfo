
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtaev_seisundid_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtaev_seisundid_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="seisundid" type="{http://arireg.x-road.eu/producer/}mtaev_seisundid_seisundid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtaev_seisundid_vastus", propOrder = {
    "seisundid"
})
public class MtaevSeisundidVastus {

    @XmlElement(required = true)
    protected MtaevSeisundidSeisundid seisundid;

    /**
     * Gets the value of the seisundid property.
     * 
     * @return
     *     possible object is
     *     {@link MtaevSeisundidSeisundid }
     *     
     */
    public MtaevSeisundidSeisundid getSeisundid() {
        return seisundid;
    }

    /**
     * Sets the value of the seisundid property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtaevSeisundidSeisundid }
     *     
     */
    public void setSeisundid(MtaevSeisundidSeisundid value) {
        this.seisundid = value;
    }

}
