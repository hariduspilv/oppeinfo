
package ee.hois.xroad.sais2.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CandidateIntTest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CandidateIntTest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CertificateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LanguageLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Result" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ResultType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MaxResult" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LevelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LevelMin" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LevelMax" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="InternationalTestName" type="{http://sais2.x-road.eu/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="CandidateIntTestResults" type="{http://sais2.x-road.eu/}ArrayOfCandidateIntTestResult" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateIntTest", propOrder = {
    "name",
    "code",
    "certificateName",
    "languageLevel",
    "date",
    "result",
    "resultType",
    "maxResult",
    "levelCode",
    "levelMin",
    "levelMax",
    "internationalTestName",
    "candidateIntTestResults"
})
public class CandidateIntTest {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "CertificateName")
    protected String certificateName;
    @XmlElement(name = "LanguageLevel")
    protected String languageLevel;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "Result", required = true, nillable = true)
    protected BigDecimal result;
    @XmlElement(name = "ResultType")
    protected String resultType;
    @XmlElement(name = "MaxResult", required = true, nillable = true)
    protected BigDecimal maxResult;
    @XmlElement(name = "LevelCode")
    protected String levelCode;
    @XmlElement(name = "LevelMin", required = true, nillable = true)
    protected BigDecimal levelMin;
    @XmlElement(name = "LevelMax", required = true, nillable = true)
    protected BigDecimal levelMax;
    @XmlElement(name = "InternationalTestName")
    protected SAISClassification internationalTestName;
    @XmlElement(name = "CandidateIntTestResults")
    protected ArrayOfCandidateIntTestResult candidateIntTestResults;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
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
     * Gets the value of the certificateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateName() {
        return certificateName;
    }

    /**
     * Sets the value of the certificateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateName(String value) {
        this.certificateName = value;
    }

    /**
     * Gets the value of the languageLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageLevel() {
        return languageLevel;
    }

    /**
     * Sets the value of the languageLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageLevel(String value) {
        this.languageLevel = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setResult(BigDecimal value) {
        this.result = value;
    }

    /**
     * Gets the value of the resultType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * Sets the value of the resultType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultType(String value) {
        this.resultType = value;
    }

    /**
     * Gets the value of the maxResult property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxResult() {
        return maxResult;
    }

    /**
     * Sets the value of the maxResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxResult(BigDecimal value) {
        this.maxResult = value;
    }

    /**
     * Gets the value of the levelCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * Sets the value of the levelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevelCode(String value) {
        this.levelCode = value;
    }

    /**
     * Gets the value of the levelMin property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLevelMin() {
        return levelMin;
    }

    /**
     * Sets the value of the levelMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLevelMin(BigDecimal value) {
        this.levelMin = value;
    }

    /**
     * Gets the value of the levelMax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLevelMax() {
        return levelMax;
    }

    /**
     * Sets the value of the levelMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLevelMax(BigDecimal value) {
        this.levelMax = value;
    }

    /**
     * Gets the value of the internationalTestName property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getInternationalTestName() {
        return internationalTestName;
    }

    /**
     * Sets the value of the internationalTestName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setInternationalTestName(SAISClassification value) {
        this.internationalTestName = value;
    }

    /**
     * Gets the value of the candidateIntTestResults property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCandidateIntTestResult }
     *     
     */
    public ArrayOfCandidateIntTestResult getCandidateIntTestResults() {
        return candidateIntTestResults;
    }

    /**
     * Sets the value of the candidateIntTestResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCandidateIntTestResult }
     *     
     */
    public void setCandidateIntTestResults(ArrayOfCandidateIntTestResult value) {
        this.candidateIntTestResults = value;
    }

}
