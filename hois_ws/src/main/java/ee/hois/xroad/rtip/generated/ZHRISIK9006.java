
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZHR_ISIK_9006 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_ISIK_9006"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LAUATELEFONI_NR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="21"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="LAUATELEFONI_LYHINR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MOBIILI_NR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="25"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MOBIILI_LYHINR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="EMAIL" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="50"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TEENINDUSKOHT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="150"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZHR_ISIK_9006", propOrder = {
    "lauatelefoninr",
    "lauatelefonilyhinr",
    "mobiilinr",
    "mobiililyhinr",
    "email",
    "teeninduskoht",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRISIK9006 {

    @XmlElement(name = "LAUATELEFONI_NR")
    protected String lauatelefoninr;
    @XmlElement(name = "LAUATELEFONI_LYHINR")
    protected String lauatelefonilyhinr;
    @XmlElement(name = "MOBIILI_NR")
    protected String mobiilinr;
    @XmlElement(name = "MOBIILI_LYHINR")
    protected String mobiililyhinr;
    @XmlElement(name = "EMAIL")
    protected String email;
    @XmlElement(name = "TEENINDUSKOHT")
    protected String teeninduskoht;
    @XmlElement(name = "MUUDETUD")
    protected String muudetud;
    @XmlElement(name = "KEHTIV_ALATES")
    protected String kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI")
    protected String kehtivkuni;

    /**
     * Gets the value of the lauatelefoninr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLAUATELEFONINR() {
        return lauatelefoninr;
    }

    /**
     * Sets the value of the lauatelefoninr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLAUATELEFONINR(String value) {
        this.lauatelefoninr = value;
    }

    /**
     * Gets the value of the lauatelefonilyhinr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLAUATELEFONILYHINR() {
        return lauatelefonilyhinr;
    }

    /**
     * Sets the value of the lauatelefonilyhinr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLAUATELEFONILYHINR(String value) {
        this.lauatelefonilyhinr = value;
    }

    /**
     * Gets the value of the mobiilinr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOBIILINR() {
        return mobiilinr;
    }

    /**
     * Sets the value of the mobiilinr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOBIILINR(String value) {
        this.mobiilinr = value;
    }

    /**
     * Gets the value of the mobiililyhinr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOBIILILYHINR() {
        return mobiililyhinr;
    }

    /**
     * Sets the value of the mobiililyhinr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOBIILILYHINR(String value) {
        this.mobiililyhinr = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAIL() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the teeninduskoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEENINDUSKOHT() {
        return teeninduskoht;
    }

    /**
     * Sets the value of the teeninduskoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEENINDUSKOHT(String value) {
        this.teeninduskoht = value;
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

}
