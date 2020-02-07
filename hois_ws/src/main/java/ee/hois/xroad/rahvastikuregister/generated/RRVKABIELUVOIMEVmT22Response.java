
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RRVK_ABIELUVOIMEVmT22RequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RRVK_ABIELUVOIMEVmT22ResponseType"/&gt;
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
@XmlRootElement(name = "RRVK_ABIELUVOIMEVmT22Response")
public class RRVKABIELUVOIMEVmT22Response {

    @XmlElement(required = true)
    protected RRVKABIELUVOIMEVmT22RequestType request;
    @XmlElement(required = true)
    protected RRVKABIELUVOIMEVmT22ResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RRVKABIELUVOIMEVmT22RequestType }
     *     
     */
    public RRVKABIELUVOIMEVmT22RequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRVKABIELUVOIMEVmT22RequestType }
     *     
     */
    public void setRequest(RRVKABIELUVOIMEVmT22RequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RRVKABIELUVOIMEVmT22ResponseType }
     *     
     */
    public RRVKABIELUVOIMEVmT22ResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRVKABIELUVOIMEVmT22ResponseType }
     *     
     */
    public void setResponse(RRVKABIELUVOIMEVmT22ResponseType value) {
        this.response = value;
    }

}
