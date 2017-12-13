
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZHR_PPA_POHIANDMED_9003 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_POHIANDMED_9003"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ERGUTUSE_LIIK" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ERGUTUSE_LIIGI_KOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ERGUTUSE_LIIGI_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="60"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SISU" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="35"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KATEGOORIA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
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
@XmlType(name = "ZHR_PPA_POHIANDMED_9003", propOrder = {
    "ergutuseliik",
    "ergutuseliigikood",
    "ergutuseliigitekst",
    "sisu",
    "kategooria",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRPPAPOHIANDMED9003 {

    @XmlElement(name = "ERGUTUSE_LIIK")
    protected String ergutuseliik;
    @XmlElement(name = "ERGUTUSE_LIIGI_KOOD")
    protected String ergutuseliigikood;
    @XmlElement(name = "ERGUTUSE_LIIGI_TEKST")
    protected String ergutuseliigitekst;
    @XmlElement(name = "SISU")
    protected String sisu;
    @XmlElement(name = "KATEGOORIA")
    protected String kategooria;
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
     * Gets the value of the ergutuseliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERGUTUSELIIK() {
        return ergutuseliik;
    }

    /**
     * Sets the value of the ergutuseliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERGUTUSELIIK(String value) {
        this.ergutuseliik = value;
    }

    /**
     * Gets the value of the ergutuseliigikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERGUTUSELIIGIKOOD() {
        return ergutuseliigikood;
    }

    /**
     * Sets the value of the ergutuseliigikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERGUTUSELIIGIKOOD(String value) {
        this.ergutuseliigikood = value;
    }

    /**
     * Gets the value of the ergutuseliigitekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERGUTUSELIIGITEKST() {
        return ergutuseliigitekst;
    }

    /**
     * Sets the value of the ergutuseliigitekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERGUTUSELIIGITEKST(String value) {
        this.ergutuseliigitekst = value;
    }

    /**
     * Gets the value of the sisu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSISU() {
        return sisu;
    }

    /**
     * Sets the value of the sisu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSISU(String value) {
        this.sisu = value;
    }

    /**
     * Gets the value of the kategooria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKATEGOORIA() {
        return kategooria;
    }

    /**
     * Sets the value of the kategooria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKATEGOORIA(String value) {
        this.kategooria = value;
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
