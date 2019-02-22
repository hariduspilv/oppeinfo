
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for xbrl_aruande_naitajad_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrl_aruande_naitajad_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="xbrl_aruande_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="paev_nr" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="maj_algus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="maj_lopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="ehak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="postiind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pteg_emtak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pteg_emtak_versioon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esitatud" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="ark" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="tootajate_arv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaive" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kasum" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="bilansimaht" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="konsol_tootajate_arv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="konsol_kaive" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="konsol_kasum" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="konsol_bilansimaht" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="valuuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitamise_viis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="auditeerimiskohustus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="auditeeritus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="bilansi_sisud" type="{http://arireg.x-road.eu/producer/}xbrl_aruande_bilansi_sisud"/&gt;
 *         &lt;element name="ainult_audiitorid_ja_emtak" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="kohustuslik_audit" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="audiitorid" type="{http://arireg.x-road.eu/producer/}xbrl_aruande_audiitorid" minOccurs="0"/&gt;
 *         &lt;element name="activity_occured" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="myygitulu" type="{http://arireg.x-road.eu/producer/}xbrl_aruande_myygitulud" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrl_aruande_naitajad_paring", propOrder = {
    "xbrlAruandeId",
    "paevNr",
    "majAlgus",
    "majLopp",
    "ehak",
    "aadress",
    "postiind",
    "ptegEmtak",
    "ptegEmtakVersioon",
    "esitatud",
    "ark",
    "tootajateArv",
    "kaive",
    "kasum",
    "bilansimaht",
    "konsolTootajateArv",
    "konsolKaive",
    "konsolKasum",
    "konsolBilansimaht",
    "valuuta",
    "esitamiseViis",
    "auditeerimiskohustus",
    "auditeeritus",
    "bilansiSisud",
    "ainultAudiitoridJaEmtak",
    "kohustuslikAudit",
    "audiitorid",
    "activityOccured",
    "myygitulu"
})
public class XbrlAruandeNaitajadParing {

