
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZEMPL_EES complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZEMPL_EES"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PERNR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="VORNA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="25"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="NACHN" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="25"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="LAUATELEFON" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="14"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MOBIIL" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="EMAIL" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="58"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="IKOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="11"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PLSTX" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="120"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DESCR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="250"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ASUTUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="60"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ROLL" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TOOLEKP" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZEMPL_EES", propOrder = {
    "pernr",
    "vorna",
    "nachn",
    "lauatelefon",
    "mobiil",
    "email",
    "ikood",
    "plstx",
    "descr",
    "asutus",
    "roll",
    "toolekp"
})
public class ZEMPLEES {

    @XmlElement(name = "PERNR")
    protected String pernr;
    @XmlElement(name = "VORNA")
    protected String vorna;
    @XmlElement(name = "NACHN")
    protected String nachn;
    @XmlElement(name = "LAUATELEFON")
    protected String lauatelefon;
    @XmlElement(name = "MOBIIL")
    protected String mobiil;
    @XmlElement(name = "EMAIL")
    protected String email;
    @XmlElement(name = "IKOOD")
    protected String ikood;
    @XmlElement(name = "PLSTX")
    protected String plstx;
    @XmlElement(name = "DESCR")
    protected String descr;
    @XmlElement(name = "ASUTUS")
    protected String asutus;
    @XmlElement(name = "ROLL")
    protected String roll;
    @XmlElement(name = "TOOLEKP")
    protected String toolekp;

    /**
     * Gets the value of the pernr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERNR() {
        return pernr;
    }

    /**
     * Sets the value of the pernr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERNR(String value) {
        this.pernr = value;
    }

    /**
     * Gets the value of the vorna property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVORNA() {
        return vorna;
    }

    /**
     * Sets the value of the vorna property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVORNA(String value) {
        this.vorna = value;
    }

    /**
     * Gets the value of the nachn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNACHN() {
        return nachn;
    }

    /**
     * Sets the value of the nachn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNACHN(String value) {
        this.nachn = value;
    }

    /**
     * Gets the value of the lauatelefon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLAUATELEFON() {
        return lauatelefon;
    }

    /**
     * Sets the value of the lauatelefon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLAUATELEFON(String value) {
        this.lauatelefon = value;
    }

    /**
     * Gets the value of the mobiil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOBIIL() {
        return mobiil;
    }

    /**
     * Sets the value of the mobiil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOBIIL(String value) {
        this.mobiil = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAIL() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the ikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIKOOD() {
        return ikood;
    }

    /**
     * Sets the value of the ikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIKOOD(String value) {
        this.ikood = value;
    }

    /**
     * Gets the value of the plstx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPLSTX() {
        return plstx;
    }

    /**
     * Sets the value of the plstx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPLSTX(String value) {
        this.plstx = value;
    }

    /**
     * Gets the value of the descr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCR() {
        return descr;
    }

    /**
     * Sets the value of the descr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCR(String value) {
        this.descr = value;
    }

    /**
     * Gets the value of the asutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASUTUS() {
        return asutus;
    }

    /**
     * Sets the value of the asutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASUTUS(String value) {
        this.asutus = value;
    }

    /**
     * Gets the value of the roll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getROLL() {
        return roll;
    }

    /**
     * Sets the value of the roll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setROLL(String value) {
        this.roll = value;
    }

    /**
     * Gets the value of the toolekp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOLEKP() {
        return toolekp;
    }

    /**
     * Sets the value of the toolekp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOLEKP(String value) {
        this.toolekp = value;
    }

}
