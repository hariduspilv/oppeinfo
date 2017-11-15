
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppImportRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppImportRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Applications" type="{http://sais2.x-road.eu/}ArrayOfImportApp" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppImportRequest", propOrder = {
    "applications"
})
public class AppImportRequest {

    @XmlElement(name = "Applications")
    protected ArrayOfImportApp applications;

    /**
     * Gets the value of the applications property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfImportApp }
     *     
     */
    public ArrayOfImportApp getApplications() {
        return applications;
    }

    /**
     * Sets the value of the applications property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfImportApp }
     *     
     */
    public void setApplications(ArrayOfImportApp value) {
        this.applications = value;
    }

}
