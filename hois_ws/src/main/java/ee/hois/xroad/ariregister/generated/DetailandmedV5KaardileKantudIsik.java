
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_kaardile_kantud_isik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_kaardile_kantud_isik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_piirkond" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="isiku_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isiku_roll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isiku_roll_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimi_arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukood_registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valis_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valis_kood_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valis_kood_riik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="synniaeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="osamaks" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="osamaksu_valuuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="osamaksu_valuuta_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="volituste_loppemise_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="aadress_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_riik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ehak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ehak_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_tanav_maja_korter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__adr_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__ads_normaliseeritud_taisaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__ads_normaliseeritud_taisaadress_tapsustus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="algus_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="lopp_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_kaardile_kantud_isik", propOrder = {
    "kirjeId",
    "kaardiPiirkond",
    "kaardiNr",
    "kaardiTyyp",
    "kandeNr",
    "isikuTyyp",
    "isikuRoll",
    "isikuRollTekstina",
    "eesnimi",
    "nimiArinimi",
    "isikukoodRegistrikood",
    "valisKood",
    "valisKoodRiik",
    "valisKoodRiikTekstina",
    "synniaeg",
    "osamaks",
    "osamaksuValuuta",
    "osamaksuValuutaTekstina",
    "volitusteLoppemiseKpv",
    "aadressRiik",
    "aadressRiikTekstina",
    "aadressEhak",
    "aadressEhakTekstina",
    "aadressTanavMajaKorter",
    "aadressAdsAdsOid",
    "aadressAdsAdrId",
    "aadressAdsAdsNormaliseeritudTaisaadress",
    "aadressAdsAdobId",
    "aadressAdsKoodaadress",
    "aadressAdsAdsNormaliseeritudTaisaadressTapsustus",
    "aadressAdsTyyp",
    "algusKpv",
    "loppKpv",
    "email"
})
public class DetailandmedV5KaardileKantudIsik {

