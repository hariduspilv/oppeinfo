
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
 * <p>Java class for ZEMPLOEE1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZEMPLOEE1"&gt;
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
 *         &lt;element name="KORRUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="5"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="RUUM" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="5"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="EMAIL" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="50"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="HARIDUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ERIALA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="70"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PEALIK" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AJUH" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
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
 *         &lt;element name="FAX" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="14"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="LINN" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="35"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TANAV" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="35"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ORGUNIT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PLSTX" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ORGEH" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
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
 *         &lt;element name="PRIOR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ASEND" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
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
 *         &lt;element name="AMNIMI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMKOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TEENINDUSKOHT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="35"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ALGUSKP" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="LAUALYHI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TOOLEKP" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZEMPLOEE1", propOrder = {
    "pernr",
    "vorna",
    "nachn",
    "lauatelefon",
    "mobiil",
    "korrus",
    "ruum",
    "email",
    "haridus",
    "eriala",
    "pealik",
    "ajuh",
    "ikood",
    "fax",
    "linn",
    "tanav",
    "orgunit",
    "plstx",
    "orgeh",
    "descr",
    "prior",
    "asend",
    "koormus",
    "amnimi",
    "amkood",
    "teeninduskoht",
    "alguskp",
    "laualyhi",
    "toolekp"
})
public class ZEMPLOEE1 {

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
    @XmlElement(name = "KORRUS")
    protected String korrus;
    @XmlElement(name = "RUUM")
    protected String ruum;
    @XmlElement(name = "EMAIL")
    protected String email;
    @XmlElement(name = "HARIDUS")
    protected String haridus;
    @XmlElement(name = "ERIALA")
    protected String eriala;
    @XmlElement(name = "PEALIK")
    protected String pealik;
    @XmlElement(name = "AJUH")
    protected String ajuh;
    @XmlElement(name = "IKOOD")
    protected String ikood;
    @XmlElement(name = "FAX")
    protected String fax;
    @XmlElement(name = "LINN")
    protected String linn;
    @XmlElement(name = "TANAV")
    protected String tanav;
    @XmlElement(name = "ORGUNIT")
    protected String orgunit;
    @XmlElement(name = "PLSTX")
    protected String plstx;
    @XmlElement(name = "ORGEH")
    protected String orgeh;
    @XmlElement(name = "DESCR")
    protected String descr;
    @XmlElement(name = "PRIOR")
    protected String prior;
    @XmlElement(name = "ASEND")
    protected String asend;
    @XmlElement(name = "KOORMUS")
    protected BigDecimal koormus;
    @XmlElement(name = "AMNIMI")
    protected String amnimi;
    @XmlElement(name = "AMKOOD")
    protected String amkood;
    @XmlElement(name = "TEENINDUSKOHT")
    protected String teeninduskoht;
    @XmlElement(name = "ALGUSKP", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate alguskp;
    @XmlElement(name = "LAUALYHI")
    protected String laualyhi;
    @XmlElement(name = "TOOLEKP", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate toolekp;

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
     * Gets the value of the korrus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKORRUS() {
        return korrus;
    }

    /**
     * Sets the value of the korrus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKORRUS(String value) {
        this.korrus = value;
    }

    /**
     * Gets the value of the ruum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRUUM() {
        return ruum;
    }

    /**
     * Sets the value of the ruum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRUUM(String value) {
        this.ruum = value;
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
     * Gets the value of the haridus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHARIDUS() {
        return haridus;
    }

    /**
     * Sets the value of the haridus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHARIDUS(String value) {
        this.haridus = value;
    }

    /**
     * Gets the value of the eriala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERIALA() {
        return eriala;
    }

    /**
     * Sets the value of the eriala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERIALA(String value) {
        this.eriala = value;
    }

    /**
     * Gets the value of the pealik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPEALIK() {
        return pealik;
    }

    /**
     * Sets the value of the pealik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPEALIK(String value) {
        this.pealik = value;
    }

    /**
     * Gets the value of the ajuh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAJUH() {
        return ajuh;
    }

    /**
     * Sets the value of the ajuh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAJUH(String value) {
        this.ajuh = value;
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
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAX() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAX(String value) {
        this.fax = value;
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
     * Gets the value of the orgunit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGUNIT() {
        return orgunit;
    }

    /**
     * Sets the value of the orgunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGUNIT(String value) {
        this.orgunit = value;
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
     * Gets the value of the orgeh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGEH() {
        return orgeh;
    }

    /**
     * Sets the value of the orgeh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGEH(String value) {
        this.orgeh = value;
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
     * Gets the value of the prior property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRIOR() {
        return prior;
    }

    /**
     * Sets the value of the prior property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRIOR(String value) {
        this.prior = value;
    }

    /**
     * Gets the value of the asend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASEND() {
        return asend;
    }

    /**
     * Sets the value of the asend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASEND(String value) {
        this.asend = value;
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
     * Gets the value of the amnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMNIMI() {
        return amnimi;
    }

    /**
     * Sets the value of the amnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMNIMI(String value) {
        this.amnimi = value;
    }

    /**
     * Gets the value of the amkood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMKOOD() {
        return amkood;
    }

    /**
     * Sets the value of the amkood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMKOOD(String value) {
        this.amkood = value;
    }

    /**
     * Gets the value of the teeninduskoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEENINDUSKOHT() {
        return teeninduskoht;
    }

    /**
     * Sets the value of the teeninduskoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEENINDUSKOHT(String value) {
        this.teeninduskoht = value;
    }

    /**
     * Gets the value of the alguskp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getALGUSKP() {
        return alguskp;
    }

    /**
     * Sets the value of the alguskp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALGUSKP(LocalDate value) {
        this.alguskp = value;
    }

    /**
     * Gets the value of the laualyhi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLAUALYHI() {
        return laualyhi;
    }

    /**
     * Sets the value of the laualyhi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLAUALYHI(String value) {
        this.laualyhi = value;
    }

    /**
     * Gets the value of the toolekp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getTOOLEKP() {
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
    public void setTOOLEKP(LocalDate value) {
        this.toolekp = value;
    }

}
