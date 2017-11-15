
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AppStatusExportRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppStatusExportRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicationIdList" type="{http://sais2.x-road.eu/}ArrayOfGuid" minOccurs="0"/&gt;
 *         &lt;element name="StatusChangeDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StatusChangeDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ApplicationStatusValues" type="{http://sais2.x-road.eu/}ArrayOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppStatusExportRequest", propOrder = {
    "applicationIdList",
    "statusChangeDateFrom",
    "statusChangeDateTo",
    "applicationStatusValues"
})
public class AppStatusExportRequest {

    @XmlElement(name = "ApplicationIdList")
    protected ArrayOfGuid applicationIdList;
    @XmlElement(name = "StatusChangeDateFrom", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar statusChangeDateFrom;
    @XmlElement(name = "StatusChangeDateTo", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar statusChangeDateTo;
    @XmlElement(name = "ApplicationStatusValues")
    protected ArrayOfString applicationStatusValues;

    /**
     * Gets the value of the applicationIdList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGuid }
     *     
     */
    public ArrayOfGuid getApplicationIdList() {
        return applicationIdList;
    }

    /**
     * Sets the value of the applicationIdList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGuid }
     *     
     */
    public void setApplicationIdList(ArrayOfGuid value) {
        this.applicationIdList = value;
    }

    /**
     * Gets the value of the statusChangeDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStatusChangeDateFrom() {
        return statusChangeDateFrom;
    }

    /**
     * Sets the value of the statusChangeDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStatusChangeDateFrom(XMLGregorianCalendar value) {
        this.statusChangeDateFrom = value;
    }

    /**
     * Gets the value of the statusChangeDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStatusChangeDateTo() {
        return statusChangeDateTo;
    }

    /**
     * Sets the value of the statusChangeDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStatusChangeDateTo(XMLGregorianCalendar value) {
        this.statusChangeDateTo = value;
    }

    /**
     * Gets the value of the applicationStatusValues property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getApplicationStatusValues() {
        return applicationStatusValues;
    }

    /**
     * Sets the value of the applicationStatusValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setApplicationStatusValues(ArrayOfString value) {
        this.applicationStatusValues = value;
    }

}
