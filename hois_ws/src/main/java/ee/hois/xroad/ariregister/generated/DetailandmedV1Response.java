
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailandmed_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_Query"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_Vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v1Response", propOrder = {
    "paring",
    "keha"
})
public class DetailandmedV1Response {

    @XmlElement(required = true)
    protected DetailandmedV5Query paring;
    @XmlElement(required = true)
    protected DetailandmedV5Vastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Query }
     *     
     */
    public DetailandmedV5Query getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Query }
     *     
     */
    public void setParing(DetailandmedV5Query value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Vastus }
     *     
     */
    public DetailandmedV5Vastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Vastus }
     *     
     */
    public void setKeha(DetailandmedV5Vastus value) {
        this.keha = value;
    }

}
