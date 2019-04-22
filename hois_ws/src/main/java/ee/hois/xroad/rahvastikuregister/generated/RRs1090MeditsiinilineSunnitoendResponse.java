
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
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RRs1090MeditsiinilineSunnitoendRequestType"/&gt;
 *         &lt;element name="response" type="{http://rr.x-road.eu/producer}RRs1090MeditsiinilineSunnitoendResponseType"/&gt;
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
@XmlRootElement(name = "RRs1090MeditsiinilineSunnitoendResponse")
public class RRs1090MeditsiinilineSunnitoendResponse {

    @XmlElement(required = true)
    protected RRs1090MeditsiinilineSunnitoendRequestType request;
    @XmlElement(required = true)
    protected RRs1090MeditsiinilineSunnitoendResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RRs1090MeditsiinilineSunnitoendRequestType }
     *     
     */
    public RRs1090MeditsiinilineSunnitoendRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRs1090MeditsiinilineSunnitoendRequestType }
     *     
     */
    public void setRequest(RRs1090MeditsiinilineSunnitoendRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link RRs1090MeditsiinilineSunnitoendResponseType }
     *     
     */
    public RRs1090MeditsiinilineSunnitoendResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRs1090MeditsiinilineSunnitoendResponseType }
     *     
     */
    public void setResponse(RRs1090MeditsiinilineSunnitoendResponseType value) {
        this.response = value;
    }

}
