
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CandidateEducation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CandidateEducation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InstitutionRegNr" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="InstitutionCountry" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="DateModified" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EhisLevel" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="InstitutionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyFieldCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyFieldName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyField" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyFieldGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CertificateNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ProjectedStudyEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StudyEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StudyCancelDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StudyNominalTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyStatus" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="StudyForm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyFormClassification" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="Commendation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CommendationClassification" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="FinancingMode" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="StudyLanguage" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="InstitutionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="GraduatedAbroad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CertificateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DateSynced" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CandidateGrades" type="{http://sais2.x-road.ee/producer/}ArrayOfCandidateGrade" minOccurs="0"/&gt;
 *         &lt;element name="CurriculumName" type="{http://sais2.x-road.ee/producer/}ArrayOfKvp" minOccurs="0"/&gt;
 *         &lt;element name="CurriculumCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateEducation", propOrder = {
    "institutionRegNr",
    "institutionCountry",
    "dateModified",
    "ehisLevel",
    "institutionName",
    "studyFieldCode",
    "studyFieldName",
    "studyField",
    "studyFieldGroup",
    "certificateNumber",
    "studyBeginDate",
    "projectedStudyEndDate",
    "studyEndDate",
    "studyCancelDate",
    "studyNominalTime",
    "studyStatus",
    "studyForm",
    "studyFormClassification",
    "commendation",
    "commendationClassification",
    "financingMode",
    "studyLanguage",
    "institutionCode",
    "graduatedAbroad",
    "certificateDate",
    "dateSynced",
    "candidateGrades",
    "curriculumName",
    "curriculumCode"
})
public class CandidateEducation {

