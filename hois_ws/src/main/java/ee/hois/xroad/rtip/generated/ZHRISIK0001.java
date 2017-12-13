
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZHR_ISIK_0001 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_ISIK_0001"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TOOTAJA_GRUPP" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="GRUPI_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TOOTAJA_ALLGRUPP" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ALLGRUPI_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PERSONALI_ALA_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="60"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ORGANISATSIOONI_YKSUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="120"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMETIKOHT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="120"/&gt;
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
@XmlType(name = "ZHR_ISIK_0001", propOrder = {
    "tootajagrupp",
    "grupitekst",
    "tootajaallgrupp",
    "allgrupitekst",
    "personalialatekst",
    "organisatsiooniyksus",
    "ametikoht",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRISIK0001 {

    @XmlElement(name = "TOOTAJA_GRUPP")
    protected String tootajagrupp;
    @XmlElement(name = "GRUPI_TEKST")
    protected String grupitekst;
    @XmlElement(name = "TOOTAJA_ALLGRUPP")
    protected String tootajaallgrupp;
    @XmlElement(name = "ALLGRUPI_TEKST")
    protected String allgrupitekst;
    @XmlElement(name = "PERSONALI_ALA_TEKST")
    protected String personalialatekst;
    @XmlElement(name = "ORGANISATSIOONI_YKSUS")
    protected String organisatsiooniyksus;
    @XmlElement(name = "AMETIKOHT")
    protected String ametikoht;
    @XmlElement(name = "MUUDETUD")
    protected String muudetud;
    @XmlElement(name = "KEHTIV_ALATES")
    protected String kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI")
    protected String kehtivkuni;

    /**
     * Gets the value of the tootajagrupp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOTAJAGRUPP() {
        return tootajagrupp;
    }

    /**
     * Sets the value of the tootajagrupp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOTAJAGRUPP(String value) {
        this.tootajagrupp = value;
    }

    /**
     * Gets the value of the grupitekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGRUPITEKST() {
        return grupitekst;
    }

    /**
     * Sets the value of the grupitekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGRUPITEKST(String value) {
        this.grupitekst = value;
    }

    /**
     * Gets the value of the tootajaallgrupp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOTAJAALLGRUPP() {
        return tootajaallgrupp;
    }

    /**
     * Sets the value of the tootajaallgrupp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOTAJAALLGRUPP(String value) {
        this.tootajaallgrupp = value;
    }

    /**
     * Gets the value of the allgrupitekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALLGRUPITEKST() {
        return allgrupitekst;
    }

    /**
     * Sets the value of the allgrupitekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALLGRUPITEKST(String value) {
        this.allgrupitekst = value;
    }

    /**
     * Gets the value of the personalialatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSONALIALATEKST() {
        return personalialatekst;
    }

    /**
     * Sets the value of the personalialatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSONALIALATEKST(String value) {
        this.personalialatekst = value;
    }

    /**
     * Gets the value of the organisatsiooniyksus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANISATSIOONIYKSUS() {
        return organisatsiooniyksus;
    }

    /**
     * Sets the value of the organisatsiooniyksus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANISATSIOONIYKSUS(String value) {
        this.organisatsiooniyksus = value;
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
