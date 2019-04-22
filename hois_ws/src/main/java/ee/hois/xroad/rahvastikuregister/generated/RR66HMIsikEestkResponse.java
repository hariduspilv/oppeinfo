
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR66HMIsikEestkRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR66HMIsikEestkResponseType"/&gt;
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
    "request",
    "response"
})
@XmlRootElement(name = "RR66HMIsikEestkResponse")
public class RR66HMIsikEestkResponse {

    @XmlElement(required = true)
    protected RR66HMIsikEestkRequestType request;
    @XmlElement(required = true)
    protected RR66HMIsikEestkResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR66HMIsikEestkRequestType }
     *     
     */
    public RR66HMIsikEestkRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR66HMIsikEestkRequestType }
     *     
     */
    public void setRequest(RR66HMIsikEestkRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR66HMIsikEestkResponseType }
     *     
     */
    public RR66HMIsikEestkResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR66HMIsikEestkResponseType }
     *     
     */
    public void setResponse(RR66HMIsikEestkResponseType value) {
        this.response = value;
    }

}
