
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
 * <p>Java class for ZHR_PPA_OSKUSED_0040 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_OSKUSED_0040"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KASUTUSSE_ANTUD_VARA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="VARA_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KOGUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *               &lt;totalDigits value="4"/&gt;
 *               &lt;fractionDigits value="0"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="YHIK" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KOMMENTAAR1" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="72"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KOMMENTAAR2" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="72"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KOMMENTAAR3" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="72"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="VARA_NUMBER_TUNNUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
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
@XmlType(name = "ZHR_PPA_OSKUSED_0040", propOrder = {
    "kasutusseantudvara",
    "varatekst",
    "kogus",
    "yhik",
    "kommentaar1",
    "kommentaar2",
    "kommentaar3",
    "muudetud",
    "kehtivalates",
    "kehtivkuni",
    "varanumbertunnus"
})
public class ZHRPPAOSKUSED0040 {

    @XmlElement(name = "KASUTUSSE_ANTUD_VARA")
    protected String kasutusseantudvara;
    @XmlElement(name = "VARA_TEKST")
    protected String varatekst;
    @XmlElement(name = "KOGUS")
    protected BigDecimal kogus;
    @XmlElement(name = "YHIK")
    protected String yhik;
    @XmlElement(name = "KOMMENTAAR1")
    protected String kommentaar1;
    @XmlElement(name = "KOMMENTAAR2")
    protected String kommentaar2;
    @XmlElement(name = "KOMMENTAAR3")
    protected String kommentaar3;
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
    @XmlElement(name = "VARA_NUMBER_TUNNUS")
    protected String varanumbertunnus;

    /**
     * Gets the value of the kasutusseantudvara property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKASUTUSSEANTUDVARA() {
        return kasutusseantudvara;
    }

    /**
     * Sets the value of the kasutusseantudvara property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKASUTUSSEANTUDVARA(String value) {
        this.kasutusseantudvara = value;
    }

    /**
     * Gets the value of the varatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVARATEKST() {
        return varatekst;
    }

    /**
     * Sets the value of the varatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVARATEKST(String value) {
        this.varatekst = value;
    }

    /**
     * Gets the value of the kogus property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKOGUS() {
        return kogus;
    }

    /**
     * Sets the value of the kogus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKOGUS(BigDecimal value) {
        this.kogus = value;
    }

    /**
     * Gets the value of the yhik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYHIK() {
        return yhik;
    }

    /**
     * Sets the value of the yhik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYHIK(String value) {
        this.yhik = value;
    }

    /**
     * Gets the value of the kommentaar1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOMMENTAAR1() {
        return kommentaar1;
    }

    /**
     * Sets the value of the kommentaar1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOMMENTAAR1(String value) {
        this.kommentaar1 = value;
    }

    /**
     * Gets the value of the kommentaar2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOMMENTAAR2() {
        return kommentaar2;
    }

    /**
     * Sets the value of the kommentaar2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOMMENTAAR2(String value) {
        this.kommentaar2 = value;
    }

    /**
     * Gets the value of the kommentaar3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOMMENTAAR3() {
        return kommentaar3;
    }

    /**
     * Sets the value of the kommentaar3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOMMENTAAR3(String value) {
        this.kommentaar3 = value;
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
     * Gets the value of the varanumbertunnus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVARANUMBERTUNNUS() {
        return varanumbertunnus;
    }

    /**
     * Sets the value of the varanumbertunnus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVARANUMBERTUNNUS(String value) {
        this.varanumbertunnus = value;
    }

}
