
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ImportApp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportApp"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InstitutionRegCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OtherIdCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Birthday" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CitizenshipCountryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ResidencyPermitNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ResidencyPermitCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ResidencyPermitExpireDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Postalcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AdmissionId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="AdmissionTuitionId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="StudyFormCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsFullLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsPartialLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsUndefinedLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CandidateEducations" type="{http://sais2.x-road.eu/}ArrayOfCandidateEducationItem" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportApp", propOrder = {
    "applicationId",
    "institutionRegCode",
    "idCode",
    "otherIdCode",
    "firstName",
    "lastName",
    "sex",
    "birthday",
    "countryCode",
    "citizenshipCountryCode",
    "residencyPermitNumber",
    "residencyPermitCode",
    "residencyPermitExpireDate",
    "phone",
    "email",
    "address",
    "postalcode",
    "admissionId",
    "admissionTuitionId",
    "studyFormCode",
    "applicationNumber",
    "isFullLoad",
    "isPartialLoad",
    "isUndefinedLoad",
    "candidateEducations"
})
public class ImportApp {

    @XmlElement(name = "ApplicationId")
    protected String applicationId;
    @XmlElement(name = "InstitutionRegCode")
    protected String institutionRegCode;
    @XmlElement(name = "IdCode")
    protected String idCode;
    @XmlElement(name = "OtherIdCode")
    protected String otherIdCode;
    @XmlElement(name = "FirstName")
    protected String firstName;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "Sex")
    protected String sex;
    @XmlElement(name = "Birthday", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar birthday;
    @XmlElement(name = "CountryCode")
    protected String countryCode;
    @XmlElement(name = "CitizenshipCountryCode")
    protected String citizenshipCountryCode;
    @XmlElement(name = "ResidencyPermitNumber")
    protected String residencyPermitNumber;
    @XmlElement(name = "ResidencyPermitCode")
    protected String residencyPermitCode;
    @XmlElement(name = "ResidencyPermitExpireDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar residencyPermitExpireDate;
    @XmlElement(name = "Phone")
    protected String phone;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "Address")
    protected String address;
    @XmlElement(name = "Postalcode")
    protected String postalcode;
    @XmlElement(name = "AdmissionId", required = true)
    protected String admissionId;
    @XmlElement(name = "AdmissionTuitionId", required = true, nillable = true)
    protected String admissionTuitionId;
    @XmlElement(name = "StudyFormCode")
    protected String studyFormCode;
    @XmlElement(name = "ApplicationNumber")
    protected String applicationNumber;
    @XmlElement(name = "IsFullLoad")
    protected boolean isFullLoad;
    @XmlElement(name = "IsPartialLoad")
    protected boolean isPartialLoad;
    @XmlElement(name = "IsUndefinedLoad")
    protected boolean isUndefinedLoad;
    @XmlElement(name = "CandidateEducations")
    protected ArrayOfCandidateEducationItem candidateEducations;

    /**
     * Gets the value of the applicationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the value of the applicationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationId(String value) {
        this.applicationId = value;
    }

    /**
     * Gets the value of the institutionRegCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitutionRegCode() {
        return institutionRegCode;
    }

    /**
     * Sets the value of the institutionRegCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitutionRegCode(String value) {
        this.institutionRegCode = value;
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
     * Gets the value of the otherIdCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherIdCode() {
        return otherIdCode;
    }

    /**
     * Sets the value of the otherIdCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherIdCode(String value) {
        this.otherIdCode = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * Gets the value of the birthday property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthday() {
        return birthday;
    }

    /**
     * Sets the value of the birthday property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthday(XMLGregorianCalendar value) {
        this.birthday = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the citizenshipCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitizenshipCountryCode() {
        return citizenshipCountryCode;
    }

    /**
     * Sets the value of the citizenshipCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitizenshipCountryCode(String value) {
        this.citizenshipCountryCode = value;
    }

    /**
     * Gets the value of the residencyPermitNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidencyPermitNumber() {
        return residencyPermitNumber;
    }

    /**
     * Sets the value of the residencyPermitNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidencyPermitNumber(String value) {
        this.residencyPermitNumber = value;
    }

    /**
     * Gets the value of the residencyPermitCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidencyPermitCode() {
        return residencyPermitCode;
    }

    /**
     * Sets the value of the residencyPermitCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidencyPermitCode(String value) {
        this.residencyPermitCode = value;
    }

    /**
     * Gets the value of the residencyPermitExpireDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResidencyPermitExpireDate() {
        return residencyPermitExpireDate;
    }

    /**
     * Sets the value of the residencyPermitExpireDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResidencyPermitExpireDate(XMLGregorianCalendar value) {
        this.residencyPermitExpireDate = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the postalcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalcode() {
        return postalcode;
    }

    /**
     * Sets the value of the postalcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalcode(String value) {
        this.postalcode = value;
    }

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
     * Gets the value of the admissionTuitionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdmissionTuitionId() {
        return admissionTuitionId;
    }

    /**
     * Sets the value of the admissionTuitionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdmissionTuitionId(String value) {
        this.admissionTuitionId = value;
    }

    /**
     * Gets the value of the studyFormCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyFormCode() {
        return studyFormCode;
    }

    /**
     * Sets the value of the studyFormCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyFormCode(String value) {
        this.studyFormCode = value;
    }

    /**
     * Gets the value of the applicationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationNumber() {
        return applicationNumber;
    }

    /**
     * Sets the value of the applicationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationNumber(String value) {
        this.applicationNumber = value;
    }

    /**
     * Gets the value of the isFullLoad property.
     * 
     */
    public boolean isIsFullLoad() {
        return isFullLoad;
    }

    /**
     * Sets the value of the isFullLoad property.
     * 
     */
    public void setIsFullLoad(boolean value) {
        this.isFullLoad = value;
    }

    /**
     * Gets the value of the isPartialLoad property.
     * 
     */
    public boolean isIsPartialLoad() {
        return isPartialLoad;
    }

    /**
     * Sets the value of the isPartialLoad property.
     * 
     */
    public void setIsPartialLoad(boolean value) {
        this.isPartialLoad = value;
    }

    /**
     * Gets the value of the isUndefinedLoad property.
     * 
     */
    public boolean isIsUndefinedLoad() {
        return isUndefinedLoad;
    }

    /**
     * Sets the value of the isUndefinedLoad property.
     * 
     */
    public void setIsUndefinedLoad(boolean value) {
        this.isUndefinedLoad = value;
    }

    /**
     * Gets the value of the candidateEducations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCandidateEducationItem }
     *     
     */
    public ArrayOfCandidateEducationItem getCandidateEducations() {
        return candidateEducations;
    }

    /**
     * Sets the value of the candidateEducations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCandidateEducationItem }
     *     
     */
    public void setCandidateEducations(ArrayOfCandidateEducationItem value) {
        this.candidateEducations = value;
    }

}
