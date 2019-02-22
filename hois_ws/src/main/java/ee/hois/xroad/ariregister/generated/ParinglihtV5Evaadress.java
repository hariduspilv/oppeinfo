
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringliht_v5_evaadress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringliht_v5_evaadress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asukoht_ettevotja_aadressis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="asukoha_ehak_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="asukoha_ehak_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="indeks_ettevotja_aadressis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aadress_ads__ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__adr_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__ads_normaliseeritud_taisaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__ads_normaliseeritud_taisaadress_tapsustus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads__tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringliht_v5_evaadress", propOrder = {
    "asukohtEttevotjaAadressis",
    "asukohaEhakKood",
    "asukohaEhakTekstina",
    "indeksEttevotjaAadressis",
    "aadressAdsAdsOid",
    "aadressAdsAdrId",
    "aadressAdsAdsNormaliseeritudTaisaadress",
    "aadressAdsAdobId",
    "aadressAdsKoodaadress",
    "aadressAdsAdsNormaliseeritudTaisaadressTapsustus",
    "aadressAdsTyyp"
})
public class ParinglihtV5Evaadress {

    @XmlElement(name = "asukoht_ettevotja_aadressis", required = true, nillable = true)
    protected String asukohtEttevotjaAadressis;
    @XmlElement(name = "asukoha_ehak_kood", required = true, nillable = true)
    protected String asukohaEhakKood;
    @XmlElement(name = "asukoha_ehak_tekstina", required = true, nillable = true)
    protected String asukohaEhakTekstina;
    @XmlElement(name = "indeks_ettevotja_aadressis", required = true, nillable = true)
    protected String indeksEttevotjaAadressis;
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

}