    @XmlElement(name = "kirje_id")
    protected BigInteger kirjeId;
    @XmlElement(name = "kaardi_piirkond")
    protected BigInteger kaardiPiirkond;
    @XmlElement(name = "kaardi_nr")
    protected BigInteger kaardiNr;
    @XmlElement(name = "kaardi_tyyp")
    protected String kaardiTyyp;
    @XmlElement(name = "kande_nr")
    protected BigInteger kandeNr;
    @XmlElement(name = "isiku_tyyp")
    protected String isikuTyyp;
    @XmlElement(name = "isiku_roll")
    protected String isikuRoll;
    @XmlElement(name = "isiku_roll_tekstina")
    protected String isikuRollTekstina;
    protected String eesnimi;
    @XmlElement(name = "nimi_arinimi")
    protected String nimiArinimi;
    @XmlElement(name = "isikukood_registrikood")
    protected String isikukoodRegistrikood;
    @XmlElement(name = "valis_kood")
    protected String valisKood;
    @XmlElement(name = "valis_kood_riik")
    protected String valisKoodRiik;
    @XmlElement(name = "valis_kood_riik_tekstina")
    protected String valisKoodRiikTekstina;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniaeg;
    protected BigDecimal osamaks;
    @XmlElement(name = "osamaksu_valuuta")
    protected String osamaksuValuuta;
    @XmlElement(name = "osamaksu_valuuta_tekstina")
    protected String osamaksuValuutaTekstina;
    @XmlElement(name = "volituste_loppemise_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar volitusteLoppemiseKpv;
    @XmlElement(name = "aadress_riik")
    protected String aadressRiik;
    @XmlElement(name = "aadress_riik_tekstina")
    protected String aadressRiikTekstina;
    @XmlElement(name = "aadress_ehak")
    protected String aadressEhak;
    @XmlElement(name = "aadress_ehak_tekstina")
    protected String aadressEhakTekstina;
    @XmlElement(name = "aadress_tanav_maja_korter")
    protected String aadressTanavMajaKorter;
    @XmlElement(name = "aadress_ads__ads_oid")
    protected String aadressAdsAdsOid;
    @XmlElement(name = "aadress_ads__adr_id")
    protected String aadressAdsAdrId;
    @XmlElement(name = "aadress_ads__ads_normaliseeritud_taisaadress")
    protected String aadressAdsAdsNormaliseeritudTaisaadress;
    @XmlElement(name = "aadress_ads__adob_id")
    protected String aadressAdsAdobId;
    @XmlElement(name = "aadress_ads__koodaadress")
    protected String aadressAdsKoodaadress;
    @XmlElement(name = "aadress_ads__ads_normaliseeritud_taisaadress_tapsustus")
    protected String aadressAdsAdsNormaliseeritudTaisaadressTapsustus;
    @XmlElement(name = "aadress_ads__tyyp")
    protected String aadressAdsTyyp;
    @XmlElement(name = "algus_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKpv;
    @XmlElement(name = "lopp_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKpv;
    protected String email;

    /**
     * Gets the value of the kirjeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKirjeId() {
        return kirjeId;
    }

    /**
     * Sets the value of the kirjeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKirjeId(BigInteger value) {
        this.kirjeId = value;
    }

    /**
     * Gets the value of the kaardiPiirkond property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKaardiPiirkond() {
        return kaardiPiirkond;
    }

    /**
     * Sets the value of the kaardiPiirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKaardiPiirkond(BigInteger value) {
        this.kaardiPiirkond = value;
    }

    /**
     * Gets the value of the kaardiNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKaardiNr() {
        return kaardiNr;
    }

    /**
     * Sets the value of the kaardiNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKaardiNr(BigInteger value) {
        this.kaardiNr = value;
    }

    /**
     * Gets the value of the kaardiTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKaardiTyyp() {
        return kaardiTyyp;
    }

    /**
     * Sets the value of the kaardiTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKaardiTyyp(String value) {
        this.kaardiTyyp = value;
    }

    /**
     * Gets the value of the kandeNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKandeNr() {
        return kandeNr;
    }

    /**
     * Sets the value of the kandeNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKandeNr(BigInteger value) {
        this.kandeNr = value;
    }

    /**
     * Gets the value of the isikuTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuTyyp() {
        return isikuTyyp;
    }

    /**
     * Sets the value of the isikuTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuTyyp(String value) {
        this.isikuTyyp = value;
    }

    /**
     * Gets the value of the isikuRoll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuRoll() {
        return isikuRoll;
    }

    /**
     * Sets the value of the isikuRoll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuRoll(String value) {
        this.isikuRoll = value;
    }

    /**
     * Gets the value of the isikuRollTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuRollTekstina() {
        return isikuRollTekstina;
    }

    /**
     * Sets the value of the isikuRollTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuRollTekstina(String value) {
        this.isikuRollTekstina = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the nimiArinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimiArinimi() {
        return nimiArinimi;
    }

    /**
     * Sets the value of the nimiArinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimiArinimi(String value) {
        this.nimiArinimi = value;
    }

    /**
     * Gets the value of the isikukoodRegistrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukoodRegistrikood() {
        return isikukoodRegistrikood;
    }

    /**
     * Sets the value of the isikukoodRegistrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukoodRegistrikood(String value) {
        this.isikukoodRegistrikood = value;
    }

    /**
     * Gets the value of the valisKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValisKood() {
        return valisKood;
    }

    /**
     * Sets the value of the valisKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValisKood(String value) {
        this.valisKood = value;
    }

    /**
     * Gets the value of the valisKoodRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValisKoodRiik() {
        return valisKoodRiik;
    }

    /**
     * Sets the value of the valisKoodRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValisKoodRiik(String value) {
        this.valisKoodRiik = value;
    }

    /**
     * Gets the value of the valisKoodRiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValisKoodRiikTekstina() {
        return valisKoodRiikTekstina;
    }

    /**
     * Sets the value of the valisKoodRiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValisKoodRiikTekstina(String value) {
        this.valisKoodRiikTekstina = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynniaeg(XMLGregorianCalendar value) {
        this.synniaeg = value;
    }

    /**
     * Gets the value of the osamaks property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOsamaks() {
        return osamaks;
    }

    /**
     * Sets the value of the osamaks property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOsamaks(BigDecimal value) {
        this.osamaks = value;
    }

    /**
     * Gets the value of the osamaksuValuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOsamaksuValuuta() {
        return osamaksuValuuta;
    }

    /**
     * Sets the value of the osamaksuValuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOsamaksuValuuta(String value) {
        this.osamaksuValuuta = value;
    }

    /**
     * Gets the value of the osamaksuValuutaTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOsamaksuValuutaTekstina() {
        return osamaksuValuutaTekstina;
    }

    /**
     * Sets the value of the osamaksuValuutaTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOsamaksuValuutaTekstina(String value) {
        this.osamaksuValuutaTekstina = value;
    }

    /**
     * Gets the value of the volitusteLoppemiseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVolitusteLoppemiseKpv() {
        return volitusteLoppemiseKpv;
    }

    /**
     * Sets the value of the volitusteLoppemiseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVolitusteLoppemiseKpv(XMLGregorianCalendar value) {
        this.volitusteLoppemiseKpv = value;
    }

    /**
     * Gets the value of the aadressRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressRiik() {
        return aadressRiik;
    }

    /**
     * Sets the value of the aadressRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressRiik(String value) {
        this.aadressRiik = value;
    }

    /**
     * Gets the value of the aadressRiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressRiikTekstina() {
        return aadressRiikTekstina;
    }

    /**
     * Sets the value of the aadressRiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressRiikTekstina(String value) {
        this.aadressRiikTekstina = value;
    }

    /**
     * Gets the value of the aadressEhak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressEhak() {
        return aadressEhak;
    }

    /**
     * Sets the value of the aadressEhak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressEhak(String value) {
        this.aadressEhak = value;
    }

    /**
     * Gets the value of the aadressEhakTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressEhakTekstina() {
        return aadressEhakTekstina;
    }

    /**
     * Sets the value of the aadressEhakTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressEhakTekstina(String value) {
        this.aadressEhakTekstina = value;
    }

    /**
     * Gets the value of the aadressTanavMajaKorter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressTanavMajaKorter() {
        return aadressTanavMajaKorter;
    }

    /**
     * Sets the value of the aadressTanavMajaKorter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressTanavMajaKorter(String value) {
        this.aadressTanavMajaKorter = value;
    }

    /**
     * Gets the value of the aadressAdsAdsOid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsAdsOid() {
        return aadressAdsAdsOid;
    }

    /**
     * Sets the value of the aadressAdsAdsOid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsAdsOid(String value) {
        this.aadressAdsAdsOid = value;
    }

    /**
     * Gets the value of the aadressAdsAdrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsAdrId() {
        return aadressAdsAdrId;
    }

    /**
     * Sets the value of the aadressAdsAdrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsAdrId(String value) {
        this.aadressAdsAdrId = value;
    }

    /**
     * Gets the value of the aadressAdsAdsNormaliseeritudTaisaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsAdsNormaliseeritudTaisaadress() {
        return aadressAdsAdsNormaliseeritudTaisaadress;
    }

    /**
     * Sets the value of the aadressAdsAdsNormaliseeritudTaisaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsAdsNormaliseeritudTaisaadress(String value) {
        this.aadressAdsAdsNormaliseeritudTaisaadress = value;
    }

    /**
     * Gets the value of the aadressAdsAdobId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsAdobId() {
        return aadressAdsAdobId;
    }

    /**
     * Sets the value of the aadressAdsAdobId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsAdobId(String value) {
        this.aadressAdsAdobId = value;
    }

    /**
     * Gets the value of the aadressAdsKoodaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsKoodaadress() {
        return aadressAdsKoodaadress;
    }

    /**
     * Sets the value of the aadressAdsKoodaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsKoodaadress(String value) {
        this.aadressAdsKoodaadress = value;
    }

    /**
     * Gets the value of the aadressAdsAdsNormaliseeritudTaisaadressTapsustus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsAdsNormaliseeritudTaisaadressTapsustus() {
        return aadressAdsAdsNormaliseeritudTaisaadressTapsustus;
    }

    /**
     * Sets the value of the aadressAdsAdsNormaliseeritudTaisaadressTapsustus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsAdsNormaliseeritudTaisaadressTapsustus(String value) {
        this.aadressAdsAdsNormaliseeritudTaisaadressTapsustus = value;
    }

    /**
     * Gets the value of the aadressAdsTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsTyyp() {
        return aadressAdsTyyp;
    }

    /**
     * Sets the value of the aadressAdsTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsTyyp(String value) {
        this.aadressAdsTyyp = value;
    }

    /**
     * Gets the value of the algusKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKpv() {
        return algusKpv;
    }

    /**
     * Sets the value of the algusKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKpv(XMLGregorianCalendar value) {
        this.algusKpv = value;
    }

    /**
     * Gets the value of the loppKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKpv() {
        return loppKpv;
    }

    /**
     * Sets the value of the loppKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKpv(XMLGregorianCalendar value) {
        this.loppKpv = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
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
    public void setEmail(String value) {
        this.email = value;
    }

}
