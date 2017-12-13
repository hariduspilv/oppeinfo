
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZHR_PPA_OSKUSED_9001 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_OSKUSED_9001"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOKUMENDI_LIIK_NR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KATEGOORIA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DOKUMENDI_KEHTIVUSE_ALGUS" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="JUHTIMISOIGUSE_ALGUS" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="MUUDETUD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="DOKUMENDI_KEHTIVUSE_LOPP" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="DOKUMENDI_NR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
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
@XmlType(name = "ZHR_PPA_OSKUSED_9001", propOrder = {
    "dokumendiliiknr",
    "kategooria",
    "dokumendikehtivusealgus",
    "juhtimisoigusealgus",
    "muudetud",
    "kehtivalates",
    "kehtivkuni",
    "dokumendikehtivuselopp",
    "dokumendinr"
})
public class ZHRPPAOSKUSED9001 {

    @XmlElement(name = "DOKUMENDI_LIIK_NR")
    protected String dokumendiliiknr;
    @XmlElement(name = "KATEGOORIA")
    protected String kategooria;
    @XmlElement(name = "DOKUMENDI_KEHTIVUSE_ALGUS", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate dokumendikehtivusealgus;
    @XmlElement(name = "JUHTIMISOIGUSE_ALGUS", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate juhtimisoigusealgus;
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
    @XmlElement(name = "DOKUMENDI_KEHTIVUSE_LOPP", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate dokumendikehtivuselopp;
    @XmlElement(name = "DOKUMENDI_NR")
    protected String dokumendinr;

    /**
     * Gets the value of the dokumendiliiknr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOKUMENDILIIKNR() {
        return dokumendiliiknr;
    }

    /**
     * Sets the value of the dokumendiliiknr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOKUMENDILIIKNR(String value) {
        this.dokumendiliiknr = value;
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
     * Gets the value of the dokumendikehtivusealgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getDOKUMENDIKEHTIVUSEALGUS() {
        return dokumendikehtivusealgus;
    }

    /**
     * Sets the value of the dokumendikehtivusealgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOKUMENDIKEHTIVUSEALGUS(LocalDate value) {
        this.dokumendikehtivusealgus = value;
    }

    /**
     * Gets the value of the juhtimisoigusealgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getJUHTIMISOIGUSEALGUS() {
        return juhtimisoigusealgus;
    }

    /**
     * Sets the value of the juhtimisoigusealgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJUHTIMISOIGUSEALGUS(LocalDate value) {
        this.juhtimisoigusealgus = value;
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
     * Gets the value of the dokumendikehtivuselopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getDOKUMENDIKEHTIVUSELOPP() {
        return dokumendikehtivuselopp;
    }

    /**
     * Sets the value of the dokumendikehtivuselopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOKUMENDIKEHTIVUSELOPP(LocalDate value) {
        this.dokumendikehtivuselopp = value;
    }

    /**
     * Gets the value of the dokumendinr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOKUMENDINR() {
        return dokumendinr;
    }

    /**
     * Sets the value of the dokumendinr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOKUMENDINR(String value) {
        this.dokumendinr = value;
    }

}
