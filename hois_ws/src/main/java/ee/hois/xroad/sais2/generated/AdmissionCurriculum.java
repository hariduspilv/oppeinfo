
package ee.hois.xroad.sais2.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdmissionCurriculum complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdmissionCurriculum"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="Name" type="{http://sais2.x-road.eu/}ArrayOfKvp" minOccurs="0"/&gt;
 *         &lt;element name="EHISCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdmissionCurriculum", propOrder = {
    "id",
    "name",
    "ehisCode"
})
public class AdmissionCurriculum {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "Name")
    protected ArrayOfKvp name;
    @XmlElement(name = "EHISCode")
    protected int ehisCode;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKvp }
     *     
     */
    public ArrayOfKvp getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKvp }
     *     
     */
    public void setName(ArrayOfKvp value) {
        this.name = value;
    }

    /**
     * Gets the value of the ehisCode property.
     * 
     */
    public int getEHISCode() {
        return ehisCode;
    }

    /**
     * Sets the value of the ehisCode property.
     * 
     */
    public void setEHISCode(int value) {
        this.ehisCode = value;
    }

}
