
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringesindus_v6_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringesindus_v6_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotjad" type="{http://arireg.x-road.eu/producer/}paringesindus_v6_ettevotted"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringesindus_v6_vastus", propOrder = {
    "ettevotjad"
})
public class ParingesindusV6Vastus {

    @XmlElement(required = true)
    protected ParingesindusV6Ettevotted ettevotjad;

    /**
     * Gets the value of the ettevotjad property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusV6Ettevotted }
     *     
     */
    public ParingesindusV6Ettevotted getEttevotjad() {
        return ettevotjad;
    }

    /**
     * Sets the value of the ettevotjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusV6Ettevotted }
     *     
     */
    public void setEttevotjad(ParingesindusV6Ettevotted value) {
        this.ettevotjad = value;
    }

}
