
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AllAdmissionsExportRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AllAdmissionsExportRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InstitutionRegCodes" type="{http://sais2.x-road.ee/producer/}ArrayOfInt" minOccurs="0"/&gt;
 *         &lt;element name="CreateDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CreateDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifyDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifyDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllAdmissionsExportRequest", propOrder = {
    "institutionRegCodes",
    "createDateFrom",
    "createDateTo",
    "modifyDateFrom",
    "modifyDateTo"
})
public class AllAdmissionsExportRequest {

    @XmlElement(name = "InstitutionRegCodes")
    protected ArrayOfInt institutionRegCodes;
    @XmlElement(name = "CreateDateFrom", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDateFrom;
    @XmlElement(name = "CreateDateTo", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDateTo;
    @XmlElement(name = "ModifyDateFrom", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifyDateFrom;
    @XmlElement(name = "ModifyDateTo", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifyDateTo;

    /**
     * Gets the value of the institutionRegCodes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInt }
     *     
     */
    public ArrayOfInt getInstitutionRegCodes() {
        return institutionRegCodes;
    }

    /**
     * Sets the value of the institutionRegCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInt }
     *     
     */
    public void setInstitutionRegCodes(ArrayOfInt value) {
        this.institutionRegCodes = value;
    }

    /**
     * Gets the value of the createDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDateFrom() {
        return createDateFrom;
    }

    /**
     * Sets the value of the createDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDateFrom(XMLGregorianCalendar value) {
        this.createDateFrom = value;
    }

    /**
     * Gets the value of the createDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDateTo() {
        return createDateTo;
    }

    /**
     * Sets the value of the createDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDateTo(XMLGregorianCalendar value) {
        this.createDateTo = value;
    }

    /**
     * Gets the value of the modifyDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifyDateFrom() {
        return modifyDateFrom;
    }

    /**
     * Sets the value of the modifyDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifyDateFrom(XMLGregorianCalendar value) {
        this.modifyDateFrom = value;
    }

    /**
     * Gets the value of the modifyDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifyDateTo() {
        return modifyDateTo;
    }

    /**
     * Sets the value of the modifyDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifyDateTo(XMLGregorianCalendar value) {
        this.modifyDateTo = value;
    }

}