    @XmlElement(name = "InstitutionRegNr", required = true, type = Integer.class, nillable = true)
    protected Integer institutionRegNr;
    @XmlElement(name = "InstitutionCountry")
    protected SAISClassification institutionCountry;
    @XmlElement(name = "DateModified", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateModified;
    @XmlElement(name = "EhisLevel")
    protected SAISClassification ehisLevel;
    @XmlElement(name = "InstitutionName")
    protected String institutionName;
    @XmlElement(name = "StudyFieldCode")
    protected String studyFieldCode;
    @XmlElement(name = "StudyFieldName")
    protected String studyFieldName;
    @XmlElement(name = "StudyField")
    protected String studyField;
    @XmlElement(name = "StudyFieldGroup")
    protected String studyFieldGroup;
    @XmlElement(name = "CertificateNumber")
    protected String certificateNumber;
    @XmlElement(name = "StudyBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar studyBeginDate;
    @XmlElement(name = "ProjectedStudyEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar projectedStudyEndDate;
    @XmlElement(name = "StudyEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar studyEndDate;
    @XmlElement(name = "StudyCancelDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar studyCancelDate;
    @XmlElement(name = "StudyNominalTime")
    protected String studyNominalTime;
    @XmlElement(name = "StudyStatus")
    protected SAISClassification studyStatus;
    @XmlElement(name = "StudyForm")
    protected String studyForm;
    @XmlElement(name = "StudyFormClassification")
    protected SAISClassification studyFormClassification;
    @XmlElement(name = "Commendation")
    protected String commendation;
    @XmlElement(name = "CommendationClassification")
    protected SAISClassification commendationClassification;
    @XmlElement(name = "FinancingMode")
    protected SAISClassification financingMode;
    @XmlElement(name = "StudyLanguage")
    protected SAISClassification studyLanguage;
    @XmlElement(name = "InstitutionCode")
    protected String institutionCode;
    @XmlElement(name = "GraduatedAbroad")
    protected boolean graduatedAbroad;
    @XmlElement(name = "CertificateDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar certificateDate;
    @XmlElement(name = "DateSynced", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateSynced;
    @XmlElement(name = "CandidateGrades")
    protected ArrayOfCandidateGrade candidateGrades;
    @XmlElement(name = "CurriculumName")
    protected ArrayOfKvp curriculumName;
    @XmlElement(name = "CurriculumCode")
    protected String curriculumCode;

    /**
     * Gets the value of the institutionRegNr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstitutionRegNr() {
        return institutionRegNr;
    }

    /**
     * Sets the value of the institutionRegNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstitutionRegNr(Integer value) {
        this.institutionRegNr = value;
    }

    /**
     * Gets the value of the institutionCountry property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getInstitutionCountry() {
        return institutionCountry;
    }

    /**
     * Sets the value of the institutionCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setInstitutionCountry(SAISClassification value) {
        this.institutionCountry = value;
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
     * Gets the value of the ehisLevel property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getEhisLevel() {
        return ehisLevel;
    }

    /**
     * Sets the value of the ehisLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setEhisLevel(SAISClassification value) {
        this.ehisLevel = value;
    }

    /**
     * Gets the value of the institutionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitutionName() {
        return institutionName;
    }

    /**
     * Sets the value of the institutionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitutionName(String value) {
        this.institutionName = value;
    }

    /**
     * Gets the value of the studyFieldCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyFieldCode() {
        return studyFieldCode;
    }

    /**
     * Sets the value of the studyFieldCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyFieldCode(String value) {
        this.studyFieldCode = value;
    }

    /**
     * Gets the value of the studyFieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyFieldName() {
        return studyFieldName;
    }

    /**
     * Sets the value of the studyFieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyFieldName(String value) {
        this.studyFieldName = value;
    }

    /**
     * Gets the value of the studyField property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyField() {
        return studyField;
    }

    /**
     * Sets the value of the studyField property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyField(String value) {
        this.studyField = value;
    }

    /**
     * Gets the value of the studyFieldGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyFieldGroup() {
        return studyFieldGroup;
    }

    /**
     * Sets the value of the studyFieldGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyFieldGroup(String value) {
        this.studyFieldGroup = value;
    }

    /**
     * Gets the value of the certificateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateNumber() {
        return certificateNumber;
    }

    /**
     * Sets the value of the certificateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateNumber(String value) {
        this.certificateNumber = value;
    }

    /**
     * Gets the value of the studyBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStudyBeginDate() {
        return studyBeginDate;
    }

    /**
     * Sets the value of the studyBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStudyBeginDate(XMLGregorianCalendar value) {
        this.studyBeginDate = value;
    }

    /**
     * Gets the value of the projectedStudyEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProjectedStudyEndDate() {
        return projectedStudyEndDate;
    }

    /**
     * Sets the value of the projectedStudyEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProjectedStudyEndDate(XMLGregorianCalendar value) {
        this.projectedStudyEndDate = value;
    }

    /**
     * Gets the value of the studyEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStudyEndDate() {
        return studyEndDate;
    }

    /**
     * Sets the value of the studyEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStudyEndDate(XMLGregorianCalendar value) {
        this.studyEndDate = value;
    }

    /**
     * Gets the value of the studyCancelDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStudyCancelDate() {
        return studyCancelDate;
    }

    /**
     * Sets the value of the studyCancelDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStudyCancelDate(XMLGregorianCalendar value) {
        this.studyCancelDate = value;
    }

    /**
     * Gets the value of the studyNominalTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyNominalTime() {
        return studyNominalTime;
    }

    /**
     * Sets the value of the studyNominalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyNominalTime(String value) {
        this.studyNominalTime = value;
    }

    /**
     * Gets the value of the studyStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getStudyStatus() {
        return studyStatus;
    }

    /**
     * Sets the value of the studyStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setStudyStatus(SAISClassification value) {
        this.studyStatus = value;
    }

    /**
     * Gets the value of the studyForm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyForm() {
        return studyForm;
    }

    /**
     * Sets the value of the studyForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyForm(String value) {
        this.studyForm = value;
    }

    /**
     * Gets the value of the studyFormClassification property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getStudyFormClassification() {
        return studyFormClassification;
    }

    /**
     * Sets the value of the studyFormClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setStudyFormClassification(SAISClassification value) {
        this.studyFormClassification = value;
    }

    /**
     * Gets the value of the commendation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommendation() {
        return commendation;
    }

    /**
     * Sets the value of the commendation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommendation(String value) {
        this.commendation = value;
    }

    /**
     * Gets the value of the commendationClassification property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getCommendationClassification() {
        return commendationClassification;
    }

    /**
     * Sets the value of the commendationClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setCommendationClassification(SAISClassification value) {
        this.commendationClassification = value;
    }

    /**
     * Gets the value of the financingMode property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getFinancingMode() {
        return financingMode;
    }

    /**
     * Sets the value of the financingMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setFinancingMode(SAISClassification value) {
        this.financingMode = value;
    }

    /**
     * Gets the value of the studyLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getStudyLanguage() {
        return studyLanguage;
    }

    /**
     * Sets the value of the studyLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setStudyLanguage(SAISClassification value) {
        this.studyLanguage = value;
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
     * Gets the value of the graduatedAbroad property.
     * 
     */
    public boolean isGraduatedAbroad() {
        return graduatedAbroad;
    }

    /**
     * Sets the value of the graduatedAbroad property.
     * 
     */
    public void setGraduatedAbroad(boolean value) {
        this.graduatedAbroad = value;
    }

    /**
     * Gets the value of the certificateDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCertificateDate() {
        return certificateDate;
    }

    /**
     * Sets the value of the certificateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCertificateDate(XMLGregorianCalendar value) {
        this.certificateDate = value;
    }

    /**
     * Gets the value of the dateSynced property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateSynced() {
        return dateSynced;
    }

    /**
     * Sets the value of the dateSynced property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateSynced(XMLGregorianCalendar value) {
        this.dateSynced = value;
    }

    /**
     * Gets the value of the candidateGrades property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCandidateGrade }
     *     
     */
    public ArrayOfCandidateGrade getCandidateGrades() {
        return candidateGrades;
    }

    /**
     * Sets the value of the candidateGrades property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCandidateGrade }
     *     
     */
    public void setCandidateGrades(ArrayOfCandidateGrade value) {
        this.candidateGrades = value;
    }

    /**
     * Gets the value of the curriculumName property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKvp }
     *     
     */
    public ArrayOfKvp getCurriculumName() {
        return curriculumName;
    }

    /**
     * Sets the value of the curriculumName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKvp }
     *     
     */
    public void setCurriculumName(ArrayOfKvp value) {
        this.curriculumName = value;
    }

    /**
     * Gets the value of the curriculumCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurriculumCode() {
        return curriculumCode;
    }

    /**
     * Sets the value of the curriculumCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurriculumCode(String value) {
        this.curriculumCode = value;
    }

}
