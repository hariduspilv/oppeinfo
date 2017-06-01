
package ee.hois.xroad.sais2.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CandidateIntTestResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CandidateIntTestResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Result" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="MaxResult" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LevelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LevelMin" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LevelMax" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateIntTestResult", propOrder = {
    "name",
    "result",
    "maxResult",
    "levelCode",
    "levelMin",
    "levelMax"
})
public class CandidateIntTestResult {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Result", required = true, nillable = true)
    protected BigDecimal result;
    @XmlElement(name = "MaxResult", required = true, nillable = true)
    protected BigDecimal maxResult;
    @XmlElement(name = "LevelCode")
    protected String levelCode;
    @XmlElement(name = "LevelMin", required = true, nillable = true)
    protected BigDecimal levelMin;
    @XmlElement(name = "LevelMax", required = true, nillable = true)
    protected BigDecimal levelMax;

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

}
