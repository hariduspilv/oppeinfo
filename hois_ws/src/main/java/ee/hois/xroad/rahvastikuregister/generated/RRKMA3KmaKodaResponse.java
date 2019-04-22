
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RRKMA3KmaKodaRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RRKMA3KmaKodaResponseType"/&gt;
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
@XmlRootElement(name = "RRKMA3KmaKodaResponse")
public class RRKMA3KmaKodaResponse {

    @XmlElement(required = true)
    protected RRKMA3KmaKodaRequestType request;
    @XmlElement(required = true)
    protected RRKMA3KmaKodaResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RRKMA3KmaKodaRequestType }
     *     
     */
    public RRKMA3KmaKodaRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKMA3KmaKodaRequestType }
     *     
     */
    public void setRequest(RRKMA3KmaKodaRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RRKMA3KmaKodaResponseType }
     *     
     */
    public RRKMA3KmaKodaResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKMA3KmaKodaResponseType }
     *     
     */
    public void setResponse(RRKMA3KmaKodaResponseType value) {
        this.response = value;
    }

}
