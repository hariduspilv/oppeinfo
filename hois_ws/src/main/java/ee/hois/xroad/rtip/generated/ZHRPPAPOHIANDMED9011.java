
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZHR_PPA_POHIANDMED_9011 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_POHIANDMED_9011"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ASENDAJA_ID" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
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
@XmlType(name = "ZHR_PPA_POHIANDMED_9011", propOrder = {
    "asendajaid",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRPPAPOHIANDMED9011 {

    @XmlElement(name = "ASENDAJA_ID")
    protected String asendajaid;
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
     * Gets the value of the asendajaid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASENDAJAID() {
        return asendajaid;
    }

    /**
     * Sets the value of the asendajaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASENDAJAID(String value) {
        this.asendajaid = value;
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