
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR404_isikRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR404_isikRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cValjad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cIsikukoodid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR404_isikRequestType", propOrder = {
    "cValjad",
    "cIsikukoodid"
})
public class RR404IsikRequestType {

    protected String cValjad;
    @XmlElement(required = true)
    protected String cIsikukoodid;

    /**
     * Gets the value of the cValjad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCValjad() {
        return cValjad;
    }

    /**
     * Sets the value of the cValjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCValjad(String value) {
        this.cValjad = value;
    }

    /**
     * Gets the value of the cIsikukoodid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIsikukoodid() {
        return cIsikukoodid;
    }

    /**
     * Sets the value of the cIsikukoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIsikukoodid(String value) {
        this.cIsikukoodid = value;
    }

}
