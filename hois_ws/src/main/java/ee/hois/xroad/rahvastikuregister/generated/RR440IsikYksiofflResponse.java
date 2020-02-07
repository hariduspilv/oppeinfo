
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR440isikYksiofflRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR440isikYksiofflResponseType"/&gt;
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
@XmlRootElement(name = "RR440isikYksiofflResponse")
public class RR440IsikYksiofflResponse {

    @XmlElement(required = true)
    protected RR440IsikYksiofflRequestType request;
    @XmlElement(required = true)
    protected RR440IsikYksiofflResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR440IsikYksiofflRequestType }
     *     
     */
    public RR440IsikYksiofflRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR440IsikYksiofflRequestType }
     *     
     */
    public void setRequest(RR440IsikYksiofflRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR440IsikYksiofflResponseType }
     *     
     */
    public RR440IsikYksiofflResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR440IsikYksiofflResponseType }
     *     
     */
    public void setResponse(RR440IsikYksiofflResponseType value) {
        this.response = value;
    }

}
