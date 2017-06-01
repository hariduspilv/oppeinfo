
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
 *         &lt;element name="request" type="{http://sais2.x-road.ee/producer/}AllAdmissionsExportRequest"/&gt;
 *         &lt;element name="response" type="{http://sais2.x-road.ee/producer/}AdmissionExportResponse" minOccurs="0"/&gt;
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
@XmlRootElement(name = "AllAdmissionsExportResponse")
public class AllAdmissionsExportResponse {

    @XmlElement(required = true, nillable = true)
    protected AllAdmissionsExportRequest request;
    protected AdmissionExportResponse response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link AllAdmissionsExportRequest }
     *     
     */
    public AllAdmissionsExportRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllAdmissionsExportRequest }
     *     
     */
    public void setRequest(AllAdmissionsExportRequest value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link AdmissionExportResponse }
     *     
     */
    public AdmissionExportResponse getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdmissionExportResponse }
     *     
     */
    public void setResponse(AdmissionExportResponse value) {
        this.response = value;
    }

}
