
package ee.hois.xroad.sais2.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Application complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Application"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="AdmissionId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="AdmissionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShowNameInRanking" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SexClassification" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="IdCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OtherIdNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportApplicationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportAppInstitutionRegNr" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsImported" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CitizenshipCountry" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="SecondaryCitizenshipCountry" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="ResidenceCountry" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="ResidencePermitType" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="ResidencePermitNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ResidencyPermitExpireDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationTotalPoints" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ApplicationCriteriaPoints" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ApplicationExtraPoints" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="IsTuitionFeeRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Birthday" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="BirthPlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DateModified" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ApplicationStatus" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="IsFullLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="StudyForm" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CandidateEducations" type="{http://sais2.x-road.ee/producer/}ArrayOfCandidateEducation" minOccurs="0"/&gt;
 *         &lt;element name="CandidateStateExams" type="{http://sais2.x-road.ee/producer/}ArrayOfCandidateStateExam" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationFormData" type="{http://sais2.x-road.ee/producer/}ArrayOfApplicationFormData" minOccurs="0"/&gt;
 *         &lt;element name="CandidateIntLangTests" type="{http://sais2.x-road.ee/producer/}ArrayOfCandidateIntTest" minOccurs="0"/&gt;
 *         &lt;element name="CandidateAddresses" type="{http://sais2.x-road.ee/producer/}ArrayOfCandidateAddress" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Application", propOrder = {
    "id",
    "admissionId",
    "admissionCode",
    "showNameInRanking",
    "firstName",
    "lastName",
    "sexClassification",
    "idCode",
    "otherIdNumber",
    "importApplicationId",
    "importAppInstitutionRegNr",
    "isImported",
    "citizenshipCountry",
    "secondaryCitizenshipCountry",
    "residenceCountry",
    "residencePermitType",
    "residencePermitNr",
    "residencyPermitExpireDate",
    "applicationTotalPoints",
    "applicationCriteriaPoints",
    "applicationExtraPoints",
    "isTuitionFeeRequired",
    "birthday",
    "birthPlace",
    "email",
    "phone",
    "dateModified",
    "applicationStatus",
    "isFullLoad",
    "studyForm",
    "applicationNumber",
    "candidateEducations",
    "candidateStateExams",
    "applicationFormData",
    "candidateIntLangTests",
    "candidateAddresses"
})
public class Application {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "AdmissionId", required = true)
    protected String admissionId;
    @XmlElement(name = "AdmissionCode")
    protected String admissionCode;
    @XmlElement(name = "ShowNameInRanking", required = true, type = Boolean.class, nillable = true)
    protected Boolean showNameInRanking;
    @XmlElement(name = "FirstName")
    protected String firstName;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "SexClassification")
    protected SAISClassification sexClassification;
    @XmlElement(name = "IdCode")
    protected String idCode;
    @XmlElement(name = "OtherIdNumber")
    protected String otherIdNumber;
    @XmlElement(name = "ImportApplicationId")
    protected String importApplicationId;
    @XmlElement(name = "ImportAppInstitutionRegNr", required = true, type = Integer.class, nillable = true)
    protected Integer importAppInstitutionRegNr;
    @XmlElement(name = "IsImported")
    protected boolean isImported;
    @XmlElement(name = "CitizenshipCountry")
    protected SAISClassification citizenshipCountry;
    @XmlElement(name = "SecondaryCitizenshipCountry")
    protected SAISClassification secondaryCitizenshipCountry;
    @XmlElement(name = "ResidenceCountry")
    protected SAISClassification residenceCountry;
    @XmlElement(name = "ResidencePermitType")
    protected SAISClassification residencePermitType;
    @XmlElement(name = "ResidencePermitNr")
    protected String residencePermitNr;
    @XmlElement(name = "ResidencyPermitExpireDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar residencyPermitExpireDate;
    @XmlElement(name = "ApplicationTotalPoints", required = true, nillable = true)
    protected BigDecimal applicationTotalPoints;
    @XmlElement(name = "ApplicationCriteriaPoints", required = true, nillable = true)
    protected BigDecimal applicationCriteriaPoints;
    @XmlElement(name = "ApplicationExtraPoints", required = true, nillable = true)
    protected BigDecimal applicationExtraPoints;
    @XmlElement(name = "IsTuitionFeeRequired")
    protected boolean isTuitionFeeRequired;
    @XmlElement(name = "Birthday", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar birthday;
    @XmlElement(name = "BirthPlace")
    protected String birthPlace;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "Phone")
    protected String phone;
    @XmlElement(name = "DateModified", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateModified;
    @XmlElement(name = "ApplicationStatus")
    protected SAISClassification applicationStatus;
    @XmlElement(name = "IsFullLoad", required = true, type = Boolean.class, nillable = true)
    protected Boolean isFullLoad;
    @XmlElement(name = "StudyForm")
    protected SAISClassification studyForm;
    @XmlElement(name = "ApplicationNumber")
    protected String applicationNumber;
    @XmlElement(name = "CandidateEducations")
    protected ArrayOfCandidateEducation candidateEducations;
    @XmlElement(name = "CandidateStateExams")
    protected ArrayOfCandidateStateExam candidateStateExams;
    @XmlElement(name = "ApplicationFormData")
    protected ArrayOfApplicationFormData applicationFormData;
    @XmlElement(name = "CandidateIntLangTests")
    protected ArrayOfCandidateIntTest candidateIntLangTests;
    @XmlElement(name = "CandidateAddresses")
    protected ArrayOfCandidateAddress candidateAddresses;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     * Gets the value of the admissionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdmissionCode() {
        return admissionCode;
    }

    /**
     * Sets the value of the admissionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdmissionCode(String value) {
        this.admissionCode = value;
    }

    /**
     * Gets the value of the showNameInRanking property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShowNameInRanking() {
        return showNameInRanking;
    }

    /**
     * Sets the value of the showNameInRanking property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShowNameInRanking(Boolean value) {
        this.showNameInRanking = value;
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
     * Gets the value of the sexClassification property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getSexClassification() {
        return sexClassification;
    }

    /**
     * Sets the value of the sexClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setSexClassification(SAISClassification value) {
        this.sexClassification = value;
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
     * Gets the value of the otherIdNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherIdNumber() {
        return otherIdNumber;
    }

    /**
     * Sets the value of the otherIdNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherIdNumber(String value) {
        this.otherIdNumber = value;
    }

    /**
     * Gets the value of the importApplicationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportApplicationId() {
        return importApplicationId;
    }

    /**
     * Sets the value of the importApplicationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportApplicationId(String value) {
        this.importApplicationId = value;
    }

    /**
     * Gets the value of the importAppInstitutionRegNr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getImportAppInstitutionRegNr() {
        return importAppInstitutionRegNr;
    }

    /**
     * Sets the value of the importAppInstitutionRegNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setImportAppInstitutionRegNr(Integer value) {
        this.importAppInstitutionRegNr = value;
    }

    /**
     * Gets the value of the isImported property.
     * 
     */
    public boolean isIsImported() {
        return isImported;
    }

    /**
     * Sets the value of the isImported property.
     * 
     */
    public void setIsImported(boolean value) {
        this.isImported = value;
    }

    /**
     * Gets the value of the citizenshipCountry property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getCitizenshipCountry() {
        return citizenshipCountry;
    }

    /**
     * Sets the value of the citizenshipCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setCitizenshipCountry(SAISClassification value) {
        this.citizenshipCountry = value;
    }

    /**
     * Gets the value of the secondaryCitizenshipCountry property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getSecondaryCitizenshipCountry() {
        return secondaryCitizenshipCountry;
    }

    /**
     * Sets the value of the secondaryCitizenshipCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setSecondaryCitizenshipCountry(SAISClassification value) {
        this.secondaryCitizenshipCountry = value;
    }

    /**
     * Gets the value of the residenceCountry property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getResidenceCountry() {
        return residenceCountry;
    }

    /**
     * Sets the value of the residenceCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setResidenceCountry(SAISClassification value) {
        this.residenceCountry = value;
    }

    /**
     * Gets the value of the residencePermitType property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getResidencePermitType() {
        return residencePermitType;
    }

    /**
     * Sets the value of the residencePermitType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setResidencePermitType(SAISClassification value) {
        this.residencePermitType = value;
    }

    /**
     * Gets the value of the residencePermitNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidencePermitNr() {
        return residencePermitNr;
    }

    /**
     * Sets the value of the residencePermitNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidencePermitNr(String value) {
        this.residencePermitNr = value;
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
     * Gets the value of the applicationTotalPoints property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getApplicationTotalPoints() {
        return applicationTotalPoints;
    }

    /**
     * Sets the value of the applicationTotalPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setApplicationTotalPoints(BigDecimal value) {
        this.applicationTotalPoints = value;
    }

    /**
     * Gets the value of the applicationCriteriaPoints property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getApplicationCriteriaPoints() {
        return applicationCriteriaPoints;
    }

    /**
     * Sets the value of the applicationCriteriaPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setApplicationCriteriaPoints(BigDecimal value) {
        this.applicationCriteriaPoints = value;
    }

    /**
     * Gets the value of the applicationExtraPoints property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getApplicationExtraPoints() {
        return applicationExtraPoints;
    }

    /**
     * Sets the value of the applicationExtraPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setApplicationExtraPoints(BigDecimal value) {
        this.applicationExtraPoints = value;
    }

    /**
     * Gets the value of the isTuitionFeeRequired property.
     * 
     */
    public boolean isIsTuitionFeeRequired() {
        return isTuitionFeeRequired;
    }

    /**
     * Sets the value of the isTuitionFeeRequired property.
     * 
     */
    public void setIsTuitionFeeRequired(boolean value) {
        this.isTuitionFeeRequired = value;
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
     * Gets the value of the birthPlace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * Sets the value of the birthPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthPlace(String value) {
        this.birthPlace = value;
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
     * Gets the value of the dateModified property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateModified() {
        return dateModified;
    }

    /**
     * Sets the value of the dateModified property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateModified(XMLGregorianCalendar value) {
        this.dateModified = value;
    }

    /**
     * Gets the value of the applicationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * Sets the value of the applicationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setApplicationStatus(SAISClassification value) {
        this.applicationStatus = value;
    }

    /**
     * Gets the value of the isFullLoad property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFullLoad() {
        return isFullLoad;
    }

    /**
     * Sets the value of the isFullLoad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFullLoad(Boolean value) {
        this.isFullLoad = value;
    }

    /**
     * Gets the value of the studyForm property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getStudyForm() {
        return studyForm;
    }

    /**
     * Sets the value of the studyForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setStudyForm(SAISClassification value) {
        this.studyForm = value;
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
     * Gets the value of the candidateEducations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCandidateEducation }
     *     
     */
    public ArrayOfCandidateEducation getCandidateEducations() {
        return candidateEducations;
    }

    /**
     * Sets the value of the candidateEducations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCandidateEducation }
     *     
     */
    public void setCandidateEducations(ArrayOfCandidateEducation value) {
        this.candidateEducations = value;
    }

    /**
     * Gets the value of the candidateStateExams property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCandidateStateExam }
     *     
     */
    public ArrayOfCandidateStateExam getCandidateStateExams() {
        return candidateStateExams;
    }

    /**
     * Sets the value of the candidateStateExams property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCandidateStateExam }
     *     
     */
    public void setCandidateStateExams(ArrayOfCandidateStateExam value) {
        this.candidateStateExams = value;
    }

    /**
     * Gets the value of the applicationFormData property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicationFormData }
     *     
     */
    public ArrayOfApplicationFormData getApplicationFormData() {
        return applicationFormData;
    }

    /**
     * Sets the value of the applicationFormData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicationFormData }
     *     
     */
    public void setApplicationFormData(ArrayOfApplicationFormData value) {
        this.applicationFormData = value;
    }

    /**
     * Gets the value of the candidateIntLangTests property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCandidateIntTest }
     *     
     */
    public ArrayOfCandidateIntTest getCandidateIntLangTests() {
        return candidateIntLangTests;
    }

    /**
     * Sets the value of the candidateIntLangTests property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCandidateIntTest }
     *     
     */
    public void setCandidateIntLangTests(ArrayOfCandidateIntTest value) {
        this.candidateIntLangTests = value;
    }

    /**
     * Gets the value of the candidateAddresses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCandidateAddress }
     *     
     */
    public ArrayOfCandidateAddress getCandidateAddresses() {
        return candidateAddresses;
    }

    /**
     * Sets the value of the candidateAddresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCandidateAddress }
     *     
     */
    public void setCandidateAddresses(ArrayOfCandidateAddress value) {
        this.candidateAddresses = value;
    }

}
