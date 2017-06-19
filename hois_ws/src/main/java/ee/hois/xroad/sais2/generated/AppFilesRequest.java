
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AppFilesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppFilesRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AdmissionId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="StatusChangeDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StatusChangeDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IdCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationStatusValues" type="{http://sais2.x-road.ee/producer/}ArrayOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppFilesRequest", propOrder = {
    "admissionId",
    "statusChangeDateFrom",
    "statusChangeDateTo",
    "idCode",
    "studyLevel",
    "applicationStatusValues"
})
public class AppFilesRequest {

    @XmlElement(name = "AdmissionId", required = true, nillable = true)
    protected String admissionId;
    @XmlElement(name = "StatusChangeDateFrom", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar statusChangeDateFrom;
    @XmlElement(name = "StatusChangeDateTo", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar statusChangeDateTo;
    @XmlElement(name = "IdCode")
    protected String idCode;
    @XmlElement(name = "StudyLevel")
    protected String studyLevel;
    @XmlElement(name = "ApplicationStatusValues")
    protected ArrayOfString applicationStatusValues;

    /**
     * Gets the value of the admissionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdmissionId() {
        return admissionId;
    }

    /**
     * Sets the value of the admissionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdmissionId(String value) {
        this.admissionId = value;
    }

    /**
     * Gets the value of the statusChangeDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStatusChangeDateFrom() {
        return statusChangeDateFrom;
    }

    /**
     * Sets the value of the statusChangeDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStatusChangeDateFrom(XMLGregorianCalendar value) {
        this.statusChangeDateFrom = value;
    }

    /**
     * Gets the value of the statusChangeDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStatusChangeDateTo() {
        return statusChangeDateTo;
    }

    /**
     * Sets the value of the statusChangeDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStatusChangeDateTo(XMLGregorianCalendar value) {
        this.statusChangeDateTo = value;
    }

    /**
     * Gets the value of the idCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * Sets the value of the idCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCode(String value) {
        this.idCode = value;
    }

    /**
     * Gets the value of the studyLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyLevel() {
        return studyLevel;
    }

    /**
     * Sets the value of the studyLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyLevel(String value) {
        this.studyLevel = value;
    }

    /**
     * Gets the value of the applicationStatusValues property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getApplicationStatusValues() {
        return applicationStatusValues;
    }

    /**
     * Sets the value of the applicationStatusValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setApplicationStatusValues(ArrayOfString value) {
        this.applicationStatusValues = value;
    }

}