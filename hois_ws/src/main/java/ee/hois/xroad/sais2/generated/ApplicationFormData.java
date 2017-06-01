
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ApplicationFormData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationFormData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DateModified" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="FieldName" type="{http://sais2.x-road.ee/producer/}ArrayOfKvp" minOccurs="0"/&gt;
 *         &lt;element name="FieldCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SelectedOptions" type="{http://sais2.x-road.ee/producer/}ArrayOfFormFieldOption" minOccurs="0"/&gt;
 *         &lt;element name="FieldType" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationFormData", propOrder = {
    "dateModified",
    "fieldName",
    "fieldCode",
    "value",
    "selectedOptions",
    "fieldType"
})
public class ApplicationFormData {

    @XmlElement(name = "DateModified", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateModified;
    @XmlElement(name = "FieldName")
    protected ArrayOfKvp fieldName;
    @XmlElement(name = "FieldCode")
    protected String fieldCode;
    @XmlElement(name = "Value")
    protected String value;
    @XmlElement(name = "SelectedOptions")
    protected ArrayOfFormFieldOption selectedOptions;
    @XmlElement(name = "FieldType")
    protected int fieldType;

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
     * Gets the value of the fieldName property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKvp }
     *     
     */
    public ArrayOfKvp getFieldName() {
        return fieldName;
    }

    /**
     * Sets the value of the fieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKvp }
     *     
     */
    public void setFieldName(ArrayOfKvp value) {
        this.fieldName = value;
    }

    /**
     * Gets the value of the fieldCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * Sets the value of the fieldCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldCode(String value) {
        this.fieldCode = value;
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
     * Gets the value of the selectedOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFormFieldOption }
     *     
     */
    public ArrayOfFormFieldOption getSelectedOptions() {
        return selectedOptions;
    }

    /**
     * Sets the value of the selectedOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFormFieldOption }
     *     
     */
    public void setSelectedOptions(ArrayOfFormFieldOption value) {
        this.selectedOptions = value;
    }

    /**
     * Gets the value of the fieldType property.
     * 
     */
    public int getFieldType() {
        return fieldType;
    }

    /**
     * Sets the value of the fieldType property.
     * 
     */
    public void setFieldType(int value) {
        this.fieldType = value;
    }

}
