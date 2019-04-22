
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR470isikukoodid16kuni26aRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR470isikukoodid16kuni26aResponseType"/&gt;
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
@XmlRootElement(name = "RR470isikukoodid16kuni26aResponse")
public class RR470Isikukoodid16Kuni26AResponse {

    @XmlElement(required = true)
    protected RR470Isikukoodid16Kuni26ARequestType request;
    @XmlElement(required = true)
    protected RR470Isikukoodid16Kuni26AResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR470Isikukoodid16Kuni26ARequestType }
     *     
     */
    public RR470Isikukoodid16Kuni26ARequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR470Isikukoodid16Kuni26ARequestType }
     *     
     */
    public void setRequest(RR470Isikukoodid16Kuni26ARequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR470Isikukoodid16Kuni26AResponseType }
     *     
     */
    public RR470Isikukoodid16Kuni26AResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR470Isikukoodid16Kuni26AResponseType }
     *     
     */
    public void setResponse(RR470Isikukoodid16Kuni26AResponseType value) {
        this.response = value;
    }

}
