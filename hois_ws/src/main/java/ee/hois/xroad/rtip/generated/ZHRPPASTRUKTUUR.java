
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZHR_PPA_STRUKTUUR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_STRUKTUUR"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OBJEKTI_ID" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="OBJEKTI_TYYP" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PIKK_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="100"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PIKK_ENG_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="100"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PERSONALI_ALA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PERSONALI_ALLYKSUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KUULUB" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMETIKOHA_TUNNUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="3"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMETIKOHA_TUNNUSE_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="25"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ZZTOOLIIN" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="50"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ZZVALDKOND" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="50"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AADRESS" type="{http://rtk-v6.x-road.eu}ZHR_PPA_STRUKTUUR_AADRESS" minOccurs="0"/&gt;
 *         &lt;element name="YKSUSE_JUHT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZHR_PPA_STRUKTUUR", propOrder = {
    "objektiid",
    "objektityyp",
    "tekst",
    "pikktekst",
    "pikkengtekst",
    "personaliala",
    "personaliallyksus",
    "kuulub",
    "ametikohatunnus",
    "ametikohatunnusetekst",
    "zztooliin",
    "zzvaldkond",
    "aadress",
    "yksusejuht",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRPPASTRUKTUUR {

    @XmlElement(name = "OBJEKTI_ID")
    protected String objektiid;
    @XmlElement(name = "OBJEKTI_TYYP")
    protected String objektityyp;
    @XmlElement(name = "TEKST")
    protected String tekst;
    @XmlElement(name = "PIKK_TEKST")
    protected String pikktekst;
    @XmlElement(name = "PIKK_ENG_TEKST")
    protected String pikkengtekst;
    @XmlElement(name = "PERSONALI_ALA")
    protected String personaliala;
    @XmlElement(name = "PERSONALI_ALLYKSUS")
    protected String personaliallyksus;
    @XmlElement(name = "KUULUB")
    protected String kuulub;
    @XmlElement(name = "AMETIKOHA_TUNNUS")
    protected String ametikohatunnus;
    @XmlElement(name = "AMETIKOHA_TUNNUSE_TEKST")
    protected String ametikohatunnusetekst;
    @XmlElement(name = "ZZTOOLIIN")
    protected String zztooliin;
    @XmlElement(name = "ZZVALDKOND")
    protected String zzvaldkond;
    @XmlElement(name = "AADRESS")
    protected ZHRPPASTRUKTUURAADRESS aadress;
    @XmlElement(name = "YKSUSE_JUHT")
    protected String yksusejuht;
    @XmlElement(name = "KEHTIV_ALATES", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivkuni;

    /**
     * Gets the value of the objektiid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOBJEKTIID() {
        return objektiid;
    }

    /**
     * Sets the value of the objektiid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOBJEKTIID(String value) {
        this.objektiid = value;
    }

    /**
     * Gets the value of the objektityyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOBJEKTITYYP() {
        return objektityyp;
    }

    /**
     * Sets the value of the objektityyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOBJEKTITYYP(String value) {
        this.objektityyp = value;
    }

    /**
     * Gets the value of the tekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEKST() {
        return tekst;
    }

    /**
     * Sets the value of the tekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEKST(String value) {
        this.tekst = value;
    }

    /**
     * Gets the value of the pikktekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPIKKTEKST() {
        return pikktekst;
    }

    /**
     * Sets the value of the pikktekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPIKKTEKST(String value) {
        this.pikktekst = value;
    }

    /**
     * Gets the value of the pikkengtekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPIKKENGTEKST() {
        return pikkengtekst;
    }

    /**
     * Sets the value of the pikkengtekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPIKKENGTEKST(String value) {
        this.pikkengtekst = value;
    }

    /**
     * Gets the value of the personaliala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSONALIALA() {
        return personaliala;
    }

    /**
     * Sets the value of the personaliala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSONALIALA(String value) {
        this.personaliala = value;
    }

    /**
     * Gets the value of the personaliallyksus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSONALIALLYKSUS() {
        return personaliallyksus;
    }

    /**
     * Sets the value of the personaliallyksus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSONALIALLYKSUS(String value) {
        this.personaliallyksus = value;
    }

    /**
     * Gets the value of the kuulub property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUULUB() {
        return kuulub;
    }

    /**
     * Sets the value of the kuulub property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUULUB(String value) {
        this.kuulub = value;
    }

    /**
     * Gets the value of the ametikohatunnus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMETIKOHATUNNUS() {
        return ametikohatunnus;
    }

    /**
     * Sets the value of the ametikohatunnus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMETIKOHATUNNUS(String value) {
        this.ametikohatunnus = value;
    }

    /**
     * Gets the value of the ametikohatunnusetekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMETIKOHATUNNUSETEKST() {
        return ametikohatunnusetekst;
    }

    /**
     * Sets the value of the ametikohatunnusetekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMETIKOHATUNNUSETEKST(String value) {
        this.ametikohatunnusetekst = value;
    }

    /**
     * Gets the value of the zztooliin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZZTOOLIIN() {
        return zztooliin;
    }

    /**
     * Sets the value of the zztooliin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZZTOOLIIN(String value) {
        this.zztooliin = value;
    }

    /**
     * Gets the value of the zzvaldkond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZZVALDKOND() {
        return zzvaldkond;
    }

    /**
     * Sets the value of the zzvaldkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZZVALDKOND(String value) {
        this.zzvaldkond = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPASTRUKTUURAADRESS }
     *     
     */
    public ZHRPPASTRUKTUURAADRESS getAADRESS() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPASTRUKTUURAADRESS }
     *     
     */
    public void setAADRESS(ZHRPPASTRUKTUURAADRESS value) {
        this.aadress = value;
    }

    /**
     * Gets the value of the yksusejuht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYKSUSEJUHT() {
        return yksusejuht;
    }

    /**
     * Sets the value of the yksusejuht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYKSUSEJUHT(String value) {
        this.yksusejuht = value;
    }

    /**
     * Gets the value of the kehtivalates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKEHTIVALATES() {
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
    public void setKEHTIVALATES(LocalDate value) {
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
    public LocalDate getKEHTIVKUNI() {
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
    public void setKEHTIVKUNI(LocalDate value) {
        this.kehtivkuni = value;
    }

}