    @XmlElement(name = "xbrl_aruande_id", required = true)
    protected BigInteger xbrlAruandeId;
    @XmlElement(name = "paev_nr", required = true)
    protected BigInteger paevNr;
    @XmlElement(name = "maj_algus", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majAlgus;
    @XmlElement(name = "maj_lopp", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majLopp;
    protected String ehak;
    protected String aadress;
    protected String postiind;
    @XmlElement(name = "pteg_emtak")
    protected String ptegEmtak;
    @XmlElement(name = "pteg_emtak_versioon")
    protected String ptegEmtakVersioon;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitatud;
    @XmlElement(required = true)
    protected BigInteger ark;
    @XmlElement(name = "tootajate_arv")
    protected BigInteger tootajateArv;
    protected BigInteger kaive;
    protected BigInteger kasum;
    protected BigInteger bilansimaht;
    @XmlElement(name = "konsol_tootajate_arv")
    protected BigInteger konsolTootajateArv;
    @XmlElement(name = "konsol_kaive")
    protected BigInteger konsolKaive;
    @XmlElement(name = "konsol_kasum")
    protected BigInteger konsolKasum;
    @XmlElement(name = "konsol_bilansimaht")
    protected BigInteger konsolBilansimaht;
    @XmlElement(required = true)
    protected String valuuta;
    @XmlElement(name = "esitamise_viis")
    protected String esitamiseViis;
    protected Boolean auditeerimiskohustus;
    protected Boolean auditeeritus;
    @XmlElement(name = "bilansi_sisud", required = true)
    protected XbrlAruandeBilansiSisud bilansiSisud;
    @XmlElement(name = "ainult_audiitorid_ja_emtak")
    protected Boolean ainultAudiitoridJaEmtak;
    @XmlElement(name = "kohustuslik_audit")
    protected Boolean kohustuslikAudit;
    protected XbrlAruandeAudiitorid audiitorid;
    @XmlElement(name = "activity_occured")
    protected BigInteger activityOccured;
    protected XbrlAruandeMyygitulud myygitulu;

    /**
     * Gets the value of the xbrlAruandeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getXbrlAruandeId() {
        return xbrlAruandeId;
    }

    /**
     * Sets the value of the xbrlAruandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setXbrlAruandeId(BigInteger value) {
        this.xbrlAruandeId = value;
    }

    /**
     * Gets the value of the paevNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPaevNr() {
        return paevNr;
    }

    /**
     * Sets the value of the paevNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPaevNr(BigInteger value) {
        this.paevNr = value;
    }

    /**
     * Gets the value of the majAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajAlgus() {
        return majAlgus;
    }

    /**
     * Sets the value of the majAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajAlgus(XMLGregorianCalendar value) {
        this.majAlgus = value;
    }

    /**
     * Gets the value of the majLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajLopp() {
        return majLopp;
    }

    /**
     * Sets the value of the majLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajLopp(XMLGregorianCalendar value) {
        this.majLopp = value;
    }

    /**
     * Gets the value of the ehak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEhak() {
        return ehak;
    }

    /**
     * Sets the value of the ehak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEhak(String value) {
        this.ehak = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadress() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadress(String value) {
        this.aadress = value;
    }

    /**
     * Gets the value of the postiind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostiind() {
        return postiind;
    }

    /**
     * Sets the value of the postiind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostiind(String value) {
        this.postiind = value;
    }

    /**
     * Gets the value of the ptegEmtak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtegEmtak() {
        return ptegEmtak;
    }

    /**
     * Sets the value of the ptegEmtak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtegEmtak(String value) {
        this.ptegEmtak = value;
    }

    /**
     * Gets the value of the ptegEmtakVersioon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtegEmtakVersioon() {
        return ptegEmtakVersioon;
    }

    /**
     * Sets the value of the ptegEmtakVersioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtegEmtakVersioon(String value) {
        this.ptegEmtakVersioon = value;
    }

    /**
     * Gets the value of the esitatud property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitatud() {
        return esitatud;
    }

    /**
     * Sets the value of the esitatud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitatud(XMLGregorianCalendar value) {
        this.esitatud = value;
    }

    /**
     * Gets the value of the ark property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getArk() {
        return ark;
    }

    /**
     * Sets the value of the ark property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setArk(BigInteger value) {
        this.ark = value;
    }

    /**
     * Gets the value of the tootajateArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTootajateArv() {
        return tootajateArv;
    }

    /**
     * Sets the value of the tootajateArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTootajateArv(BigInteger value) {
        this.tootajateArv = value;
    }

    /**
     * Gets the value of the kaive property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKaive() {
        return kaive;
    }

    /**
     * Sets the value of the kaive property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKaive(BigInteger value) {
        this.kaive = value;
    }

    /**
     * Gets the value of the kasum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKasum() {
        return kasum;
    }

    /**
     * Sets the value of the kasum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKasum(BigInteger value) {
        this.kasum = value;
    }

    /**
     * Gets the value of the bilansimaht property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBilansimaht() {
        return bilansimaht;
    }

    /**
     * Sets the value of the bilansimaht property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBilansimaht(BigInteger value) {
        this.bilansimaht = value;
    }

    /**
     * Gets the value of the konsolTootajateArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKonsolTootajateArv() {
        return konsolTootajateArv;
    }

    /**
     * Sets the value of the konsolTootajateArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKonsolTootajateArv(BigInteger value) {
        this.konsolTootajateArv = value;
    }

    /**
     * Gets the value of the konsolKaive property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKonsolKaive() {
        return konsolKaive;
    }

    /**
     * Sets the value of the konsolKaive property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKonsolKaive(BigInteger value) {
        this.konsolKaive = value;
    }

    /**
     * Gets the value of the konsolKasum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKonsolKasum() {
        return konsolKasum;
    }

    /**
     * Sets the value of the konsolKasum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKonsolKasum(BigInteger value) {
        this.konsolKasum = value;
    }

    /**
     * Gets the value of the konsolBilansimaht property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKonsolBilansimaht() {
        return konsolBilansimaht;
    }

    /**
     * Sets the value of the konsolBilansimaht property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKonsolBilansimaht(BigInteger value) {
        this.konsolBilansimaht = value;
    }

    /**
     * Gets the value of the valuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuuta() {
        return valuuta;
    }

    /**
     * Sets the value of the valuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuuta(String value) {
        this.valuuta = value;
    }

    /**
     * Gets the value of the esitamiseViis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitamiseViis() {
        return esitamiseViis;
    }

    /**
     * Sets the value of the esitamiseViis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitamiseViis(String value) {
        this.esitamiseViis = value;
    }

    /**
     * Gets the value of the auditeerimiskohustus property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAuditeerimiskohustus() {
        return auditeerimiskohustus;
    }

    /**
     * Sets the value of the auditeerimiskohustus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAuditeerimiskohustus(Boolean value) {
        this.auditeerimiskohustus = value;
    }

    /**
     * Gets the value of the auditeeritus property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAuditeeritus() {
        return auditeeritus;
    }

    /**
     * Sets the value of the auditeeritus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAuditeeritus(Boolean value) {
        this.auditeeritus = value;
    }

    /**
     * Gets the value of the bilansiSisud property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlAruandeBilansiSisud }
     *     
     */
    public XbrlAruandeBilansiSisud getBilansiSisud() {
        return bilansiSisud;
    }

    /**
     * Sets the value of the bilansiSisud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlAruandeBilansiSisud }
     *     
     */
    public void setBilansiSisud(XbrlAruandeBilansiSisud value) {
        this.bilansiSisud = value;
    }

    /**
     * Gets the value of the ainultAudiitoridJaEmtak property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAinultAudiitoridJaEmtak() {
        return ainultAudiitoridJaEmtak;
    }

    /**
     * Sets the value of the ainultAudiitoridJaEmtak property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAinultAudiitoridJaEmtak(Boolean value) {
        this.ainultAudiitoridJaEmtak = value;
    }

    /**
     * Gets the value of the kohustuslikAudit property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKohustuslikAudit() {
        return kohustuslikAudit;
    }

    /**
     * Sets the value of the kohustuslikAudit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKohustuslikAudit(Boolean value) {
        this.kohustuslikAudit = value;
    }

    /**
     * Gets the value of the audiitorid property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlAruandeAudiitorid }
     *     
     */
    public XbrlAruandeAudiitorid getAudiitorid() {
        return audiitorid;
    }

    /**
     * Sets the value of the audiitorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlAruandeAudiitorid }
     *     
     */
    public void setAudiitorid(XbrlAruandeAudiitorid value) {
        this.audiitorid = value;
    }

    /**
     * Gets the value of the activityOccured property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getActivityOccured() {
        return activityOccured;
    }

    /**
     * Sets the value of the activityOccured property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setActivityOccured(BigInteger value) {
        this.activityOccured = value;
    }

    /**
     * Gets the value of the myygitulu property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlAruandeMyygitulud }
     *     
     */
    public XbrlAruandeMyygitulud getMyygitulu() {
        return myygitulu;
    }

    /**
     * Sets the value of the myygitulu property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlAruandeMyygitulud }
     *     
     */
    public void setMyygitulu(XbrlAruandeMyygitulud value) {
        this.myygitulu = value;
    }

}
