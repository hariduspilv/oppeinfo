
package ee.hois.xroad.sais2.generated;

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
 *         &lt;element name="request" type="{http://sais2.x-road.eu/}AppDismissRequest"/&gt;
 *         &lt;element name="response" type="{http://sais2.x-road.eu/}AppDismissResponse" minOccurs="0"/&gt;
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
@XmlRootElement(name = "DismissApplicationResponse")
public class DismissApplicationResponse {

    @XmlElement(required = true, nillable = true)
    protected AppDismissRequest request;
    protected AppDismissResponse response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link AppDismissRequest }
     *     
     */
    public AppDismissRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppDismissRequest }
     *     
     */
    public void setRequest(AppDismissRequest value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link AppDismissResponse }
     *     
     */
    public AppDismissResponse getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppDismissResponse }
     *     
     */
    public void setResponse(AppDismissResponse value) {
        this.response = value;
    }

}
