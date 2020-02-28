
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR443elukohtadegaRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR443elukohtadegaResponseType"/&gt;
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
@XmlRootElement(name = "RR443elukohtadegaResponse")
public class RR443ElukohtadegaResponse {

    @XmlElement(required = true)
    protected RR443ElukohtadegaRequestType request;
    @XmlElement(required = true)
    protected RR443ElukohtadegaResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR443ElukohtadegaRequestType }
     *     
     */
    public RR443ElukohtadegaRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR443ElukohtadegaRequestType }
     *     
     */
    public void setRequest(RR443ElukohtadegaRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR443ElukohtadegaResponseType }
     *     
     */
    public RR443ElukohtadegaResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR443ElukohtadegaResponseType }
     *     
     */
    public void setResponse(RR443ElukohtadegaResponseType value) {
        this.response = value;
    }

}