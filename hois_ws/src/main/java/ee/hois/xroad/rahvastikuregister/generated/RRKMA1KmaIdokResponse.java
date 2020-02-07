
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RRKMA1KmaIdokRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RRKMA1KmaIdokResponseType"/&gt;
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
@XmlRootElement(name = "RRKMA1KmaIdokResponse")
public class RRKMA1KmaIdokResponse {

    @XmlElement(required = true)
    protected RRKMA1KmaIdokRequestType request;
    @XmlElement(required = true)
    protected RRKMA1KmaIdokResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RRKMA1KmaIdokRequestType }
     *     
     */
    public RRKMA1KmaIdokRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKMA1KmaIdokRequestType }
     *     
     */
    public void setRequest(RRKMA1KmaIdokRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RRKMA1KmaIdokResponseType }
     *     
     */
    public RRKMA1KmaIdokResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKMA1KmaIdokResponseType }
     *     
     */
    public void setResponse(RRKMA1KmaIdokResponseType value) {
        this.response = value;
    }

}
