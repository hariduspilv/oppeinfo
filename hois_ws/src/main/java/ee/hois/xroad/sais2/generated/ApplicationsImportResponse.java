
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
 *         &lt;element name="request" type="{http://sais2.x-road.eu/}AppImportRequest"/&gt;
 *         &lt;element name="response" type="{http://sais2.x-road.eu/}AppImportResponse" minOccurs="0"/&gt;
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
@XmlRootElement(name = "ApplicationsImportResponse")
public class ApplicationsImportResponse {

    @XmlElement(required = true, nillable = true)
    protected AppImportRequest request;
    protected AppImportResponse response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link AppImportRequest }
     *     
     */
    public AppImportRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppImportRequest }
     *     
     */
    public void setRequest(AppImportRequest value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link AppImportResponse }
     *     
     */
    public AppImportResponse getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppImportResponse }
     *     
     */
    public void setResponse(AppImportResponse value) {
        this.response = value;
    }

}
