
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CandidateEducationItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CandidateEducationItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InstitutionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InstitutionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InstitutionCountryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StudyEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EhisStudyLevelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StudyLanguageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CommendationCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="FinancingModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CertificateNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CertificateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StudyStatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UseForAdmission" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateEducationItem", propOrder = {
    "institutionName",
    "institutionCode",
    "institutionCountryCode",
    "studyBeginDate",
    "studyEndDate",
    "ehisStudyLevelCode",
    "studyLanguageCode",
    "commendationCode",
    "financingModeCode",
    "certificateNumber",
    "certificateDate",
    "studyStatusCode",
    "useForAdmission"
})
public class CandidateEducationItem {

    @XmlElement(name = "InstitutionName")
    protected String institutionName;
    @XmlElement(name = "InstitutionCode")
    protected String institutionCode;
    @XmlElement(name = "InstitutionCountryCode")
    protected String institutionCountryCode;
    @XmlElement(name = "StudyBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar studyBeginDate;
    @XmlElement(name = "StudyEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar studyEndDate;
    @XmlElement(name = "EhisStudyLevelCode")
    protected String ehisStudyLevelCode;
    @XmlElement(name = "StudyLanguageCode")
    protected String studyLanguageCode;
    @XmlElement(name = "CommendationCode", required = true, type = Integer.class, nillable = true)
    protected Integer commendationCode;
    @XmlElement(name = "FinancingModeCode")
    protected String financingModeCode;
    @XmlElement(name = "CertificateNumber")
    protected String certificateNumber;
    @XmlElement(name = "CertificateDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar certificateDate;
    @XmlElement(name = "StudyStatusCode")
    protected String studyStatusCode;
    @XmlElement(name = "UseForAdmission")
    protected boolean useForAdmission;

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
     * Gets the value of the institutionCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitutionCountryCode() {
        return institutionCountryCode;
    }

    /**
     * Sets the value of the institutionCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitutionCountryCode(String value) {
        this.institutionCountryCode = value;
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
     * Gets the value of the ehisStudyLevelCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEhisStudyLevelCode() {
        return ehisStudyLevelCode;
    }

    /**
     * Sets the value of the ehisStudyLevelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEhisStudyLevelCode(String value) {
        this.ehisStudyLevelCode = value;
    }

    /**
     * Gets the value of the studyLanguageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyLanguageCode() {
        return studyLanguageCode;
    }

    /**
     * Sets the value of the studyLanguageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyLanguageCode(String value) {
        this.studyLanguageCode = value;
    }

    /**
     * Gets the value of the commendationCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCommendationCode() {
        return commendationCode;
    }

    /**
     * Sets the value of the commendationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCommendationCode(Integer value) {
        this.commendationCode = value;
    }

    /**
     * Gets the value of the financingModeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinancingModeCode() {
        return financingModeCode;
    }

    /**
     * Sets the value of the financingModeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinancingModeCode(String value) {
        this.financingModeCode = value;
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
     * Gets the value of the studyStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyStatusCode() {
        return studyStatusCode;
    }

    /**
     * Sets the value of the studyStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyStatusCode(String value) {
        this.studyStatusCode = value;
    }

    /**
     * Gets the value of the useForAdmission property.
     * 
     */
    public boolean isUseForAdmission() {
        return useForAdmission;
    }

    /**
     * Sets the value of the useForAdmission property.
     * 
     */
    public void setUseForAdmission(boolean value) {
        this.useForAdmission = value;
    }

}
