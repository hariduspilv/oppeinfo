
package ee.hois.xroad.sais2.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCandidateIntTestResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCandidateIntTestResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CandidateIntTestResult" type="{http://sais2.x-road.eu/}CandidateIntTestResult" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCandidateIntTestResult", propOrder = {
    "candidateIntTestResult"
})
public class ArrayOfCandidateIntTestResult {

    @XmlElement(name = "CandidateIntTestResult", nillable = true)
    protected List<CandidateIntTestResult> candidateIntTestResult;

    /**
     * Gets the value of the candidateIntTestResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the candidateIntTestResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCandidateIntTestResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CandidateIntTestResult }
     * 
     * 
     */
    public List<CandidateIntTestResult> getCandidateIntTestResult() {
        if (candidateIntTestResult == null) {
            candidateIntTestResult = new ArrayList<CandidateIntTestResult>();
        }
        return this.candidateIntTestResult;
    }

}
