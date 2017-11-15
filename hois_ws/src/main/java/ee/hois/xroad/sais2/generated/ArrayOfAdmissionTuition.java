
package ee.hois.xroad.sais2.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfAdmissionTuition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAdmissionTuition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AdmissionTuition" type="{http://sais2.x-road.eu/}AdmissionTuition" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAdmissionTuition", propOrder = {
    "admissionTuition"
})
public class ArrayOfAdmissionTuition {

    @XmlElement(name = "AdmissionTuition", nillable = true)
    protected List<AdmissionTuition> admissionTuition;

    /**
     * Gets the value of the admissionTuition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the admissionTuition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdmissionTuition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdmissionTuition }
     * 
     * 
     */
    public List<AdmissionTuition> getAdmissionTuition() {
        if (admissionTuition == null) {
            admissionTuition = new ArrayList<AdmissionTuition>();
        }
        return this.admissionTuition;
    }

}
