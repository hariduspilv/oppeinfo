
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_pandipidaja complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_pandipidaja"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="pandipidaja_isiku_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandipidaja_isikukood_registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandipidaja_valis_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandipidaja_synniaeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="pandipidaja_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandipidaja_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_riik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ehak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ehak_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__adr_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__ads_normaliseeritud_taisaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__ads_normaliseeritud_taisaadress_tapsustus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omandiliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omandiliik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="murdosa_lugeja" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="murdosa_nimetaja" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="algus_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="lopp_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_pandipidaja", propOrder = {
    "kirjeId",
    "pandipidajaIsikuLiik",
    "pandipidajaIsikukoodRegistrikood",
    "pandipidajaValisKood",
    "pandipidajaSynniaeg",
    "pandipidajaNimi",
    "pandipidajaEesnimi",
    "aadressRiik",
    "aadressRiikTekstina",
    "aadressEhak",
    "aadressEhakTekstina",
    "aadressAdsAdsOid",
    "aadressAdsAdrId",
    "aadressAdsAdsNormaliseeritudTaisaadress",
    "aadressAdsAdobId",
    "aadressAdsKoodaadress",
    "aadressAdsAdsNormaliseeritudTaisaadressTapsustus",
    "aadressAdsTyyp",
    "omandiliik",
    "omandiliikTekstina",
    "murdosaLugeja",
    "murdosaNimetaja",
    "algusKpv",
    "loppKpv"
})
public class DetailandmedV5Pandipidaja {

    @XmlElement(name = "kirje_id")
    protected BigInteger kirjeId;
    @XmlElement(name = "pandipidaja_isiku_liik")
    protected String pandipidajaIsikuLiik;
    @XmlElement(name = "pandipidaja_isikukood_registrikood")
    protected String pandipidajaIsikukoodRegistrikood;
    @XmlElement(name = "pandipidaja_valis_kood")
    protected String pandipidajaValisKood;
    @XmlElement(name = "pandipidaja_synniaeg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar pandipidajaSynniaeg;
    @XmlElement(name = "pandipidaja_nimi")
    protected String pandipidajaNimi;
    @XmlElement(name = "pandipidaja_eesnimi")
    protected String pandipidajaEesnimi;
    @XmlElement(name = "aadress_riik")
    protected String aadressRiik;
    @XmlElement(name = "aadress_riik_tekstina")
    protected String aadressRiikTekstina;
    @XmlElement(name = "aadress_ehak")
    protected String aadressEhak;
    @XmlElement(name = "aadress_ehak_tekstina")
    protected String aadressEhakTekstina;
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
    protected String omandiliik;
    @XmlElement(name = "omandiliik_tekstina")
    protected String omandiliikTekstina;
    @XmlElement(name = "murdosa_lugeja")
    protected BigInteger murdosaLugeja;
    @XmlElement(name = "murdosa_nimetaja")
    protected BigInteger murdosaNimetaja;
    @XmlElement(name = "algus_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKpv;
    @XmlElement(name = "lopp_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKpv;

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
     * Gets the value of the pandipidajaIsikuLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandipidajaIsikuLiik() {
        return pandipidajaIsikuLiik;
    }

    /**
     * Sets the value of the pandipidajaIsikuLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandipidajaIsikuLiik(String value) {
        this.pandipidajaIsikuLiik = value;
    }

    /**
     * Gets the value of the pandipidajaIsikukoodRegistrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandipidajaIsikukoodRegistrikood() {
        return pandipidajaIsikukoodRegistrikood;
    }

    /**
     * Sets the value of the pandipidajaIsikukoodRegistrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandipidajaIsikukoodRegistrikood(String value) {
        this.pandipidajaIsikukoodRegistrikood = value;
    }

    /**
     * Gets the value of the pandipidajaValisKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandipidajaValisKood() {
        return pandipidajaValisKood;
    }

    /**
     * Sets the value of the pandipidajaValisKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandipidajaValisKood(String value) {
        this.pandipidajaValisKood = value;
    }

    /**
     * Gets the value of the pandipidajaSynniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPandipidajaSynniaeg() {
        return pandipidajaSynniaeg;
    }

    /**
     * Sets the value of the pandipidajaSynniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPandipidajaSynniaeg(XMLGregorianCalendar value) {
        this.pandipidajaSynniaeg = value;
    }

    /**
     * Gets the value of the pandipidajaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandipidajaNimi() {
        return pandipidajaNimi;
    }

    /**
     * Sets the value of the pandipidajaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandipidajaNimi(String value) {
        this.pandipidajaNimi = value;
    }

    /**
     * Gets the value of the pandipidajaEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandipidajaEesnimi() {
        return pandipidajaEesnimi;
    }

    /**
     * Sets the value of the pandipidajaEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandipidajaEesnimi(String value) {
        this.pandipidajaEesnimi = value;
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
     * Gets the value of the omandiliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandiliik() {
        return omandiliik;
    }

    /**
     * Sets the value of the omandiliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandiliik(String value) {
        this.omandiliik = value;
    }

    /**
     * Gets the value of the omandiliikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandiliikTekstina() {
        return omandiliikTekstina;
    }

    /**
     * Sets the value of the omandiliikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandiliikTekstina(String value) {
        this.omandiliikTekstina = value;
    }

    /**
     * Gets the value of the murdosaLugeja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMurdosaLugeja() {
        return murdosaLugeja;
    }

    /**
     * Sets the value of the murdosaLugeja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMurdosaLugeja(BigInteger value) {
        this.murdosaLugeja = value;
    }

    /**
     * Gets the value of the murdosaNimetaja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMurdosaNimetaja() {
        return murdosaNimetaja;
    }

    /**
     * Sets the value of the murdosaNimetaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMurdosaNimetaja(BigInteger value) {
        this.murdosaNimetaja = value;
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

}
