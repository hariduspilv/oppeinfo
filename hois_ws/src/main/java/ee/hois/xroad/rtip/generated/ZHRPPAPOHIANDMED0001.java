
package ee.hois.xroad.rtip.generated;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZHR_PPA_POHIANDMED_0001 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_POHIANDMED_0001"&gt;
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
 *         &lt;element name="PERSONALI_ALA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PERSONALI_ALA_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="30"/&gt;
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
 *         &lt;element name="PERSONALI_ALLYKSUSE_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="15"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ORGANISATSIOONI_YKSUSE_ID" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMETIKOHA_ID" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ASENDUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ASENDUSE_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="25"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ASENDUSAJAKS_TOOL" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="3"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ASENDUSAJAKS_TOOL_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PALGAARVESTUSE_ALA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PALGAARVESTUSE_ALA_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KOMPANII_KOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KOMPANII_NIMETUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="25"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KOORMUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *               &lt;totalDigits value="5"/&gt;
 *               &lt;fractionDigits value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
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
@XmlType(name = "ZHR_PPA_POHIANDMED_0001", propOrder = {
    "tootajagrupp",
    "grupitekst",
    "tootajaallgrupp",
    "allgrupitekst",
    "personaliala",
    "personalialatekst",
    "personaliallyksus",
    "personaliallyksusetekst",
    "organisatsiooniyksuseid",
    "ametikohaid",
    "asendus",
    "asendusetekst",
    "asendusajakstool",
    "asendusajakstooltekst",
    "palgaarvestuseala",
    "palgaarvestusealatekst",
    "kompaniikood",
    "kompaniinimetus",
    "koormus",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRPPAPOHIANDMED0001 {

    @XmlElement(name = "TOOTAJA_GRUPP")
    protected String tootajagrupp;
    @XmlElement(name = "GRUPI_TEKST")
    protected String grupitekst;
    @XmlElement(name = "TOOTAJA_ALLGRUPP")
    protected String tootajaallgrupp;
    @XmlElement(name = "ALLGRUPI_TEKST")
    protected String allgrupitekst;
    @XmlElement(name = "PERSONALI_ALA")
    protected String personaliala;
    @XmlElement(name = "PERSONALI_ALA_TEKST")
    protected String personalialatekst;
    @XmlElement(name = "PERSONALI_ALLYKSUS")
    protected String personaliallyksus;
    @XmlElement(name = "PERSONALI_ALLYKSUSE_TEKST")
    protected String personaliallyksusetekst;
    @XmlElement(name = "ORGANISATSIOONI_YKSUSE_ID")
    protected String organisatsiooniyksuseid;
    @XmlElement(name = "AMETIKOHA_ID")
    protected String ametikohaid;
    @XmlElement(name = "ASENDUS")
    protected String asendus;
    @XmlElement(name = "ASENDUSE_TEKST")
    protected String asendusetekst;
    @XmlElement(name = "ASENDUSAJAKS_TOOL")
    protected String asendusajakstool;
    @XmlElement(name = "ASENDUSAJAKS_TOOL_TEKST")
    protected String asendusajakstooltekst;
    @XmlElement(name = "PALGAARVESTUSE_ALA")
    protected String palgaarvestuseala;
    @XmlElement(name = "PALGAARVESTUSE_ALA_TEKST")
    protected String palgaarvestusealatekst;
    @XmlElement(name = "KOMPANII_KOOD")
    protected String kompaniikood;
    @XmlElement(name = "KOMPANII_NIMETUS")
    protected String kompaniinimetus;
    @XmlElement(name = "KOORMUS")
    protected BigDecimal koormus;
    @XmlElement(name = "MUUDETUD", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate muudetud;
    @XmlElement(name = "KEHTIV_ALATES", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivkuni;

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
     * Gets the value of the personaliallyksusetekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSONALIALLYKSUSETEKST() {
        return personaliallyksusetekst;
    }

    /**
     * Sets the value of the personaliallyksusetekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSONALIALLYKSUSETEKST(String value) {
        this.personaliallyksusetekst = value;
    }

    /**
     * Gets the value of the organisatsiooniyksuseid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANISATSIOONIYKSUSEID() {
        return organisatsiooniyksuseid;
    }

    /**
     * Sets the value of the organisatsiooniyksuseid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANISATSIOONIYKSUSEID(String value) {
        this.organisatsiooniyksuseid = value;
    }

    /**
     * Gets the value of the ametikohaid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMETIKOHAID() {
        return ametikohaid;
    }

    /**
     * Sets the value of the ametikohaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMETIKOHAID(String value) {
        this.ametikohaid = value;
    }

    /**
     * Gets the value of the asendus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASENDUS() {
        return asendus;
    }

    /**
     * Sets the value of the asendus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASENDUS(String value) {
        this.asendus = value;
    }

    /**
     * Gets the value of the asendusetekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASENDUSETEKST() {
        return asendusetekst;
    }

    /**
     * Sets the value of the asendusetekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASENDUSETEKST(String value) {
        this.asendusetekst = value;
    }

    /**
     * Gets the value of the asendusajakstool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASENDUSAJAKSTOOL() {
        return asendusajakstool;
    }

    /**
     * Sets the value of the asendusajakstool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASENDUSAJAKSTOOL(String value) {
        this.asendusajakstool = value;
    }

    /**
     * Gets the value of the asendusajakstooltekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASENDUSAJAKSTOOLTEKST() {
        return asendusajakstooltekst;
    }

    /**
     * Sets the value of the asendusajakstooltekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASENDUSAJAKSTOOLTEKST(String value) {
        this.asendusajakstooltekst = value;
    }

    /**
     * Gets the value of the palgaarvestuseala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPALGAARVESTUSEALA() {
        return palgaarvestuseala;
    }

    /**
     * Sets the value of the palgaarvestuseala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPALGAARVESTUSEALA(String value) {
        this.palgaarvestuseala = value;
    }

    /**
     * Gets the value of the palgaarvestusealatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPALGAARVESTUSEALATEKST() {
        return palgaarvestusealatekst;
    }

    /**
     * Sets the value of the palgaarvestusealatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPALGAARVESTUSEALATEKST(String value) {
        this.palgaarvestusealatekst = value;
    }

    /**
     * Gets the value of the kompaniikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOMPANIIKOOD() {
        return kompaniikood;
    }

    /**
     * Sets the value of the kompaniikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOMPANIIKOOD(String value) {
        this.kompaniikood = value;
    }

    /**
     * Gets the value of the kompaniinimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOMPANIINIMETUS() {
        return kompaniinimetus;
    }

    /**
     * Sets the value of the kompaniinimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOMPANIINIMETUS(String value) {
        this.kompaniinimetus = value;
    }

    /**
     * Gets the value of the koormus property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKOORMUS() {
        return koormus;
    }

    /**
     * Sets the value of the koormus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKOORMUS(BigDecimal value) {
        this.koormus = value;
    }

    /**
     * Gets the value of the muudetud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getMUUDETUD() {
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
    public void setMUUDETUD(LocalDate value) {
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
