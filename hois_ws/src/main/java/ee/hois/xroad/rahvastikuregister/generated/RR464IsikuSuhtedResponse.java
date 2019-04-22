
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR464isikuSuhtedRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR464isikuSuhtedResponseType"/&gt;
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
@XmlRootElement(name = "RR464isikuSuhtedResponse")
public class RR464IsikuSuhtedResponse {

    @XmlElement(required = true)
    protected RR464IsikuSuhtedRequestType request;
    @XmlElement(required = true)
    protected RR464IsikuSuhtedResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR464IsikuSuhtedRequestType }
     *     
     */
    public RR464IsikuSuhtedRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR464IsikuSuhtedRequestType }
     *     
     */
    public void setRequest(RR464IsikuSuhtedRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR464IsikuSuhtedResponseType }
     *     
     */
    public RR464IsikuSuhtedResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR464IsikuSuhtedResponseType }
     *     
     */
    public void setResponse(RR464IsikuSuhtedResponseType value) {
        this.response = value;
    }

}
