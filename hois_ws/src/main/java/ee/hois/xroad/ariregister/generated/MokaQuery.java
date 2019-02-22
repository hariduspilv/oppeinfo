
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MokaQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MokaQuery"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Moka" type="{http://arireg.x-road.eu/producer/}MokaType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MokaQuery", propOrder = {
    "moka"
})
public class MokaQuery {

    @XmlElement(name = "Moka", required = true)
    protected MokaType moka;

    /**
     * Gets the value of the moka property.
     * 
     * @return
     *     possible object is
     *     {@link MokaType }
     *     
     */
    public MokaType getMoka() {
        return moka;
    }

    /**
     * Sets the value of the moka property.
     * 
     * @param value
     *     allowed object is
     *     {@link MokaType }
     *     
     */
    public void setMoka(MokaType value) {
        this.moka = value;
    }

}
