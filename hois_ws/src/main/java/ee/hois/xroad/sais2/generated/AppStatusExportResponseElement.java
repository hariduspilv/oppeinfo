
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppStatusExportResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppStatusExportResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicationStatuses" type="{http://sais2.x-road.eu/}ArrayOfApplicationStatus" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppStatusExportResponse", propOrder = {
    "applicationStatuses"
})
public class AppStatusExportResponseElement {

    @XmlElement(name = "ApplicationStatuses")
    protected ArrayOfApplicationStatus applicationStatuses;

    /**
     * Gets the value of the applicationStatuses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicationStatus }
     *     
     */
    public ArrayOfApplicationStatus getApplicationStatuses() {
        return applicationStatuses;
    }

    /**
     * Sets the value of the applicationStatuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicationStatus }
     *     
     */
    public void setApplicationStatuses(ArrayOfApplicationStatus value) {
        this.applicationStatuses = value;
    }

}
