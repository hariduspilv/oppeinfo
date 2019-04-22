
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR408integratsioonRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR408integratsioonResponseType"/&gt;
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
@XmlRootElement(name = "RR408integratsioonResponse")
public class RR408IntegratsioonResponse {

    @XmlElement(required = true)
    protected RR408IntegratsioonRequestType request;
    @XmlElement(required = true)
    protected RR408IntegratsioonResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR408IntegratsioonRequestType }
     *     
     */
    public RR408IntegratsioonRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR408IntegratsioonRequestType }
     *     
     */
    public void setRequest(RR408IntegratsioonRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR408IntegratsioonResponseType }
     *     
     */
    public RR408IntegratsioonResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR408IntegratsioonResponseType }
     *     
     */
    public void setResponse(RR408IntegratsioonResponseType value) {
        this.response = value;
    }

}
