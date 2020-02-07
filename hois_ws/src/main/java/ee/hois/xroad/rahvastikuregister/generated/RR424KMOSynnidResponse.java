
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR424KMOSynnidRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR424KMOSynnidResponseType"/&gt;
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
@XmlRootElement(name = "RR424KMOSynnidResponse")
public class RR424KMOSynnidResponse {

    @XmlElement(required = true)
    protected RR424KMOSynnidRequestType request;
    @XmlElement(required = true)
    protected RR424KMOSynnidResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR424KMOSynnidRequestType }
     *     
     */
    public RR424KMOSynnidRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR424KMOSynnidRequestType }
     *     
     */
    public void setRequest(RR424KMOSynnidRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR424KMOSynnidResponseType }
     *     
     */
    public RR424KMOSynnidResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR424KMOSynnidResponseType }
     *     
     */
    public void setResponse(RR424KMOSynnidResponseType value) {
        this.response = value;
    }

}
