
package ee.hois.xroad.sais2.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfAdmissionCurriculum complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAdmissionCurriculum"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AdmissionCurriculum" type="{http://sais2.x-road.ee/producer/}AdmissionCurriculum" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAdmissionCurriculum", propOrder = {
    "admissionCurriculum"
})
public class ArrayOfAdmissionCurriculum {

    @XmlElement(name = "AdmissionCurriculum", nillable = true)
    protected List<AdmissionCurriculum> admissionCurriculum;

    /**
     * Gets the value of the admissionCurriculum property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the admissionCurriculum property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdmissionCurriculum().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdmissionCurriculum }
     * 
     * 
     */
    public List<AdmissionCurriculum> getAdmissionCurriculum() {
        if (admissionCurriculum == null) {
            admissionCurriculum = new ArrayList<AdmissionCurriculum>();
        }
        return this.admissionCurriculum;
    }

}
