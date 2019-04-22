
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR48isikPRIARequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR48isikPRIARequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikusynnikp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikueesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikumaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuvald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR48isikPRIARequestType", propOrder = {
    "isikukood",
    "isikusynnikp",
    "isikuperenimi",
    "isikueesnimi",
    "isikumaakond",
    "isikuvald"
})
public class RR48IsikPRIARequestType {

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Isikusynnikp", required = true)
    protected String isikusynnikp;
    @XmlElement(name = "Isikuperenimi", required = true)
    protected String isikuperenimi;
    @XmlElement(name = "Isikueesnimi", required = true)
    protected String isikueesnimi;
    @XmlElement(name = "Isikumaakond", required = true)
    protected String isikumaakond;
    @XmlElement(name = "Isikuvald", required = true)
    protected String isikuvald;

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
     * Gets the value of the isikusynnikp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikusynnikp() {
        return isikusynnikp;
    }

    /**
     * Sets the value of the isikusynnikp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikusynnikp(String value) {
        this.isikusynnikp = value;
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
     * Gets the value of the isikumaakond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikumaakond() {
        return isikumaakond;
    }

    /**
     * Sets the value of the isikumaakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikumaakond(String value) {
        this.isikumaakond = value;
    }

    /**
     * Gets the value of the isikuvald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuvald() {
        return isikuvald;
    }

    /**
     * Sets the value of the isikuvald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuvald(String value) {
        this.isikuvald = value;
    }

}
