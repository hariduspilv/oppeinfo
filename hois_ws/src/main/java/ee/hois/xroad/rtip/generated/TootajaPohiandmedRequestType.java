
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for tootajaPohiandmedRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tootajaPohiandmedRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="ISIKUKOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="11"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KOMPANII_KOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="PERSONALI_ALA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PERSONALI_NR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_X" type="{http://rtk-v6.x-road.eu}TOOTAJA_POHIANDMED_X" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tootajaPohiandmedRequestType", propOrder = {

})
public class TootajaPohiandmedRequestType {

    @XmlElement(name = "ISIKUKOOD")
    protected String isikukood;
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
    @XmlElement(name = "MUUDETUD_ALATES", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate muudetudalates;
    @XmlElement(name = "PERSONALI_ALA")
    protected String personaliala;
    @XmlElement(name = "PERSONALI_NR")
    protected String personalinr;
    @XmlElement(name = "TOOTAJA_POHIANDMED_X")
    protected TOOTAJAPOHIANDMEDX tootajapohiandmedx;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISIKUKOOD() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISIKUKOOD(String value) {
        this.isikukood = value;
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
     * Gets the value of the muudetudalates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getMUUDETUDALATES() {
        return muudetudalates;
    }

    /**
     * Sets the value of the muudetudalates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUUDETUDALATES(LocalDate value) {
        this.muudetudalates = value;
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
     * Gets the value of the personalinr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSONALINR() {
        return personalinr;
    }

    /**
     * Sets the value of the personalinr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSONALINR(String value) {
        this.personalinr = value;
    }

    /**
     * Gets the value of the tootajapohiandmedx property.
     * 
     * @return
     *     possible object is
     *     {@link TOOTAJAPOHIANDMEDX }
     *     
     */
    public TOOTAJAPOHIANDMEDX getTOOTAJAPOHIANDMEDX() {
        return tootajapohiandmedx;
    }

    /**
     * Sets the value of the tootajapohiandmedx property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOOTAJAPOHIANDMEDX }
     *     
     */
    public void setTOOTAJAPOHIANDMEDX(TOOTAJAPOHIANDMEDX value) {
        this.tootajapohiandmedx = value;
    }

}
