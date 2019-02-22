
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kustutamine_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kustutamine_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirjed" type="{http://arireg.x-road.eu/producer/}kustutamine_kirjed"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kustutamine_vastus", propOrder = {
    "kirjed"
})
public class KustutamineVastus {

    @XmlElement(required = true)
    protected KustutamineKirjed kirjed;

    /**
     * Gets the value of the kirjed property.
     * 
     * @return
     *     possible object is
     *     {@link KustutamineKirjed }
     *     
     */
    public KustutamineKirjed getKirjed() {
        return kirjed;
    }

    /**
     * Sets the value of the kirjed property.
     * 
     * @param value
     *     allowed object is
     *     {@link KustutamineKirjed }
     *     
     */
    public void setKirjed(KustutamineKirjed value) {
        this.kirjed = value;
    }

}
