
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Admission complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Admission"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="DateCreated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DateModified" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Name" type="{http://sais2.x-road.eu/}ArrayOfKvp" minOccurs="0"/&gt;
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RootInstitutionId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="RootInstitutionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InstitutionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InstitutionId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="AdmissionPeriodId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="AdmissionPeriodStart" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="AdmissionPeriodEnd" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EHISStudyLevel" type="{http://sais2.x-road.eu/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="StudyForms" type="{http://sais2.x-road.eu/}ArrayOfSAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="CurriculumLanguages" type="{http://sais2.x-road.eu/}ArrayOfSAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="Curriculums" type="{http://sais2.x-road.eu/}ArrayOfAdmissionCurriculum" minOccurs="0"/&gt;
 *         &lt;element name="StudyLocationCountry" type="{http://sais2.x-road.eu/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="StudyLocationCity" type="{http://sais2.x-road.eu/}ArrayOfKvp" minOccurs="0"/&gt;
 *         &lt;element name="AdmissionTuitions" type="{http://sais2.x-road.eu/}ArrayOfAdmissionTuition" minOccurs="0"/&gt;
 *         &lt;element name="IsPartialLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsFullLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsUndefinedLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AdmissionCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Admission", propOrder = {
    "id",
    "dateCreated",
    "dateModified",
    "name",
    "code",
    "rootInstitutionId",
    "rootInstitutionCode",
    "institutionCode",
    "institutionId",
    "admissionPeriodId",
    "admissionPeriodStart",
    "admissionPeriodEnd",
    "ehisStudyLevel",
    "studyForms",
    "curriculumLanguages",
    "curriculums",
    "studyLocationCountry",
    "studyLocationCity",
    "admissionTuitions",
    "isPartialLoad",
    "isFullLoad",
    "isUndefinedLoad",
    "admissionCount"
})
public class Admission {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "DateCreated", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreated;
    @XmlElement(name = "DateModified", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateModified;
    @XmlElement(name = "Name")
    protected ArrayOfKvp name;
    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "RootInstitutionId", required = true, nillable = true)
    protected String rootInstitutionId;
    @XmlElement(name = "RootInstitutionCode")
    protected String rootInstitutionCode;
    @XmlElement(name = "InstitutionCode")
    protected String institutionCode;
    @XmlElement(name = "InstitutionId", required = true)
    protected String institutionId;
    @XmlElement(name = "AdmissionPeriodId", required = true, nillable = true)
    protected String admissionPeriodId;
    @XmlElement(name = "AdmissionPeriodStart", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar admissionPeriodStart;
    @XmlElement(name = "AdmissionPeriodEnd", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar admissionPeriodEnd;
    @XmlElement(name = "EHISStudyLevel")
    protected SAISClassification ehisStudyLevel;
    @XmlElement(name = "StudyForms")
    protected ArrayOfSAISClassification studyForms;
    @XmlElement(name = "CurriculumLanguages")
    protected ArrayOfSAISClassification curriculumLanguages;
    @XmlElement(name = "Curriculums")
    protected ArrayOfAdmissionCurriculum curriculums;
    @XmlElement(name = "StudyLocationCountry")
    protected SAISClassification studyLocationCountry;
    @XmlElement(name = "StudyLocationCity")
    protected ArrayOfKvp studyLocationCity;
    @XmlElement(name = "AdmissionTuitions")
    protected ArrayOfAdmissionTuition admissionTuitions;
    @XmlElement(name = "IsPartialLoad")
    protected boolean isPartialLoad;
    @XmlElement(name = "IsFullLoad")
    protected boolean isFullLoad;
    @XmlElement(name = "IsUndefinedLoad")
    protected boolean isUndefinedLoad;
    @XmlElement(name = "AdmissionCount", required = true, type = Integer.class, nillable = true)
    protected Integer admissionCount;

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
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCreated(XMLGregorianCalendar value) {
        this.dateCreated = value;
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKvp }
     *     
     */
    public ArrayOfKvp getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKvp }
     *     
     */
    public void setName(ArrayOfKvp value) {
        this.name = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the rootInstitutionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootInstitutionId() {
        return rootInstitutionId;
    }

    /**
     * Sets the value of the rootInstitutionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootInstitutionId(String value) {
        this.rootInstitutionId = value;
    }

    /**
     * Gets the value of the rootInstitutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootInstitutionCode() {
        return rootInstitutionCode;
    }

    /**
     * Sets the value of the rootInstitutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootInstitutionCode(String value) {
        this.rootInstitutionCode = value;
    }

    /**
     * Gets the value of the institutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitutionCode() {
        return institutionCode;
    }

    /**
     * Sets the value of the institutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitutionCode(String value) {
        this.institutionCode = value;
    }

    /**
     * Gets the value of the institutionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitutionId() {
        return institutionId;
    }

    /**
     * Sets the value of the institutionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitutionId(String value) {
        this.institutionId = value;
    }

    /**
     * Gets the value of the admissionPeriodId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdmissionPeriodId() {
        return admissionPeriodId;
    }

    /**
     * Sets the value of the admissionPeriodId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdmissionPeriodId(String value) {
        this.admissionPeriodId = value;
    }

    /**
     * Gets the value of the admissionPeriodStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAdmissionPeriodStart() {
        return admissionPeriodStart;
    }

    /**
     * Sets the value of the admissionPeriodStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAdmissionPeriodStart(XMLGregorianCalendar value) {
        this.admissionPeriodStart = value;
    }

    /**
     * Gets the value of the admissionPeriodEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAdmissionPeriodEnd() {
        return admissionPeriodEnd;
    }

    /**
     * Sets the value of the admissionPeriodEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAdmissionPeriodEnd(XMLGregorianCalendar value) {
        this.admissionPeriodEnd = value;
    }

    /**
     * Gets the value of the ehisStudyLevel property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getEHISStudyLevel() {
        return ehisStudyLevel;
    }

    /**
     * Sets the value of the ehisStudyLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setEHISStudyLevel(SAISClassification value) {
        this.ehisStudyLevel = value;
    }

    /**
     * Gets the value of the studyForms property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSAISClassification }
     *     
     */
    public ArrayOfSAISClassification getStudyForms() {
        return studyForms;
    }

    /**
     * Sets the value of the studyForms property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSAISClassification }
     *     
     */
    public void setStudyForms(ArrayOfSAISClassification value) {
        this.studyForms = value;
    }

    /**
     * Gets the value of the curriculumLanguages property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSAISClassification }
     *     
     */
    public ArrayOfSAISClassification getCurriculumLanguages() {
        return curriculumLanguages;
    }

    /**
     * Sets the value of the curriculumLanguages property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSAISClassification }
     *     
     */
    public void setCurriculumLanguages(ArrayOfSAISClassification value) {
        this.curriculumLanguages = value;
    }

    /**
     * Gets the value of the curriculums property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdmissionCurriculum }
     *     
     */
    public ArrayOfAdmissionCurriculum getCurriculums() {
        return curriculums;
    }

    /**
     * Sets the value of the curriculums property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdmissionCurriculum }
     *     
     */
    public void setCurriculums(ArrayOfAdmissionCurriculum value) {
        this.curriculums = value;
    }

    /**
     * Gets the value of the studyLocationCountry property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getStudyLocationCountry() {
        return studyLocationCountry;
    }

    /**
     * Sets the value of the studyLocationCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setStudyLocationCountry(SAISClassification value) {
        this.studyLocationCountry = value;
    }

    /**
     * Gets the value of the studyLocationCity property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKvp }
     *     
     */
    public ArrayOfKvp getStudyLocationCity() {
        return studyLocationCity;
    }

    /**
     * Sets the value of the studyLocationCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKvp }
     *     
     */
    public void setStudyLocationCity(ArrayOfKvp value) {
        this.studyLocationCity = value;
    }

    /**
     * Gets the value of the admissionTuitions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdmissionTuition }
     *     
     */
    public ArrayOfAdmissionTuition getAdmissionTuitions() {
        return admissionTuitions;
    }

    /**
     * Sets the value of the admissionTuitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdmissionTuition }
     *     
     */
    public void setAdmissionTuitions(ArrayOfAdmissionTuition value) {
        this.admissionTuitions = value;
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
     * Gets the value of the admissionCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAdmissionCount() {
        return admissionCount;
    }

    /**
     * Sets the value of the admissionCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAdmissionCount(Integer value) {
        this.admissionCount = value;
    }

}
