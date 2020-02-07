
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR465isikuHooldusoigusedRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR465isikuHooldusoigusedResponseType"/&gt;
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
@XmlRootElement(name = "RR465isikuHooldusoigusedResponse")
public class RR465IsikuHooldusoigusedResponse {

    @XmlElement(required = true)
    protected RR465IsikuHooldusoigusedRequestType request;
    @XmlElement(required = true)
    protected RR465IsikuHooldusoigusedResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR465IsikuHooldusoigusedRequestType }
     *     
     */
    public RR465IsikuHooldusoigusedRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR465IsikuHooldusoigusedRequestType }
     *     
     */
    public void setRequest(RR465IsikuHooldusoigusedRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR465IsikuHooldusoigusedResponseType }
     *     
     */
    public RR465IsikuHooldusoigusedResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR465IsikuHooldusoigusedResponseType }
     *     
     */
    public void setResponse(RR465IsikuHooldusoigusedResponseType value) {
        this.response = value;
    }

}
