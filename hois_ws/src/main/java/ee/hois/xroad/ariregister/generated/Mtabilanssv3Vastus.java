
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtabilanssv3_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtabilanssv3_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bilansid" type="{http://arireg.x-road.eu/producer/}mtabilanssv3_bilansid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtabilanssv3_vastus", propOrder = {
    "bilansid"
})
public class Mtabilanssv3Vastus {

    @XmlElement(required = true)
    protected Mtabilanssv3Bilansid bilansid;

    /**
     * Gets the value of the bilansid property.
     * 
     * @return
     *     possible object is
     *     {@link Mtabilanssv3Bilansid }
     *     
     */
    public Mtabilanssv3Bilansid getBilansid() {
        return bilansid;
    }

    /**
     * Sets the value of the bilansid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mtabilanssv3Bilansid }
     *     
     */
    public void setBilansid(Mtabilanssv3Bilansid value) {
        this.bilansid = value;
    }

}
