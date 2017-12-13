
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZHR_ISIK_9005 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_ISIK_9005"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="YKSUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="150"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ALLYKSUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="150"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMETIKOHT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="150"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MARKUS" minOccurs="0"&gt;
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
@XmlType(name = "ZHR_ISIK_9005", propOrder = {
    "yksus",
    "allyksus",
    "ametikoht",
    "markus",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRISIK9005 {

    @XmlElement(name = "YKSUS")
    protected String yksus;
    @XmlElement(name = "ALLYKSUS")
    protected String allyksus;
    @XmlElement(name = "AMETIKOHT")
    protected String ametikoht;
    @XmlElement(name = "MARKUS")
    protected String markus;
    @XmlElement(name = "MUUDETUD")
    protected String muudetud;
    @XmlElement(name = "KEHTIV_ALATES")
    protected String kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI")
    protected String kehtivkuni;

    /**
     * Gets the value of the yksus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYKSUS() {
        return yksus;
    }

    /**
     * Sets the value of the yksus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYKSUS(String value) {
        this.yksus = value;
    }

    /**
     * Gets the value of the allyksus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALLYKSUS() {
        return allyksus;
    }

    /**
     * Sets the value of the allyksus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALLYKSUS(String value) {
        this.allyksus = value;
    }

    /**
     * Gets the value of the ametikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMETIKOHT() {
        return ametikoht;
    }

    /**
     * Sets the value of the ametikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMETIKOHT(String value) {
        this.ametikoht = value;
    }

    /**
     * Gets the value of the markus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARKUS() {
        return markus;
    }

    /**
     * Sets the value of the markus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARKUS(String value) {
        this.markus = value;
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
