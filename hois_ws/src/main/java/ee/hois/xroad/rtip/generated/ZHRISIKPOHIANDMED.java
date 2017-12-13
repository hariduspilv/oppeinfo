
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZHR_ISIK_POHIANDMED complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_ISIK_POHIANDMED"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PERSONALI_NR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ISIKUKOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="11"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *         &lt;element name="ISIK_POHIANDMED_9005" type="{http://rtk-v6.x-road.eu}ZHR_T_ISIK_9005" minOccurs="0"/&gt;
 *         &lt;element name="ISIK_POHIANDMED_0001" type="{http://rtk-v6.x-road.eu}ZHR_T_ISIK_0001" minOccurs="0"/&gt;
 *         &lt;element name="ISIK_POHIANDMED_0002" type="{http://rtk-v6.x-road.eu}ZHR_T_ISIK_0002" minOccurs="0"/&gt;
 *         &lt;element name="ISIK_POHIANDMED_0552" type="{http://rtk-v6.x-road.eu}ZHR_T_ISIK_0552" minOccurs="0"/&gt;
 *         &lt;element name="ISIK_POHIANDMED_0022" type="{http://rtk-v6.x-road.eu}ZHR_T_ISIK_0022" minOccurs="0"/&gt;
 *         &lt;element name="ISIK_POHIANDMED_9006" type="{http://rtk-v6.x-road.eu}ZHR_T_ISIK_9006" minOccurs="0"/&gt;
 *         &lt;element name="OSKUSED_0024" type="{http://rtk-v6.x-road.eu}ZHR_T_ISIK_0024" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZHR_ISIK_POHIANDMED", propOrder = {
    "personalinr",
    "isikukood",
    "muudetud",
    "isikpohiandmed9005",
    "isikpohiandmed0001",
    "isikpohiandmed0002",
    "isikpohiandmed0552",
    "isikpohiandmed0022",
    "isikpohiandmed9006",
    "oskused0024"
})
public class ZHRISIKPOHIANDMED {

    @XmlElement(name = "PERSONALI_NR")
    protected String personalinr;
    @XmlElement(name = "ISIKUKOOD")
    protected String isikukood;
    @XmlElement(name = "MUUDETUD")
    protected String muudetud;
    @XmlElement(name = "ISIK_POHIANDMED_9005")
    protected ZHRTISIK9005 isikpohiandmed9005;
    @XmlElement(name = "ISIK_POHIANDMED_0001")
    protected ZHRTISIK0001 isikpohiandmed0001;
    @XmlElement(name = "ISIK_POHIANDMED_0002")
    protected ZHRTISIK0002 isikpohiandmed0002;
    @XmlElement(name = "ISIK_POHIANDMED_0552")
    protected ZHRTISIK0552 isikpohiandmed0552;
    @XmlElement(name = "ISIK_POHIANDMED_0022")
    protected ZHRTISIK0022 isikpohiandmed0022;
    @XmlElement(name = "ISIK_POHIANDMED_9006")
    protected ZHRTISIK9006 isikpohiandmed9006;
    @XmlElement(name = "OSKUSED_0024")
    protected ZHRTISIK0024 oskused0024;

    /**
     * Gets the value of the personalinr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSONALINR() {
        return personalinr;
    }

    /**
     * Sets the value of the personalinr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSONALINR(String value) {
        this.personalinr = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISIKUKOOD() {
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
    public void setISIKUKOOD(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the muudetud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUUDETUD() {
        return muudetud;
    }

    /**
     * Sets the value of the muudetud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUUDETUD(String value) {
        this.muudetud = value;
    }

    /**
     * Gets the value of the isikpohiandmed9005 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRTISIK9005 }
     *     
     */
    public ZHRTISIK9005 getISIKPOHIANDMED9005() {
        return isikpohiandmed9005;
    }

    /**
     * Sets the value of the isikpohiandmed9005 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRTISIK9005 }
     *     
     */
    public void setISIKPOHIANDMED9005(ZHRTISIK9005 value) {
        this.isikpohiandmed9005 = value;
    }

    /**
     * Gets the value of the isikpohiandmed0001 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRTISIK0001 }
     *     
     */
    public ZHRTISIK0001 getISIKPOHIANDMED0001() {
        return isikpohiandmed0001;
    }

    /**
     * Sets the value of the isikpohiandmed0001 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRTISIK0001 }
     *     
     */
    public void setISIKPOHIANDMED0001(ZHRTISIK0001 value) {
        this.isikpohiandmed0001 = value;
    }

    /**
     * Gets the value of the isikpohiandmed0002 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRTISIK0002 }
     *     
     */
    public ZHRTISIK0002 getISIKPOHIANDMED0002() {
        return isikpohiandmed0002;
    }

    /**
     * Sets the value of the isikpohiandmed0002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRTISIK0002 }
     *     
     */
    public void setISIKPOHIANDMED0002(ZHRTISIK0002 value) {
        this.isikpohiandmed0002 = value;
    }

    /**
     * Gets the value of the isikpohiandmed0552 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRTISIK0552 }
     *     
     */
    public ZHRTISIK0552 getISIKPOHIANDMED0552() {
        return isikpohiandmed0552;
    }

    /**
     * Sets the value of the isikpohiandmed0552 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRTISIK0552 }
     *     
     */
    public void setISIKPOHIANDMED0552(ZHRTISIK0552 value) {
        this.isikpohiandmed0552 = value;
    }

    /**
     * Gets the value of the isikpohiandmed0022 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRTISIK0022 }
     *     
     */
    public ZHRTISIK0022 getISIKPOHIANDMED0022() {
        return isikpohiandmed0022;
    }

    /**
     * Sets the value of the isikpohiandmed0022 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRTISIK0022 }
     *     
     */
    public void setISIKPOHIANDMED0022(ZHRTISIK0022 value) {
        this.isikpohiandmed0022 = value;
    }

    /**
     * Gets the value of the isikpohiandmed9006 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRTISIK9006 }
     *     
     */
    public ZHRTISIK9006 getISIKPOHIANDMED9006() {
        return isikpohiandmed9006;
    }

    /**
     * Sets the value of the isikpohiandmed9006 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRTISIK9006 }
     *     
     */
    public void setISIKPOHIANDMED9006(ZHRTISIK9006 value) {
        this.isikpohiandmed9006 = value;
    }

    /**
     * Gets the value of the oskused0024 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRTISIK0024 }
     *     
     */
    public ZHRTISIK0024 getOSKUSED0024() {
        return oskused0024;
    }

    /**
     * Sets the value of the oskused0024 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRTISIK0024 }
     *     
     */
    public void setOSKUSED0024(ZHRTISIK0024 value) {
        this.oskused0024 = value;
    }

}
