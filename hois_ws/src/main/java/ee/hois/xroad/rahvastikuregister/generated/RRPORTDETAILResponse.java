
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RRPORTDETAILRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RRPORTDETAILResponseType"/&gt;
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
@XmlRootElement(name = "RRPORTDETAILResponse")
public class RRPORTDETAILResponse {

    @XmlElement(required = true)
    protected RRPORTDETAILRequestType request;
    @XmlElement(required = true)
    protected RRPORTDETAILResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RRPORTDETAILRequestType }
     *     
     */
    public RRPORTDETAILRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTDETAILRequestType }
     *     
     */
    public void setRequest(RRPORTDETAILRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RRPORTDETAILResponseType }
     *     
     */
    public RRPORTDETAILResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTDETAILResponseType }
     *     
     */
    public void setResponse(RRPORTDETAILResponseType value) {
        this.response = value;
    }

}
