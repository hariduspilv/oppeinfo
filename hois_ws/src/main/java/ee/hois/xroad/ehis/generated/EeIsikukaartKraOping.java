
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eeIsikukaartKraOping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartKraOping"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="institutionCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="institutionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="institutionTypeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isForeignSchool" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ehisCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="docNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="programmeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="programmeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="accreditTypeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="educationLevelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="educationLevelName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="studyYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="studyLanguageId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="studyTypeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="studyTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartKraOping", propOrder = {
    "institutionCode",
    "institutionName",
    "institutionTypeId",
    "isForeignSchool",
    "ehisCode",
    "startDate",
    "endDate",
    "docNo",
    "programmeCode",
    "programmeName",
    "accreditTypeId",
    "educationLevelId",
    "educationLevelName",
    "studyYear",
    "studyLanguageId",
    "studyTypeId",
    "studyTime"
})
public class EeIsikukaartKraOping {

    protected Integer institutionCode;
    protected String institutionName;
    protected String institutionTypeId;
    protected String isForeignSchool;
    protected String ehisCode;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    protected String docNo;
    protected String programmeCode;
    protected String programmeName;
    protected String accreditTypeId;
    protected String educationLevelId;
    protected String educationLevelName;
    protected String studyYear;
    protected String studyLanguageId;
    protected String studyTypeId;
    protected Integer studyTime;

    /**
     * Gets the value of the institutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstitutionCode() {
        return institutionCode;
    }

    /**
     * Sets the value of the institutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstitutionCode(Integer value) {
        this.institutionCode = value;
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
     * Gets the value of the institutionTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitutionTypeId() {
        return institutionTypeId;
    }

    /**
     * Sets the value of the institutionTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitutionTypeId(String value) {
        this.institutionTypeId = value;
    }

    /**
     * Gets the value of the isForeignSchool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsForeignSchool() {
        return isForeignSchool;
    }

    /**
     * Sets the value of the isForeignSchool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsForeignSchool(String value) {
        this.isForeignSchool = value;
    }

    /**
     * Gets the value of the ehisCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEhisCode() {
        return ehisCode;
    }

    /**
     * Sets the value of the ehisCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEhisCode(String value) {
        this.ehisCode = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the docNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNo() {
        return docNo;
    }

    /**
     * Sets the value of the docNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNo(String value) {
        this.docNo = value;
    }

    /**
     * Gets the value of the programmeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrammeCode() {
        return programmeCode;
    }

    /**
     * Sets the value of the programmeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrammeCode(String value) {
        this.programmeCode = value;
    }

    /**
     * Gets the value of the programmeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrammeName() {
        return programmeName;
    }

    /**
     * Sets the value of the programmeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrammeName(String value) {
        this.programmeName = value;
    }

    /**
     * Gets the value of the accreditTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccreditTypeId() {
        return accreditTypeId;
    }

    /**
     * Sets the value of the accreditTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccreditTypeId(String value) {
        this.accreditTypeId = value;
    }

    /**
     * Gets the value of the educationLevelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEducationLevelId() {
        return educationLevelId;
    }

    /**
     * Sets the value of the educationLevelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEducationLevelId(String value) {
        this.educationLevelId = value;
    }

    /**
     * Gets the value of the educationLevelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEducationLevelName() {
        return educationLevelName;
    }

    /**
     * Sets the value of the educationLevelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEducationLevelName(String value) {
        this.educationLevelName = value;
    }

    /**
     * Gets the value of the studyYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyYear() {
        return studyYear;
    }

    /**
     * Sets the value of the studyYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyYear(String value) {
        this.studyYear = value;
    }

    /**
     * Gets the value of the studyLanguageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyLanguageId() {
        return studyLanguageId;
    }

    /**
     * Sets the value of the studyLanguageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyLanguageId(String value) {
        this.studyLanguageId = value;
    }

    /**
     * Gets the value of the studyTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyTypeId() {
        return studyTypeId;
    }

    /**
     * Sets the value of the studyTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyTypeId(String value) {
        this.studyTypeId = value;
    }

    /**
     * Gets the value of the studyTime property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStudyTime() {
        return studyTime;
    }

    /**
     * Sets the value of the studyTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStudyTime(Integer value) {
        this.studyTime = value;
    }

}
