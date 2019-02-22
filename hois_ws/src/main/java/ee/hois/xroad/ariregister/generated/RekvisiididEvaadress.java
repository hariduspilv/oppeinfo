
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rekvisiidid_evaadress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rekvisiidid_evaadress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asukoht_ettevotja_aadressis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="asukoha_ehak_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="asukoha_ehak_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="indeks_ettevotja_aadressis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ads_adr_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ads_normaliseeritud_taisaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ads_adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ads_koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ads_normaliseeritud_taisaadress_tapsustus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ads_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rekvisiidid_evaadress", propOrder = {
    "asukohtEttevotjaAadressis",
    "asukohaEhakKood",
    "asukohaEhakTekstina",
    "indeksEttevotjaAadressis",
    "adsAdrId",
    "adsOid",
    "adsNormaliseeritudTaisaadress",
    "adsAdobId",
    "adsKoodaadress",
    "adsNormaliseeritudTaisaadressTapsustus",
    "adsTyyp"
})
public class RekvisiididEvaadress {

    @XmlElement(name = "asukoht_ettevotja_aadressis", required = true, nillable = true)
    protected String asukohtEttevotjaAadressis;
    @XmlElement(name = "asukoha_ehak_kood", required = true, nillable = true)
    protected String asukohaEhakKood;
    @XmlElement(name = "asukoha_ehak_tekstina", required = true, nillable = true)
    protected String asukohaEhakTekstina;
    @XmlElement(name = "indeks_ettevotja_aadressis", required = true, nillable = true)
    protected String indeksEttevotjaAadressis;
    @XmlElement(name = "ads_adr_id")
    protected String adsAdrId;
    @XmlElement(name = "ads_oid")
    protected String adsOid;
    @XmlElement(name = "ads_normaliseeritud_taisaadress")
    protected String adsNormaliseeritudTaisaadress;
    @XmlElement(name = "ads_adob_id")
    protected String adsAdobId;
    @XmlElement(name = "ads_koodaadress")
    protected String adsKoodaadress;
    @XmlElement(name = "ads_normaliseeritud_taisaadress_tapsustus")
    protected String adsNormaliseeritudTaisaadressTapsustus;
    @XmlElement(name = "ads_tyyp")
    protected String adsTyyp;

    /**
     * Gets the value of the asukohtEttevotjaAadressis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsukohtEttevotjaAadressis() {
        return asukohtEttevotjaAadressis;
    }

    /**
     * Sets the value of the asukohtEttevotjaAadressis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsukohtEttevotjaAadressis(String value) {
        this.asukohtEttevotjaAadressis = value;
    }

    /**
     * Gets the value of the asukohaEhakKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsukohaEhakKood() {
        return asukohaEhakKood;
    }

    /**
     * Sets the value of the asukohaEhakKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsukohaEhakKood(String value) {
        this.asukohaEhakKood = value;
    }

    /**
     * Gets the value of the asukohaEhakTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsukohaEhakTekstina() {
        return asukohaEhakTekstina;
    }

    /**
     * Sets the value of the asukohaEhakTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsukohaEhakTekstina(String value) {
        this.asukohaEhakTekstina = value;
    }

    /**
     * Gets the value of the indeksEttevotjaAadressis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndeksEttevotjaAadressis() {
        return indeksEttevotjaAadressis;
    }

    /**
     * Sets the value of the indeksEttevotjaAadressis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndeksEttevotjaAadressis(String value) {
        this.indeksEttevotjaAadressis = value;
    }

    /**
     * Gets the value of the adsAdrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdsAdrId() {
        return adsAdrId;
    }

    /**
     * Sets the value of the adsAdrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdsAdrId(String value) {
        this.adsAdrId = value;
    }

    /**
     * Gets the value of the adsOid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdsOid() {
        return adsOid;
    }

    /**
     * Sets the value of the adsOid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdsOid(String value) {
        this.adsOid = value;
    }

    /**
     * Gets the value of the adsNormaliseeritudTaisaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdsNormaliseeritudTaisaadress() {
        return adsNormaliseeritudTaisaadress;
    }

    /**
     * Sets the value of the adsNormaliseeritudTaisaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdsNormaliseeritudTaisaadress(String value) {
        this.adsNormaliseeritudTaisaadress = value;
    }

    /**
     * Gets the value of the adsAdobId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdsAdobId() {
        return adsAdobId;
    }

    /**
     * Sets the value of the adsAdobId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdsAdobId(String value) {
        this.adsAdobId = value;
    }

    /**
     * Gets the value of the adsKoodaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdsKoodaadress() {
        return adsKoodaadress;
    }

    /**
     * Sets the value of the adsKoodaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdsKoodaadress(String value) {
        this.adsKoodaadress = value;
    }

    /**
     * Gets the value of the adsNormaliseeritudTaisaadressTapsustus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdsNormaliseeritudTaisaadressTapsustus() {
        return adsNormaliseeritudTaisaadressTapsustus;
    }

    /**
     * Sets the value of the adsNormaliseeritudTaisaadressTapsustus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdsNormaliseeritudTaisaadressTapsustus(String value) {
        this.adsNormaliseeritudTaisaadressTapsustus = value;
    }

    /**
     * Gets the value of the adsTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdsTyyp() {
        return adsTyyp;
    }

    /**
     * Sets the value of the adsTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdsTyyp(String value) {
        this.adsTyyp = value;
    }

}
