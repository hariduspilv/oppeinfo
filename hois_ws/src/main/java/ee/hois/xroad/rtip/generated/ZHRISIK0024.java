
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZHR_ISIK_0024 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_ISIK_0024"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KEELEOSKUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="OBJEKT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="45"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="OBJEKTI_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TASE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TASEME_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
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
@XmlType(name = "ZHR_ISIK_0024", propOrder = {
    "keeleoskus",
    "objekt",
    "objektitekst",
    "tase",
    "tasemetekst",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRISIK0024 {

    @XmlElement(name = "KEELEOSKUS")
    protected String keeleoskus;
    @XmlElement(name = "OBJEKT")
    protected String objekt;
    @XmlElement(name = "OBJEKTI_TEKST")
    protected String objektitekst;
    @XmlElement(name = "TASE")
    protected String tase;
    @XmlElement(name = "TASEME_TEKST")
    protected String tasemetekst;
    @XmlElement(name = "MUUDETUD")
    protected String muudetud;
    @XmlElement(name = "KEHTIV_ALATES")
    protected String kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI")
    protected String kehtivkuni;

    /**
     * Gets the value of the keeleoskus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEELEOSKUS() {
        return keeleoskus;
    }

    /**
     * Sets the value of the keeleoskus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEELEOSKUS(String value) {
        this.keeleoskus = value;
    }

    /**
     * Gets the value of the objekt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOBJEKT() {
        return objekt;
    }

    /**
     * Sets the value of the objekt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOBJEKT(String value) {
        this.objekt = value;
    }

    /**
     * Gets the value of the objektitekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOBJEKTITEKST() {
        return objektitekst;
    }

    /**
     * Sets the value of the objektitekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOBJEKTITEKST(String value) {
        this.objektitekst = value;
    }

    /**
     * Gets the value of the tase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASE() {
        return tase;
    }

    /**
     * Sets the value of the tase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASE(String value) {
        this.tase = value;
    }

    /**
     * Gets the value of the tasemetekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASEMETEKST() {
        return tasemetekst;
    }

    /**
     * Sets the value of the tasemetekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASEMETEKST(String value) {
        this.tasemetekst = value;
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
