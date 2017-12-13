
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZHR_PPA_TOOTAJA_POHIANDMED complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_TOOTAJA_POHIANDMED"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PERSONALI_NR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ISIKUKOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="11"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="TEENISTUSKAIK_0000" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_TEENISTUSKAIK_0000" minOccurs="0"/&gt;
 *         &lt;element name="TEENISTUSKAIK_9005" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_TEENISTUSKAIK_9005" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0001" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0001" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0002" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0002" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_9004" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_9004" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0552" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0552" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0022" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0022" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0016" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0016" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0007" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0007" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_9006" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_9006" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0006" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0006" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0021" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0021" minOccurs="0"/&gt;
 *         &lt;element name="OSKUSED_9001" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_OSKUSED_9001" minOccurs="0"/&gt;
 *         &lt;element name="OSKUSED_0024" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_OSKUSED_0024" minOccurs="0"/&gt;
 *         &lt;element name="OSKUSED_0040" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_OSKUSED_0040" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_9011" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_9011" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_9003" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_9003" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_9020" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_9020" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0027" type="{http://rtk-v6.x-road.eu}ZHR_PPA_T_POHIANDMED_0027" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0035" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_0034" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED_9013" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZHR_PPA_TOOTAJA_POHIANDMED", propOrder = {
    "personalinr",
    "isikukood",
    "muudetud",
    "teenistuskaik0000",
    "teenistuskaik9005",
    "tootajapohiandmed0001",
    "tootajapohiandmed0002",
    "tootajapohiandmed9004",
    "tootajapohiandmed0552",
    "tootajapohiandmed0022",
    "tootajapohiandmed0016",
    "tootajapohiandmed0007",
    "tootajapohiandmed9006",
    "tootajapohiandmed0006",
    "tootajapohiandmed0021",
    "oskused9001",
    "oskused0024",
    "oskused0040",
    "tootajapohiandmed9011",
    "tootajapohiandmed9003",
    "tootajapohiandmed9020",
    "tootajapohiandmed0027",
    "tootajapohiandmed0035",
    "tootajapohiandmed0034",
    "tootajapohiandmed9013"
})
public class ZHRPPATOOTAJAPOHIANDMED {

    @XmlElement(name = "PERSONALI_NR")
    protected String personalinr;
    @XmlElement(name = "ISIKUKOOD")
    protected String isikukood;
    @XmlElement(name = "MUUDETUD", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate muudetud;
    @XmlElement(name = "TEENISTUSKAIK_0000")
    protected ZHRPPATTEENISTUSKAIK0000 teenistuskaik0000;
    @XmlElement(name = "TEENISTUSKAIK_9005")
    protected ZHRPPATTEENISTUSKAIK9005 teenistuskaik9005;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0001")
    protected ZHRPPATPOHIANDMED0001 tootajapohiandmed0001;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0002")
    protected ZHRPPATPOHIANDMED0002 tootajapohiandmed0002;
    @XmlElement(name = "TOOTAJA_POHIANDMED_9004")
    protected ZHRPPATPOHIANDMED9004 tootajapohiandmed9004;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0552")
    protected ZHRPPATPOHIANDMED0552 tootajapohiandmed0552;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0022")
    protected ZHRPPATPOHIANDMED0022 tootajapohiandmed0022;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0016")
    protected ZHRPPATPOHIANDMED0016 tootajapohiandmed0016;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0007")
    protected ZHRPPATPOHIANDMED0007 tootajapohiandmed0007;
    @XmlElement(name = "TOOTAJA_POHIANDMED_9006")
    protected ZHRPPATPOHIANDMED9006 tootajapohiandmed9006;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0006")
    protected ZHRPPATPOHIANDMED0006 tootajapohiandmed0006;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0021")
    protected ZHRPPATPOHIANDMED0021 tootajapohiandmed0021;
    @XmlElement(name = "OSKUSED_9001")
    protected ZHRPPATOSKUSED9001 oskused9001;
    @XmlElement(name = "OSKUSED_0024")
    protected ZHRPPATOSKUSED0024 oskused0024;
    @XmlElement(name = "OSKUSED_0040")
    protected ZHRPPATOSKUSED0040 oskused0040;
    @XmlElement(name = "TOOTAJA_POHIANDMED_9011")
    protected ZHRPPATPOHIANDMED9011 tootajapohiandmed9011;
    @XmlElement(name = "TOOTAJA_POHIANDMED_9003")
    protected ZHRPPATPOHIANDMED9003 tootajapohiandmed9003;
    @XmlElement(name = "TOOTAJA_POHIANDMED_9020")
    protected ZHRPPATPOHIANDMED9020 tootajapohiandmed9020;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0027")
    protected ZHRPPATPOHIANDMED0027 tootajapohiandmed0027;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0035")
    protected String tootajapohiandmed0035;
    @XmlElement(name = "TOOTAJA_POHIANDMED_0034")
    protected String tootajapohiandmed0034;
    @XmlElement(name = "TOOTAJA_POHIANDMED_9013")
    protected String tootajapohiandmed9013;

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
     * Gets the value of the teenistuskaik0000 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATTEENISTUSKAIK0000 }
     *     
     */
    public ZHRPPATTEENISTUSKAIK0000 getTEENISTUSKAIK0000() {
        return teenistuskaik0000;
    }

