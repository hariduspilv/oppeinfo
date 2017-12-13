
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for isikuandmedRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isikuandmedRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="ISIKUKOOD"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="11"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ISIK_POHIANDMED_X" type="{http://rtk-v6.x-road.eu}ZHR_BAPI_ISIK_POHIANDMED_X" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *         &lt;element name="PERSONALI_NR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isikuandmedRequestType", propOrder = {

})
public class IsikuandmedRequestType {

    @XmlElement(name = "ISIKUKOOD", required = true)
    protected String isikukood;
    @XmlElement(name = "ISIK_POHIANDMED_X")
    protected ZHRBAPIISIKPOHIANDMEDX isikpohiandmedx;
    @XmlElement(name = "KEHTIV_ALATES")
    protected String kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI")
    protected String kehtivkuni;
    @XmlElement(name = "PERSONALI_NR")
    protected String personalinr;

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
     * Gets the value of the isikpohiandmedx property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRBAPIISIKPOHIANDMEDX }
     *     
     */
    public ZHRBAPIISIKPOHIANDMEDX getISIKPOHIANDMEDX() {
        return isikpohiandmedx;
    }

    /**
     * Sets the value of the isikpohiandmedx property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRBAPIISIKPOHIANDMEDX }
     *     
     */
    public void setISIKPOHIANDMEDX(ZHRBAPIISIKPOHIANDMEDX value) {
        this.isikpohiandmedx = value;
    }

    /**
     * Gets the value of the kehtivalates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEHTIVALATES() {
        return kehtivalates;
    }

    /**
     * Sets the value of the kehtivalates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEHTIVALATES(String value) {
        this.kehtivalates = value;
    }

    /**
     * Gets the value of the kehtivkuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEHTIVKUNI() {
        return kehtivkuni;
    }

    /**
     * Sets the value of the kehtivkuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEHTIVKUNI(String value) {
        this.kehtivkuni = value;
    }

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

}
