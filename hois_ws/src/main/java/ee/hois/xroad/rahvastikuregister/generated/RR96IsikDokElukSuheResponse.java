
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RR96IsikDokElukSuheRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RR96IsikDokElukSuheResponseType"/&gt;
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
@XmlRootElement(name = "RR96IsikDokElukSuheResponse")
public class RR96IsikDokElukSuheResponse {

    @XmlElement(required = true)
    protected RR96IsikDokElukSuheRequestType request;
    @XmlElement(required = true)
    protected RR96IsikDokElukSuheResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RR96IsikDokElukSuheRequestType }
     *     
     */
    public RR96IsikDokElukSuheRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR96IsikDokElukSuheRequestType }
     *     
     */
    public void setRequest(RR96IsikDokElukSuheRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RR96IsikDokElukSuheResponseType }
     *     
     */
    public RR96IsikDokElukSuheResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR96IsikDokElukSuheResponseType }
     *     
     */
    public void setResponse(RR96IsikDokElukSuheResponseType value) {
        this.response = value;
    }

}
