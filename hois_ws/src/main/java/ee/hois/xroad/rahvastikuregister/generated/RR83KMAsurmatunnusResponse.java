
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR83KMAsurmatunnusRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR83KMAsurmatunnusResponseType"/&gt;
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
@XmlRootElement(name = "RR83KMAsurmatunnusResponse")
public class RR83KMAsurmatunnusResponse {

    @XmlElement(required = true)
    protected RR83KMAsurmatunnusRequestType request;
    @XmlElement(required = true)
    protected RR83KMAsurmatunnusResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR83KMAsurmatunnusRequestType }
     *     
     */
    public RR83KMAsurmatunnusRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR83KMAsurmatunnusRequestType }
     *     
     */
    public void setRequest(RR83KMAsurmatunnusRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR83KMAsurmatunnusResponseType }
     *     
     */
    public RR83KMAsurmatunnusResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR83KMAsurmatunnusResponseType }
     *     
     */
    public void setResponse(RR83KMAsurmatunnusResponseType value) {
        this.response = value;
    }

}
