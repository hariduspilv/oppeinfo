
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CandidateGrade complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CandidateGrade"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CurriculumClassification" type="{http://sais2.x-road.eu/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="DateModified" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CurriculumCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Grade" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SpecificationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Specification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="RawGrade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReplacedResult" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsImported" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateGrade", propOrder = {
    "curriculumClassification",
    "dateModified",
    "curriculumCode",
    "grade",
    "specificationCode",
    "specification",
    "isRequired",
    "rawGrade",
    "replacedResult",
    "isImported"
})
public class CandidateGrade {

    @XmlElement(name = "CurriculumClassification")
    protected SAISClassification curriculumClassification;
    @XmlElement(name = "DateModified", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateModified;
    @XmlElement(name = "CurriculumCode")
    protected String curriculumCode;
    @XmlElement(name = "Grade", required = true, type = Integer.class, nillable = true)
    protected Integer grade;
    @XmlElement(name = "SpecificationCode")
    protected String specificationCode;
    @XmlElement(name = "Specification")
    protected String specification;
    @XmlElement(name = "IsRequired")
    protected boolean isRequired;
    @XmlElement(name = "RawGrade")
    protected String rawGrade;
    @XmlElement(name = "ReplacedResult", required = true, type = Integer.class, nillable = true)
    protected Integer replacedResult;
    @XmlElement(name = "IsImported", required = true, type = Boolean.class, nillable = true)
    protected Boolean isImported;

    /**
     * Gets the value of the curriculumClassification property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getCurriculumClassification() {
        return curriculumClassification;
    }

    /**
     * Sets the value of the curriculumClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setCurriculumClassification(SAISClassification value) {
        this.curriculumClassification = value;
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

    /**
     * Gets the value of the grade property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * Sets the value of the grade property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGrade(Integer value) {
        this.grade = value;
    }

    /**
     * Gets the value of the specificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecificationCode() {
        return specificationCode;
    }

    /**
     * Sets the value of the specificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecificationCode(String value) {
        this.specificationCode = value;
    }

    /**
     * Gets the value of the specification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * Sets the value of the specification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecification(String value) {
        this.specification = value;
    }

    /**
     * Gets the value of the isRequired property.
     * 
     */
    public boolean isIsRequired() {
        return isRequired;
    }

    /**
     * Sets the value of the isRequired property.
     * 
     */
    public void setIsRequired(boolean value) {
        this.isRequired = value;
    }

    /**
     * Gets the value of the rawGrade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRawGrade() {
        return rawGrade;
    }

    /**
     * Sets the value of the rawGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRawGrade(String value) {
        this.rawGrade = value;
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

}
