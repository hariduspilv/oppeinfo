
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRriikIdRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRriikIdRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RiigiId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRriikIdRequestType", propOrder = {
    "riigiKood",
    "riigiId"
})
public class RRriikIdRequestType {

    @XmlElement(name = "RiigiKood")
    protected String riigiKood;
    @XmlElement(name = "RiigiId")
    protected String riigiId;

    /**
     * Gets the value of the riigiKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiigiKood() {
        return riigiKood;
    }

    /**
     * Sets the value of the riigiKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiigiKood(String value) {
        this.riigiKood = value;
    }

    /**
     * Gets the value of the riigiId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiigiId() {
        return riigiId;
    }

    /**
     * Sets the value of the riigiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiigiId(String value) {
        this.riigiId = value;
    }

}
