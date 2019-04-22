
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR421KMOElKodElukohaRegistreerimineRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR421KMOElKodElukohaRegistreerimineResponseType"/&gt;
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
@XmlRootElement(name = "RR421KMOElKodElukohaRegistreerimineResponse")
public class RR421KMOElKodElukohaRegistreerimineResponse {

    @XmlElement(required = true)
    protected RR421KMOElKodElukohaRegistreerimineRequestType request;
    @XmlElement(required = true)
    protected RR421KMOElKodElukohaRegistreerimineResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR421KMOElKodElukohaRegistreerimineRequestType }
     *     
     */
    public RR421KMOElKodElukohaRegistreerimineRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR421KMOElKodElukohaRegistreerimineRequestType }
     *     
     */
    public void setRequest(RR421KMOElKodElukohaRegistreerimineRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR421KMOElKodElukohaRegistreerimineResponseType }
     *     
     */
    public RR421KMOElKodElukohaRegistreerimineResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR421KMOElKodElukohaRegistreerimineResponseType }
     *     
     */
    public void setResponse(RR421KMOElKodElukohaRegistreerimineResponseType value) {
        this.response = value;
    }

}
