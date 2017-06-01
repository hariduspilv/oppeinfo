
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tunnistused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}loputunnistusTunnistused"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tunnistused"
})
@XmlRootElement(name = "kodLoputunnistusResponse")
public class KodLoputunnistusResponse {

    @XmlElement(required = true, nillable = true)
    protected LoputunnistusTunnistused tunnistused;

    /**
     * Gets the value of the tunnistused property.
     * 
     * @return
     *     possible object is
     *     {@link LoputunnistusTunnistused }
     *     
     */
    public LoputunnistusTunnistused getTunnistused() {
        return tunnistused;
    }

    /**
     * Sets the value of the tunnistused property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoputunnistusTunnistused }
     *     
     */
    public void setTunnistused(LoputunnistusTunnistused value) {
        this.tunnistused = value;
    }

}