    /**
     * Sets the value of the teenistuskaik0000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATTEENISTUSKAIK0000 }
     *     
     */
    public void setTEENISTUSKAIK0000(ZHRPPATTEENISTUSKAIK0000 value) {
        this.teenistuskaik0000 = value;
    }

    /**
     * Gets the value of the teenistuskaik9005 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATTEENISTUSKAIK9005 }
     *     
     */
    public ZHRPPATTEENISTUSKAIK9005 getTEENISTUSKAIK9005() {
        return teenistuskaik9005;
    }

    /**
     * Sets the value of the teenistuskaik9005 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATTEENISTUSKAIK9005 }
     *     
     */
    public void setTEENISTUSKAIK9005(ZHRPPATTEENISTUSKAIK9005 value) {
        this.teenistuskaik9005 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0001 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0001 }
     *     
     */
    public ZHRPPATPOHIANDMED0001 getTOOTAJAPOHIANDMED0001() {
        return tootajapohiandmed0001;
    }

    /**
     * Sets the value of the tootajapohiandmed0001 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0001 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0001(ZHRPPATPOHIANDMED0001 value) {
        this.tootajapohiandmed0001 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0002 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0002 }
     *     
     */
    public ZHRPPATPOHIANDMED0002 getTOOTAJAPOHIANDMED0002() {
        return tootajapohiandmed0002;
    }

    /**
     * Sets the value of the tootajapohiandmed0002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0002 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0002(ZHRPPATPOHIANDMED0002 value) {
        this.tootajapohiandmed0002 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed9004 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED9004 }
     *     
     */
    public ZHRPPATPOHIANDMED9004 getTOOTAJAPOHIANDMED9004() {
        return tootajapohiandmed9004;
    }

    /**
     * Sets the value of the tootajapohiandmed9004 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED9004 }
     *     
     */
    public void setTOOTAJAPOHIANDMED9004(ZHRPPATPOHIANDMED9004 value) {
        this.tootajapohiandmed9004 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0552 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0552 }
     *     
     */
    public ZHRPPATPOHIANDMED0552 getTOOTAJAPOHIANDMED0552() {
        return tootajapohiandmed0552;
    }

    /**
     * Sets the value of the tootajapohiandmed0552 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0552 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0552(ZHRPPATPOHIANDMED0552 value) {
        this.tootajapohiandmed0552 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0022 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0022 }
     *     
     */
    public ZHRPPATPOHIANDMED0022 getTOOTAJAPOHIANDMED0022() {
        return tootajapohiandmed0022;
    }

    /**
     * Sets the value of the tootajapohiandmed0022 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0022 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0022(ZHRPPATPOHIANDMED0022 value) {
        this.tootajapohiandmed0022 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0016 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0016 }
     *     
     */
    public ZHRPPATPOHIANDMED0016 getTOOTAJAPOHIANDMED0016() {
        return tootajapohiandmed0016;
    }

    /**
     * Sets the value of the tootajapohiandmed0016 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0016 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0016(ZHRPPATPOHIANDMED0016 value) {
        this.tootajapohiandmed0016 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0007 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0007 }
     *     
     */
    public ZHRPPATPOHIANDMED0007 getTOOTAJAPOHIANDMED0007() {
        return tootajapohiandmed0007;
    }

    /**
     * Sets the value of the tootajapohiandmed0007 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0007 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0007(ZHRPPATPOHIANDMED0007 value) {
        this.tootajapohiandmed0007 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed9006 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED9006 }
     *     
     */
    public ZHRPPATPOHIANDMED9006 getTOOTAJAPOHIANDMED9006() {
        return tootajapohiandmed9006;
    }

    /**
     * Sets the value of the tootajapohiandmed9006 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED9006 }
     *     
     */
    public void setTOOTAJAPOHIANDMED9006(ZHRPPATPOHIANDMED9006 value) {
        this.tootajapohiandmed9006 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0006 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0006 }
     *     
     */
    public ZHRPPATPOHIANDMED0006 getTOOTAJAPOHIANDMED0006() {
        return tootajapohiandmed0006;
    }

