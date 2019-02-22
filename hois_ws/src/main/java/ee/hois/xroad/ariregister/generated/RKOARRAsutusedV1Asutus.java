
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RKOARRAsutused_v1_Asutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RKOARRAsutused_v1_Asutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oig_vorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oig_vorm_alal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aadress_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadres_ehak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_adr_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads_normaliseeritud_taisaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_ads_normaliseeritud_taisaadress_tapsustus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="evreg_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="korgemalseisev_asutus" type="{http://arireg.x-road.eu/producer/}RKOARRAsutused_v1_KOAS" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="sidevahend" type="{http://arireg.x-road.eu/producer/}RKOARRAsutused_v1_Sidevahend" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RKOARRAsutused_v1_Asutus", propOrder = {
    "kood",
    "nimi",
    "oigVorm",
    "oigVormAlal",
    "olek",
    "aadressRiik",
    "aadresEhak",
    "aadressTekst",
    "aadressPostiindeks",
    "aadressAdrId",
    "aadressAdsOid",
    "aadressAdsNormaliseeritudTaisaadress",
    "aadressAdobId",
    "aadressKoodaadress",
    "aadressAdsNormaliseeritudTaisaadressTapsustus",
    "aadressTyyp",
    "evregKood",
    "korgemalseisevAsutus",
    "sidevahend"
})
public class RKOARRAsutusedV1Asutus {

    @XmlElement(required = true)
    protected BigInteger kood;
    @XmlElement(required = true)
    protected String nimi;
    @XmlElement(name = "oig_vorm", required = true)
    protected String oigVorm;
    @XmlElement(name = "oig_vorm_alal")
    protected String oigVormAlal;
    @XmlElement(required = true)
    protected String olek;
    @XmlElement(name = "aadress_riik")
    protected String aadressRiik;
    @XmlElement(name = "aadres_ehak")
    protected String aadresEhak;
    @XmlElement(name = "aadress_tekst")
    protected String aadressTekst;
    @XmlElement(name = "aadress_postiindeks")
    protected String aadressPostiindeks;
    @XmlElement(name = "aadress_adr_id")
    protected BigInteger aadressAdrId;
    @XmlElement(name = "aadress_ads_oid")
    protected String aadressAdsOid;
    @XmlElement(name = "aadress_ads_normaliseeritud_taisaadress")
    protected String aadressAdsNormaliseeritudTaisaadress;
    @XmlElement(name = "aadress_adob_id")
    protected String aadressAdobId;
    @XmlElement(name = "aadress_koodaadress")
    protected String aadressKoodaadress;
    @XmlElement(name = "aadress_ads_normaliseeritud_taisaadress_tapsustus")
    protected String aadressAdsNormaliseeritudTaisaadressTapsustus;
    @XmlElement(name = "aadress_tyyp")
    protected String aadressTyyp;
    @XmlElement(name = "evreg_kood")
    protected String evregKood;
    @XmlElement(name = "korgemalseisev_asutus")
    protected List<RKOARRAsutusedV1KOAS> korgemalseisevAsutus;
    protected List<RKOARRAsutusedV1Sidevahend> sidevahend;

