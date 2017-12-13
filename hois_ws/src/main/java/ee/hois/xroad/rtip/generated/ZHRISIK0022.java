
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZHR_ISIK_0022 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_ISIK_0022"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="HARIDUSASUTUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="80"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="HARIDUSASTE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="HARIDUSASTE_TEKST" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="35"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PEAMINE_HARIDUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="KORGEM_ERITEENISTUS_HARIDUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
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
 *         &lt;element name="LOPETAMISE_AEG" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AKADEEMILINE_KRAAD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AKAD_KRAADI_NIMETUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://rtk-v6.x-road.eu}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZHR_ISIK_0022", propOrder = {
    "haridusasutus",
    "haridusaste",
    "haridusastetekst",
    "peamineharidus",
    "korgemeriteenistusharidus",
    "eriala",
    "lopetamiseaeg",
    "akadeemilinekraad",
    "akadkraadinimetus",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRISIK0022 {

    @XmlElement(name = "HARIDUSASUTUS")
    protected String haridusasutus;
    @XmlElement(name = "HARIDUSASTE")
    protected String haridusaste;
    @XmlElement(name = "HARIDUSASTE_TEKST")
    protected String haridusastetekst;
    @XmlElement(name = "PEAMINE_HARIDUS")
    protected String peamineharidus;
    @XmlElement(name = "KORGEM_ERITEENISTUS_HARIDUS")
    protected String korgemeriteenistusharidus;
    @XmlElement(name = "ERIALA")
    protected String eriala;
    @XmlElement(name = "LOPETAMISE_AEG")
    protected String lopetamiseaeg;
    @XmlElement(name = "AKADEEMILINE_KRAAD")
    protected String akadeemilinekraad;
    @XmlElement(name = "AKAD_KRAADI_NIMETUS")
    protected String akadkraadinimetus;
    @XmlElement(name = "MUUDETUD")
    protected String muudetud;
    @XmlElement(name = "KEHTIV_ALATES")
    protected String kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI")
    protected String kehtivkuni;

    /**
     * Gets the value of the haridusasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHARIDUSASUTUS() {
        return haridusasutus;
    }

    /**
     * Sets the value of the haridusasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHARIDUSASUTUS(String value) {
        this.haridusasutus = value;
    }

    /**
     * Gets the value of the haridusaste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHARIDUSASTE() {
        return haridusaste;
    }

    /**
     * Sets the value of the haridusaste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHARIDUSASTE(String value) {
        this.haridusaste = value;
    }

    /**
     * Gets the value of the haridusastetekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHARIDUSASTETEKST() {
        return haridusastetekst;
    }

    /**
     * Sets the value of the haridusastetekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHARIDUSASTETEKST(String value) {
        this.haridusastetekst = value;
    }

    /**
     * Gets the value of the peamineharidus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPEAMINEHARIDUS() {
        return peamineharidus;
    }

    /**
     * Sets the value of the peamineharidus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPEAMINEHARIDUS(String value) {
        this.peamineharidus = value;
    }

    /**
     * Gets the value of the korgemeriteenistusharidus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKORGEMERITEENISTUSHARIDUS() {
        return korgemeriteenistusharidus;
    }

    /**
     * Sets the value of the korgemeriteenistusharidus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKORGEMERITEENISTUSHARIDUS(String value) {
        this.korgemeriteenistusharidus = value;
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
     * Gets the value of the lopetamiseaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOPETAMISEAEG() {
        return lopetamiseaeg;
    }

    /**
     * Sets the value of the lopetamiseaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOPETAMISEAEG(String value) {
        this.lopetamiseaeg = value;
    }

    /**
     * Gets the value of the akadeemilinekraad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAKADEEMILINEKRAAD() {
        return akadeemilinekraad;
    }

    /**
     * Sets the value of the akadeemilinekraad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAKADEEMILINEKRAAD(String value) {
        this.akadeemilinekraad = value;
    }

    /**
     * Gets the value of the akadkraadinimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAKADKRAADINIMETUS() {
        return akadkraadinimetus;
    }

    /**
     * Sets the value of the akadkraadinimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAKADKRAADINIMETUS(String value) {
        this.akadkraadinimetus = value;
    }

    /**
     * Gets the value of the muudetud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUUDETUD() {
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
    public void setMUUDETUD(String value) {
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
    public String getKEHTIVALATES() {
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
    public void setKEHTIVALATES(String value) {
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
    public String getKEHTIVKUNI() {
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
    public void setKEHTIVKUNI(String value) {
        this.kehtivkuni = value;
    }

}
