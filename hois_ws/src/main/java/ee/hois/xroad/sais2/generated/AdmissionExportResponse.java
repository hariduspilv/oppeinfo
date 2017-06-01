
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdmissionExportResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdmissionExportResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Admissions" type="{http://sais2.x-road.ee/producer/}ArrayOfAdmission" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdmissionExportResponse", propOrder = {
    "admissions"
})
public class AdmissionExportResponse {

    @XmlElement(name = "Admissions")
    protected ArrayOfAdmission admissions;

    /**
     * Gets the value of the admissions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdmission }
     *     
     */
    public ArrayOfAdmission getAdmissions() {
        return admissions;
    }

    /**
     * Sets the value of the admissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdmission }
     *     
     */
    public void setAdmissions(ArrayOfAdmission value) {
        this.admissions = value;
    }

}
