
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR86LapseVanemadRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR86LapseVanemadResponseType"/&gt;
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
@XmlRootElement(name = "RR86LapseVanemadResponse")
public class RR86LapseVanemadResponse {

    @XmlElement(required = true)
    protected RR86LapseVanemadRequestType request;
    @XmlElement(required = true)
    protected RR86LapseVanemadResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR86LapseVanemadRequestType }
     *     
     */
    public RR86LapseVanemadRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR86LapseVanemadRequestType }
     *     
     */
    public void setRequest(RR86LapseVanemadRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR86LapseVanemadResponseType }
     *     
     */
    public RR86LapseVanemadResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR86LapseVanemadResponseType }
     *     
     */
    public void setResponse(RR86LapseVanemadResponseType value) {
        this.response = value;
    }

}
