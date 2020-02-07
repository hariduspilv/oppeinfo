
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RRs1305IsikukoodiTellimineRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RRs1305IsikukoodiTellimineResponseType"/&gt;
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
@XmlRootElement(name = "RRs1305IsikukoodiTellimineResponse")
public class RRs1305IsikukoodiTellimineResponse {

    @XmlElement(required = true)
    protected RRs1305IsikukoodiTellimineRequestType request;
    @XmlElement(required = true)
    protected RRs1305IsikukoodiTellimineResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RRs1305IsikukoodiTellimineRequestType }
     *     
     */
    public RRs1305IsikukoodiTellimineRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRs1305IsikukoodiTellimineRequestType }
     *     
     */
    public void setRequest(RRs1305IsikukoodiTellimineRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RRs1305IsikukoodiTellimineResponseType }
     *     
     */
    public RRs1305IsikukoodiTellimineResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRs1305IsikukoodiTellimineResponseType }
     *     
     */
    public void setResponse(RRs1305IsikukoodiTellimineResponseType value) {
        this.response = value;
    }

}
