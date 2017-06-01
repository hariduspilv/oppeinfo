
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CandidateStateExam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CandidateStateExam"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StateExamClassification" type="{http://sais2.x-road.ee/producer/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="DateModified" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Result" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="JointPosition" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReplacedResult" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LanguageSkillLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="JointPartResult" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="JointPartPosition" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CertificateNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CertificateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StateExamCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsImported" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Course" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateStateExam", propOrder = {
    "stateExamClassification",
    "dateModified",
    "result",
    "jointPosition",
    "replacedResult",
    "languageSkillLevel",
    "jointPartResult",
    "jointPartPosition",
    "certificateNumber",
    "certificateDate",
    "stateExamCode",
    "isImported",
    "course",
    "language",
    "languageCode"
})
public class CandidateStateExam {

    @XmlElement(name = "StateExamClassification")
    protected SAISClassification stateExamClassification;
    @XmlElement(name = "DateModified", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateModified;
    @XmlElement(name = "Result")
    protected int result;
    @XmlElement(name = "JointPosition", required = true, type = Integer.class, nillable = true)
    protected Integer jointPosition;
    @XmlElement(name = "ReplacedResult", required = true, type = Integer.class, nillable = true)
    protected Integer replacedResult;
    @XmlElement(name = "LanguageSkillLevel")
    protected String languageSkillLevel;
    @XmlElement(name = "JointPartResult", required = true, type = Integer.class, nillable = true)
    protected Integer jointPartResult;
    @XmlElement(name = "JointPartPosition", required = true, type = Integer.class, nillable = true)
    protected Integer jointPartPosition;
    @XmlElement(name = "CertificateNumber")
    protected String certificateNumber;
    @XmlElement(name = "CertificateDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar certificateDate;
    @XmlElement(name = "StateExamCode")
    protected String stateExamCode;
    @XmlElement(name = "IsImported", required = true, type = Boolean.class, nillable = true)
    protected Boolean isImported;
    @XmlElement(name = "Course")
    protected String course;
    @XmlElement(name = "Language")
    protected String language;
    @XmlElement(name = "LanguageCode")
    protected String languageCode;

    /**
     * Gets the value of the stateExamClassification property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getStateExamClassification() {
        return stateExamClassification;
    }

    /**
     * Sets the value of the stateExamClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setStateExamClassification(SAISClassification value) {
        this.stateExamClassification = value;
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
     * Gets the value of the result property.
     * 
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     */
    public void setResult(int value) {
        this.result = value;
    }

    /**
     * Gets the value of the jointPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getJointPosition() {
        return jointPosition;
    }

    /**
     * Sets the value of the jointPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setJointPosition(Integer value) {
        this.jointPosition = value;
    }

    /**
     * Gets the value of the replacedResult property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReplacedResult() {
        return replacedResult;
    }

    /**
     * Sets the value of the replacedResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReplacedResult(Integer value) {
        this.replacedResult = value;
    }

    /**
     * Gets the value of the languageSkillLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageSkillLevel() {
        return languageSkillLevel;
    }

    /**
     * Sets the value of the languageSkillLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageSkillLevel(String value) {
        this.languageSkillLevel = value;
    }

    /**
     * Gets the value of the jointPartResult property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getJointPartResult() {
        return jointPartResult;
    }

    /**
     * Sets the value of the jointPartResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setJointPartResult(Integer value) {
        this.jointPartResult = value;
    }

    /**
     * Gets the value of the jointPartPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getJointPartPosition() {
        return jointPartPosition;
    }

    /**
     * Sets the value of the jointPartPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setJointPartPosition(Integer value) {
        this.jointPartPosition = value;
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
     * Gets the value of the stateExamCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateExamCode() {
        return stateExamCode;
    }

    /**
     * Sets the value of the stateExamCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateExamCode(String value) {
        this.stateExamCode = value;
    }

    /**
     * Gets the value of the isImported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsImported() {
        return isImported;
    }

    /**
     * Sets the value of the isImported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsImported(Boolean value) {
        this.isImported = value;
    }

    /**
     * Gets the value of the course property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourse() {
        return course;
    }

    /**
     * Sets the value of the course property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourse(String value) {
        this.course = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

}
