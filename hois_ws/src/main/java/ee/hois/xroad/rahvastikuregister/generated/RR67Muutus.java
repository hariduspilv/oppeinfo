
package ee.hois.xroad.rahvastikuregister.generated;

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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR67_muutusRequestType"/&gt;
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
    "request"
})
@XmlRootElement(name = "RR67_muutus")
public class RR67Muutus {

    @XmlElement(required = true)
    protected RR67MuutusRequestType request;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR67MuutusRequestType }
     *     
     */
    public RR67MuutusRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR67MuutusRequestType }
     *     
     */
    public void setRequest(RR67MuutusRequestType value) {
        this.request = value;
    }

}