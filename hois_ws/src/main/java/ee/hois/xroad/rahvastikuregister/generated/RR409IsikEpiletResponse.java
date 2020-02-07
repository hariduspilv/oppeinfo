
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR409isikEpiletRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR409isikEpiletResponseType"/&gt;
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
@XmlRootElement(name = "RR409isikEpiletResponse")
public class RR409IsikEpiletResponse {

    @XmlElement(required = true)
    protected RR409IsikEpiletRequestType request;
    @XmlElement(required = true)
    protected RR409IsikEpiletResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR409IsikEpiletRequestType }
     *     
     */
    public RR409IsikEpiletRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR409IsikEpiletRequestType }
     *     
     */
    public void setRequest(RR409IsikEpiletRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR409IsikEpiletResponseType }
     *     
     */
    public RR409IsikEpiletResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR409IsikEpiletResponseType }
     *     
     */
    public void setResponse(RR409IsikEpiletResponseType value) {
        this.response = value;
    }

}
