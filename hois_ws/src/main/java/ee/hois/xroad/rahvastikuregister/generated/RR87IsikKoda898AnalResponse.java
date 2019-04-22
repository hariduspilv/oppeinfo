
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR87IsikKoda898AnalRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR87IsikKoda898AnalResponseType"/&gt;
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
@XmlRootElement(name = "RR87IsikKoda898AnalResponse")
public class RR87IsikKoda898AnalResponse {

    @XmlElement(required = true)
    protected RR87IsikKoda898AnalRequestType request;
    @XmlElement(required = true)
    protected RR87IsikKoda898AnalResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR87IsikKoda898AnalRequestType }
     *     
     */
    public RR87IsikKoda898AnalRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR87IsikKoda898AnalRequestType }
     *     
     */
    public void setRequest(RR87IsikKoda898AnalRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR87IsikKoda898AnalResponseType }
     *     
     */
    public RR87IsikKoda898AnalResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR87IsikKoda898AnalResponseType }
     *     
     */
    public void setResponse(RR87IsikKoda898AnalResponseType value) {
        this.response = value;
    }

}
