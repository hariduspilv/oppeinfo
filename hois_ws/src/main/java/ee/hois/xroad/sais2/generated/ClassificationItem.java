
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClassificationItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClassificationItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="ClassificationTypeId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Weight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsImported" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Translation" type="{http://sais2.x-road.eu/}ArrayOfKvp" minOccurs="0"/&gt;
 *         &lt;element name="DescriptionTranslation" type="{http://sais2.x-road.eu/}ArrayOfKvp" minOccurs="0"/&gt;
 *         &lt;element name="Value2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassificationItem", propOrder = {
    "id",
    "classificationTypeId",
    "value",
    "description",
    "weight",
    "isActive",
    "isImported",
    "name",
    "translation",
    "descriptionTranslation",
    "value2"
})
public class ClassificationItem {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "ClassificationTypeId", required = true)
    protected String classificationTypeId;
    @XmlElement(name = "Value")
    protected String value;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Weight", required = true, type = Integer.class, nillable = true)
    protected Integer weight;
    @XmlElement(name = "IsActive")
    protected boolean isActive;
    @XmlElement(name = "IsImported")
    protected boolean isImported;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Translation")
    protected ArrayOfKvp translation;
    @XmlElement(name = "DescriptionTranslation")
    protected ArrayOfKvp descriptionTranslation;
    @XmlElement(name = "Value2")
    protected String value2;

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
     * Gets the value of the classificationTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificationTypeId() {
        return classificationTypeId;
    }

    /**
     * Sets the value of the classificationTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassificationTypeId(String value) {
        this.classificationTypeId = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWeight(Integer value) {
        this.weight = value;
    }

    /**
     * Gets the value of the isActive property.
     * 
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     */
    public void setIsActive(boolean value) {
        this.isActive = value;
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
     * Gets the value of the translation property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKvp }
     *     
     */
    public ArrayOfKvp getTranslation() {
        return translation;
    }

    /**
     * Sets the value of the translation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKvp }
     *     
     */
    public void setTranslation(ArrayOfKvp value) {
        this.translation = value;
    }

    /**
     * Gets the value of the descriptionTranslation property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKvp }
     *     
     */
    public ArrayOfKvp getDescriptionTranslation() {
        return descriptionTranslation;
    }

    /**
     * Sets the value of the descriptionTranslation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKvp }
     *     
     */
    public void setDescriptionTranslation(ArrayOfKvp value) {
        this.descriptionTranslation = value;
    }

    /**
     * Gets the value of the value2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue2() {
        return value2;
    }

    /**
     * Sets the value of the value2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue2(String value) {
        this.value2 = value;
    }

}
