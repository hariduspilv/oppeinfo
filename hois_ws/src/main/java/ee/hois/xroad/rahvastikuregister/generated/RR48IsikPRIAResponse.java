
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR48isikPRIARequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR48isikPRIAResponseType"/&gt;
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
@XmlRootElement(name = "RR48isikPRIAResponse")
public class RR48IsikPRIAResponse {

    @XmlElement(required = true)
    protected RR48IsikPRIARequestType request;
    @XmlElement(required = true)
    protected RR48IsikPRIAResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR48IsikPRIARequestType }
     *     
     */
    public RR48IsikPRIARequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR48IsikPRIARequestType }
     *     
     */
    public void setRequest(RR48IsikPRIARequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR48IsikPRIAResponseType }
     *     
     */
    public RR48IsikPRIAResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR48IsikPRIAResponseType }
     *     
     */
    public void setResponse(RR48IsikPRIAResponseType value) {
        this.response = value;
    }

}
