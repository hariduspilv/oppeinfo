
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
 * <p>Java class for ZHR_PPA_POHIANDMED_0027 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_POHIANDMED_0027"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KULUKESKUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KOMPANII_KOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TELLIMUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="12"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PROTSENT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *               &lt;totalDigits value="5"/&gt;
 *               &lt;fractionDigits value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FOND" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FONDI_KESKUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="16"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TEGEVUSALA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="16"/&gt;
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
@XmlType(name = "ZHR_PPA_POHIANDMED_0027", propOrder = {
    "kulukeskus",
    "muudetud",
    "kehtivalates",
    "kehtivkuni",
    "kompaniikood",
    "tellimus",
    "protsent",
    "fond",
    "fondikeskus",
    "tegevusala"
})
public class ZHRPPAPOHIANDMED0027 {

    @XmlElement(name = "KULUKESKUS")
    protected String kulukeskus;
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
    @XmlElement(name = "KOMPANII_KOOD")
    protected String kompaniikood;
    @XmlElement(name = "TELLIMUS")
    protected String tellimus;
    @XmlElement(name = "PROTSENT")
    protected BigDecimal protsent;
    @XmlElement(name = "FOND")
    protected String fond;
    @XmlElement(name = "FONDI_KESKUS")
    protected String fondikeskus;
    @XmlElement(name = "TEGEVUSALA")
    protected String tegevusala;

    /**
     * Gets the value of the kulukeskus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKULUKESKUS() {
        return kulukeskus;
    }

    /**
     * Sets the value of the kulukeskus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKULUKESKUS(String value) {
        this.kulukeskus = value;
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
     * Gets the value of the tellimus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELLIMUS() {
        return tellimus;
    }

    /**
     * Sets the value of the tellimus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELLIMUS(String value) {
        this.tellimus = value;
    }

    /**
     * Gets the value of the protsent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPROTSENT() {
        return protsent;
    }

    /**
     * Sets the value of the protsent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPROTSENT(BigDecimal value) {
        this.protsent = value;
    }

    /**
     * Gets the value of the fond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFOND() {
        return fond;
    }

    /**
     * Sets the value of the fond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFOND(String value) {
        this.fond = value;
    }

    /**
     * Gets the value of the fondikeskus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFONDIKESKUS() {
        return fondikeskus;
    }

    /**
     * Sets the value of the fondikeskus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFONDIKESKUS(String value) {
        this.fondikeskus = value;
    }

    /**
     * Gets the value of the tegevusala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEGEVUSALA() {
        return tegevusala;
    }

    /**
     * Sets the value of the tegevusala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEGEVUSALA(String value) {
        this.tegevusala = value;
    }

}