    /**
     * Gets the value of the kood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKood() {
        return kood;
    }

    /**
     * Sets the value of the kood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKood(BigInteger value) {
        this.kood = value;
    }

    /**
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

    /**
     * Gets the value of the oigVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOigVorm() {
        return oigVorm;
    }

    /**
     * Sets the value of the oigVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOigVorm(String value) {
        this.oigVorm = value;
    }

    /**
     * Gets the value of the oigVormAlal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOigVormAlal() {
        return oigVormAlal;
    }

    /**
     * Sets the value of the oigVormAlal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOigVormAlal(String value) {
        this.oigVormAlal = value;
    }

    /**
     * Gets the value of the olek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOlek() {
        return olek;
    }

    /**
     * Sets the value of the olek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOlek(String value) {
        this.olek = value;
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
     * Gets the value of the aadresEhak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadresEhak() {
        return aadresEhak;
    }

    /**
     * Sets the value of the aadresEhak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadresEhak(String value) {
        this.aadresEhak = value;
    }

    /**
     * Gets the value of the aadressTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressTekst() {
        return aadressTekst;
    }

    /**
     * Sets the value of the aadressTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressTekst(String value) {
        this.aadressTekst = value;
    }

    /**
     * Gets the value of the aadressPostiindeks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressPostiindeks() {
        return aadressPostiindeks;
    }

    /**
     * Sets the value of the aadressPostiindeks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressPostiindeks(String value) {
        this.aadressPostiindeks = value;
    }

    /**
     * Gets the value of the aadressAdrId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAadressAdrId() {
        return aadressAdrId;
    }

    /**
     * Sets the value of the aadressAdrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAadressAdrId(BigInteger value) {
        this.aadressAdrId = value;
    }

    /**
     * Gets the value of the aadressAdsOid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsOid() {
        return aadressAdsOid;
    }

    /**
     * Sets the value of the aadressAdsOid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsOid(String value) {
        this.aadressAdsOid = value;
    }

    /**
     * Gets the value of the aadressAdsNormaliseeritudTaisaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsNormaliseeritudTaisaadress() {
        return aadressAdsNormaliseeritudTaisaadress;
    }

    /**
     * Sets the value of the aadressAdsNormaliseeritudTaisaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsNormaliseeritudTaisaadress(String value) {
        this.aadressAdsNormaliseeritudTaisaadress = value;
    }

    /**
     * Gets the value of the aadressAdobId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdobId() {
        return aadressAdobId;
    }

    /**
     * Sets the value of the aadressAdobId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdobId(String value) {
        this.aadressAdobId = value;
    }

    /**
     * Gets the value of the aadressKoodaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressKoodaadress() {
        return aadressKoodaadress;
    }

    /**
     * Sets the value of the aadressKoodaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressKoodaadress(String value) {
        this.aadressKoodaadress = value;
    }

    /**
     * Gets the value of the aadressAdsNormaliseeritudTaisaadressTapsustus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressAdsNormaliseeritudTaisaadressTapsustus() {
        return aadressAdsNormaliseeritudTaisaadressTapsustus;
    }

    /**
     * Sets the value of the aadressAdsNormaliseeritudTaisaadressTapsustus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressAdsNormaliseeritudTaisaadressTapsustus(String value) {
        this.aadressAdsNormaliseeritudTaisaadressTapsustus = value;
    }

    /**
     * Gets the value of the aadressTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressTyyp() {
        return aadressTyyp;
    }

    /**
     * Sets the value of the aadressTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressTyyp(String value) {
        this.aadressTyyp = value;
    }

    /**
     * Gets the value of the evregKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvregKood() {
        return evregKood;
    }

    /**
     * Sets the value of the evregKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvregKood(String value) {
        this.evregKood = value;
    }

    /**
     * Gets the value of the korgemalseisevAsutus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the korgemalseisevAsutus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKorgemalseisevAsutus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RKOARRAsutusedV1KOAS }
     * 
     * 
     */
    public List<RKOARRAsutusedV1KOAS> getKorgemalseisevAsutus() {
        if (korgemalseisevAsutus == null) {
            korgemalseisevAsutus = new ArrayList<RKOARRAsutusedV1KOAS>();
        }
        return this.korgemalseisevAsutus;
    }

    /**
     * Gets the value of the sidevahend property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sidevahend property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSidevahend().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RKOARRAsutusedV1Sidevahend }
     * 
     * 
     */
    public List<RKOARRAsutusedV1Sidevahend> getSidevahend() {
        if (sidevahend == null) {
            sidevahend = new ArrayList<RKOARRAsutusedV1Sidevahend>();
        }
        return this.sidevahend;
    }

}
