
package ee.hois.xroad.sais2.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdmissionTuition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdmissionTuition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="IsFree" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Currency" type="{http://sais2.x-road.eu/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="TuitionType" type="{http://sais2.x-road.eu/}SAISClassification" minOccurs="0"/&gt;
 *         &lt;element name="Descripiton" type="{http://sais2.x-road.eu/}ArrayOfKvp" minOccurs="0"/&gt;
 *         &lt;element name="IsFullLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsPartialLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsUndefinedLoad" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdmissionTuition", propOrder = {
    "id",
    "isFree",
    "currency",
    "amount",
    "tuitionType",
    "descripiton",
    "isFullLoad",
    "isPartialLoad",
    "isUndefinedLoad"
})
public class AdmissionTuition {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "IsFree")
    protected boolean isFree;
    @XmlElement(name = "Currency")
    protected SAISClassification currency;
    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "TuitionType")
    protected SAISClassification tuitionType;
    @XmlElement(name = "Descripiton")
    protected ArrayOfKvp descripiton;
    @XmlElement(name = "IsFullLoad")
    protected boolean isFullLoad;
    @XmlElement(name = "IsPartialLoad")
    protected boolean isPartialLoad;
    @XmlElement(name = "IsUndefinedLoad")
    protected boolean isUndefinedLoad;

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
     * Gets the value of the isFree property.
     * 
     */
    public boolean isIsFree() {
        return isFree;
    }

    /**
     * Sets the value of the isFree property.
     * 
     */
    public void setIsFree(boolean value) {
        this.isFree = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setCurrency(SAISClassification value) {
        this.currency = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the tuitionType property.
     * 
     * @return
     *     possible object is
     *     {@link SAISClassification }
     *     
     */
    public SAISClassification getTuitionType() {
        return tuitionType;
    }

    /**
     * Sets the value of the tuitionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAISClassification }
     *     
     */
    public void setTuitionType(SAISClassification value) {
        this.tuitionType = value;
    }

    /**
     * Gets the value of the descripiton property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKvp }
     *     
     */
    public ArrayOfKvp getDescripiton() {
        return descripiton;
    }

    /**
     * Sets the value of the descripiton property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKvp }
     *     
     */
    public void setDescripiton(ArrayOfKvp value) {
        this.descripiton = value;
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

}
