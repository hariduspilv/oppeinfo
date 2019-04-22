
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR96IsikDokElukSuheRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR96IsikDokElukSuheRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikueesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VastusteArv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR96IsikDokElukSuheRequestType", propOrder = {
    "isikukood",
    "isikueesnimi",
    "isikuperenimi",
    "vastusteArv"
})
public class RR96IsikDokElukSuheRequestType {

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Isikueesnimi", required = true)
    protected String isikueesnimi;
    @XmlElement(name = "Isikuperenimi", required = true)
    protected String isikuperenimi;
    @XmlElement(name = "VastusteArv", required = true)
    protected String vastusteArv;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the isikueesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikueesnimi() {
        return isikueesnimi;
    }

    /**
     * Sets the value of the isikueesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikueesnimi(String value) {
        this.isikueesnimi = value;
    }

    /**
     * Gets the value of the isikuperenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuperenimi() {
        return isikuperenimi;
    }

    /**
     * Sets the value of the isikuperenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuperenimi(String value) {
        this.isikuperenimi = value;
    }

    /**
     * Gets the value of the vastusteArv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastusteArv() {
        return vastusteArv;
    }

    /**
     * Sets the value of the vastusteArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastusteArv(String value) {
        this.vastusteArv = value;
    }

}
