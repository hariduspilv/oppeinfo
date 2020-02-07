
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR407HMIsikEestkRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR407HMIsikEestkResponseType"/&gt;
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
@XmlRootElement(name = "RR407HMIsikEestkResponse")
public class RR407HMIsikEestkResponse {

    @XmlElement(required = true)
    protected RR407HMIsikEestkRequestType request;
    @XmlElement(required = true)
    protected RR407HMIsikEestkResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR407HMIsikEestkRequestType }
     *     
     */
    public RR407HMIsikEestkRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR407HMIsikEestkRequestType }
     *     
     */
    public void setRequest(RR407HMIsikEestkRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR407HMIsikEestkResponseType }
     *     
     */
    public RR407HMIsikEestkResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR407HMIsikEestkResponseType }
     *     
     */
    public void setResponse(RR407HMIsikEestkResponseType value) {
        this.response = value;
    }

}
