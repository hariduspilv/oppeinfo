
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR420KMOOnElukohaTeadePoleElamisOigustRequestType"/&gt;
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
@XmlRootElement(name = "RR420KMOOnElukohaTeadePoleElamisOigust")
public class RR420KMOOnElukohaTeadePoleElamisOigust {

    @XmlElement(required = true)
    protected RR420KMOOnElukohaTeadePoleElamisOigustRequestType request;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR420KMOOnElukohaTeadePoleElamisOigustRequestType }
     *     
     */
    public RR420KMOOnElukohaTeadePoleElamisOigustRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR420KMOOnElukohaTeadePoleElamisOigustRequestType }
     *     
     */
    public void setRequest(RR420KMOOnElukohaTeadePoleElamisOigustRequestType value) {
        this.request = value;
    }

}
