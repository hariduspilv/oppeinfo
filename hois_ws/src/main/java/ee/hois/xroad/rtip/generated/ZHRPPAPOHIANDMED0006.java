
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZHR_PPA_POHIANDMED_0006 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_POHIANDMED_0006"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TEGELIKU_ELUKOHA_AADRESS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TANAV" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="60"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MAJA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KORTER" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="VALD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="INDEKS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="LINN" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ISIKLIK_TELEFON" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="14"/&gt;
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
@XmlType(name = "ZHR_PPA_POHIANDMED_0006", propOrder = {
    "tegelikuelukohaaadress",
    "tanav",
    "maja",
    "korter",
    "vald",
    "indeks",
    "linn",
    "isikliktelefon",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRPPAPOHIANDMED0006 {

    @XmlElement(name = "TEGELIKU_ELUKOHA_AADRESS")
    protected String tegelikuelukohaaadress;
    @XmlElement(name = "TANAV")
    protected String tanav;
    @XmlElement(name = "MAJA")
    protected String maja;
    @XmlElement(name = "KORTER")
    protected String korter;
    @XmlElement(name = "VALD")
    protected String vald;
    @XmlElement(name = "INDEKS")
    protected String indeks;
    @XmlElement(name = "LINN")
    protected String linn;
    @XmlElement(name = "ISIKLIK_TELEFON")
    protected String isikliktelefon;
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
     * Gets the value of the tegelikuelukohaaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEGELIKUELUKOHAAADRESS() {
        return tegelikuelukohaaadress;
    }

    /**
     * Sets the value of the tegelikuelukohaaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEGELIKUELUKOHAAADRESS(String value) {
        this.tegelikuelukohaaadress = value;
    }

    /**
     * Gets the value of the tanav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTANAV() {
        return tanav;
    }

    /**
     * Sets the value of the tanav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTANAV(String value) {
        this.tanav = value;
    }

    /**
     * Gets the value of the maja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMAJA() {
        return maja;
    }

    /**
     * Sets the value of the maja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMAJA(String value) {
        this.maja = value;
    }

    /**
     * Gets the value of the korter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKORTER() {
        return korter;
    }

    /**
     * Sets the value of the korter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKORTER(String value) {
        this.korter = value;
    }

    /**
     * Gets the value of the vald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALD() {
        return vald;
    }

    /**
     * Sets the value of the vald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALD(String value) {
        this.vald = value;
    }

    /**
     * Gets the value of the indeks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEKS() {
        return indeks;
    }

    /**
     * Sets the value of the indeks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEKS(String value) {
        this.indeks = value;
    }

    /**
     * Gets the value of the linn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLINN() {
        return linn;
    }

    /**
     * Sets the value of the linn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLINN(String value) {
        this.linn = value;
    }

    /**
     * Gets the value of the isikliktelefon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISIKLIKTELEFON() {
        return isikliktelefon;
    }

    /**
     * Sets the value of the isikliktelefon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISIKLIKTELEFON(String value) {
        this.isikliktelefon = value;
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