    /**
     * Sets the value of the tootajapohiandmed0006 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0006 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0006(ZHRPPATPOHIANDMED0006 value) {
        this.tootajapohiandmed0006 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0021 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0021 }
     *     
     */
    public ZHRPPATPOHIANDMED0021 getTOOTAJAPOHIANDMED0021() {
        return tootajapohiandmed0021;
    }

    /**
     * Sets the value of the tootajapohiandmed0021 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0021 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0021(ZHRPPATPOHIANDMED0021 value) {
        this.tootajapohiandmed0021 = value;
    }

    /**
     * Gets the value of the oskused9001 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATOSKUSED9001 }
     *     
     */
    public ZHRPPATOSKUSED9001 getOSKUSED9001() {
        return oskused9001;
    }

    /**
     * Sets the value of the oskused9001 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATOSKUSED9001 }
     *     
     */
    public void setOSKUSED9001(ZHRPPATOSKUSED9001 value) {
        this.oskused9001 = value;
    }

    /**
     * Gets the value of the oskused0024 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATOSKUSED0024 }
     *     
     */
    public ZHRPPATOSKUSED0024 getOSKUSED0024() {
        return oskused0024;
    }

    /**
     * Sets the value of the oskused0024 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATOSKUSED0024 }
     *     
     */
    public void setOSKUSED0024(ZHRPPATOSKUSED0024 value) {
        this.oskused0024 = value;
    }

    /**
     * Gets the value of the oskused0040 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATOSKUSED0040 }
     *     
     */
    public ZHRPPATOSKUSED0040 getOSKUSED0040() {
        return oskused0040;
    }

    /**
     * Sets the value of the oskused0040 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATOSKUSED0040 }
     *     
     */
    public void setOSKUSED0040(ZHRPPATOSKUSED0040 value) {
        this.oskused0040 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed9011 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED9011 }
     *     
     */
    public ZHRPPATPOHIANDMED9011 getTOOTAJAPOHIANDMED9011() {
        return tootajapohiandmed9011;
    }

    /**
     * Sets the value of the tootajapohiandmed9011 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED9011 }
     *     
     */
    public void setTOOTAJAPOHIANDMED9011(ZHRPPATPOHIANDMED9011 value) {
        this.tootajapohiandmed9011 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed9003 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED9003 }
     *     
     */
    public ZHRPPATPOHIANDMED9003 getTOOTAJAPOHIANDMED9003() {
        return tootajapohiandmed9003;
    }

    /**
     * Sets the value of the tootajapohiandmed9003 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED9003 }
     *     
     */
    public void setTOOTAJAPOHIANDMED9003(ZHRPPATPOHIANDMED9003 value) {
        this.tootajapohiandmed9003 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed9020 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED9020 }
     *     
     */
    public ZHRPPATPOHIANDMED9020 getTOOTAJAPOHIANDMED9020() {
        return tootajapohiandmed9020;
    }

    /**
     * Sets the value of the tootajapohiandmed9020 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED9020 }
     *     
     */
    public void setTOOTAJAPOHIANDMED9020(ZHRPPATPOHIANDMED9020 value) {
        this.tootajapohiandmed9020 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0027 property.
     * 
     * @return
     *     possible object is
     *     {@link ZHRPPATPOHIANDMED0027 }
     *     
     */
    public ZHRPPATPOHIANDMED0027 getTOOTAJAPOHIANDMED0027() {
        return tootajapohiandmed0027;
    }

    /**
     * Sets the value of the tootajapohiandmed0027 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZHRPPATPOHIANDMED0027 }
     *     
     */
    public void setTOOTAJAPOHIANDMED0027(ZHRPPATPOHIANDMED0027 value) {
        this.tootajapohiandmed0027 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0035 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOTAJAPOHIANDMED0035() {
        return tootajapohiandmed0035;
    }

    /**
     * Sets the value of the tootajapohiandmed0035 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOTAJAPOHIANDMED0035(String value) {
        this.tootajapohiandmed0035 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed0034 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOTAJAPOHIANDMED0034() {
        return tootajapohiandmed0034;
    }

    /**
     * Sets the value of the tootajapohiandmed0034 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOTAJAPOHIANDMED0034(String value) {
        this.tootajapohiandmed0034 = value;
    }

    /**
     * Gets the value of the tootajapohiandmed9013 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOTAJAPOHIANDMED9013() {
        return tootajapohiandmed9013;
    }

    /**
     * Sets the value of the tootajapohiandmed9013 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOTAJAPOHIANDMED9013(String value) {
        this.tootajapohiandmed9013 = value;
    }

}
