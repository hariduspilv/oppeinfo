
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
 * <p>Java class for ZHR_PPA_POHIANDMED_0007 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_POHIANDMED_0007"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TOOAJANORM" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TOOAJANORM_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="25"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="OSALINE_TOOAEG" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
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
 *         &lt;element name="TOOT_ALLGR_KALENDR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PYHADE_KALENDR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PER_ALLYKSUS_KALENDR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SUMMEERITUD_TOOAEG" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
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
@XmlType(name = "ZHR_PPA_POHIANDMED_0007", propOrder = {
    "tooajanorm",
    "tooajanormtekst",
    "muudetud",
    "kehtivalates",
    "kehtivkuni",
    "osalinetooaeg",
    "koormus",
    "tootallgrkalendr",
    "pyhadekalendr",
    "perallyksuskalendr",
    "summeeritudtooaeg"
})
public class ZHRPPAPOHIANDMED0007 {

    @XmlElement(name = "TOOAJANORM")
    protected String tooajanorm;
    @XmlElement(name = "TOOAJANORM_TEKST")
    protected String tooajanormtekst;
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
    @XmlElement(name = "OSALINE_TOOAEG")
    protected String osalinetooaeg;
    @XmlElement(name = "KOORMUS")
    protected BigDecimal koormus;
    @XmlElement(name = "TOOT_ALLGR_KALENDR")
    protected String tootallgrkalendr;
    @XmlElement(name = "PYHADE_KALENDR")
    protected String pyhadekalendr;
    @XmlElement(name = "PER_ALLYKSUS_KALENDR")
    protected String perallyksuskalendr;
    @XmlElement(name = "SUMMEERITUD_TOOAEG")
    protected String summeeritudtooaeg;

    /**
     * Gets the value of the tooajanorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOAJANORM() {
        return tooajanorm;
    }

    /**
     * Sets the value of the tooajanorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOAJANORM(String value) {
        this.tooajanorm = value;
    }

    /**
     * Gets the value of the tooajanormtekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOAJANORMTEKST() {
        return tooajanormtekst;
    }

    /**
     * Sets the value of the tooajanormtekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOAJANORMTEKST(String value) {
        this.tooajanormtekst = value;
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
     * Gets the value of the osalinetooaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOSALINETOOAEG() {
        return osalinetooaeg;
    }

    /**
     * Sets the value of the osalinetooaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOSALINETOOAEG(String value) {
        this.osalinetooaeg = value;
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
     * Gets the value of the tootallgrkalendr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOOTALLGRKALENDR() {
        return tootallgrkalendr;
    }

    /**
     * Sets the value of the tootallgrkalendr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOOTALLGRKALENDR(String value) {
        this.tootallgrkalendr = value;
    }

    /**
     * Gets the value of the pyhadekalendr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPYHADEKALENDR() {
        return pyhadekalendr;
    }

    /**
     * Sets the value of the pyhadekalendr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPYHADEKALENDR(String value) {
        this.pyhadekalendr = value;
    }

    /**
     * Gets the value of the perallyksuskalendr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERALLYKSUSKALENDR() {
        return perallyksuskalendr;
    }

    /**
     * Sets the value of the perallyksuskalendr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERALLYKSUSKALENDR(String value) {
        this.perallyksuskalendr = value;
    }

    /**
     * Gets the value of the summeeritudtooaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUMMEERITUDTOOAEG() {
        return summeeritudtooaeg;
    }

    /**
     * Sets the value of the summeeritudtooaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUMMEERITUDTOOAEG(String value) {
        this.summeeritudtooaeg = value;
    }

}
