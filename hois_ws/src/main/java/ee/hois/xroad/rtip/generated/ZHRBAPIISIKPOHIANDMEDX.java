
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZHR_BAPI_ISIK_POHIANDMED_X complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_BAPI_ISIK_POHIANDMED_X"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="X_TOOTAJA_POHIANDMED_9005" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="X_TOOTAJA_POHIANDMED_0001" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="X_TOOTAJA_POHIANDMED_0002" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="X_TOOTAJA_POHIANDMED_0552" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="X_TOOTAJA_POHIANDMED_0022" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="X_TOOTAJA_POHIANDMED_9006" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="X_OSKUSED_0024" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZHR_BAPI_ISIK_POHIANDMED_X", propOrder = {
    "xtootajapohiandmed9005",
    "xtootajapohiandmed0001",
    "xtootajapohiandmed0002",
    "xtootajapohiandmed0552",
    "xtootajapohiandmed0022",
    "xtootajapohiandmed9006",
    "xoskused0024"
})
public class ZHRBAPIISIKPOHIANDMEDX {

    @XmlElement(name = "X_TOOTAJA_POHIANDMED_9005")
    protected String xtootajapohiandmed9005;
    @XmlElement(name = "X_TOOTAJA_POHIANDMED_0001")
    protected String xtootajapohiandmed0001;
    @XmlElement(name = "X_TOOTAJA_POHIANDMED_0002")
    protected String xtootajapohiandmed0002;
    @XmlElement(name = "X_TOOTAJA_POHIANDMED_0552")
    protected String xtootajapohiandmed0552;
    @XmlElement(name = "X_TOOTAJA_POHIANDMED_0022")
    protected String xtootajapohiandmed0022;
    @XmlElement(name = "X_TOOTAJA_POHIANDMED_9006")
    protected String xtootajapohiandmed9006;
    @XmlElement(name = "X_OSKUSED_0024")
    protected String xoskused0024;

    /**
     * Gets the value of the xtootajapohiandmed9005 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXTOOTAJAPOHIANDMED9005() {
        return xtootajapohiandmed9005;
    }

    /**
     * Sets the value of the xtootajapohiandmed9005 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXTOOTAJAPOHIANDMED9005(String value) {
        this.xtootajapohiandmed9005 = value;
    }

    /**
     * Gets the value of the xtootajapohiandmed0001 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXTOOTAJAPOHIANDMED0001() {
        return xtootajapohiandmed0001;
    }

    /**
     * Sets the value of the xtootajapohiandmed0001 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXTOOTAJAPOHIANDMED0001(String value) {
        this.xtootajapohiandmed0001 = value;
    }

    /**
     * Gets the value of the xtootajapohiandmed0002 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXTOOTAJAPOHIANDMED0002() {
        return xtootajapohiandmed0002;
    }

    /**
     * Sets the value of the xtootajapohiandmed0002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXTOOTAJAPOHIANDMED0002(String value) {
        this.xtootajapohiandmed0002 = value;
    }

    /**
     * Gets the value of the xtootajapohiandmed0552 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXTOOTAJAPOHIANDMED0552() {
        return xtootajapohiandmed0552;
    }

    /**
     * Sets the value of the xtootajapohiandmed0552 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXTOOTAJAPOHIANDMED0552(String value) {
        this.xtootajapohiandmed0552 = value;
    }

    /**
     * Gets the value of the xtootajapohiandmed0022 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXTOOTAJAPOHIANDMED0022() {
        return xtootajapohiandmed0022;
    }

    /**
     * Sets the value of the xtootajapohiandmed0022 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXTOOTAJAPOHIANDMED0022(String value) {
        this.xtootajapohiandmed0022 = value;
    }

    /**
     * Gets the value of the xtootajapohiandmed9006 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXTOOTAJAPOHIANDMED9006() {
        return xtootajapohiandmed9006;
    }

    /**
     * Sets the value of the xtootajapohiandmed9006 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXTOOTAJAPOHIANDMED9006(String value) {
        this.xtootajapohiandmed9006 = value;
    }

    /**
     * Gets the value of the xoskused0024 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXOSKUSED0024() {
        return xoskused0024;
    }

    /**
     * Sets the value of the xoskused0024 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXOSKUSED0024(String value) {
        this.xoskused0024 = value;
    }

}
