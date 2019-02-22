
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aadresskatastrigaType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aadresskatastrigaType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asukoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ehak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="adr_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="tase6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tase7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tase8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="katastriyksus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="taisaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="taisaadress_tapsustus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aadresskatastrigaType_v4", propOrder = {
    "asukoht",
    "ehak",
    "riik",
    "postiindeks",
    "adobId",
    "adrId",
    "tase6",
    "tase7",
    "tase8",
    "adsOid",
    "koodaadress",
    "katastriyksus",
    "taisaadress",
    "taisaadressTapsustus",
    "tyyp"
})
public class AadresskatastrigaTypeV4 {

    protected String asukoht;
    protected String ehak;
    @XmlElementRef(name = "riik", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> riik;
    protected String postiindeks;
    @XmlElement(name = "adob_id")
    protected String adobId;
    @XmlElement(name = "adr_id")
    protected Integer adrId;
    protected String tase6;
    protected String tase7;
    protected String tase8;
    @XmlElement(name = "ads_oid")
    protected String adsOid;
    protected String koodaadress;
    protected String katastriyksus;
    protected String taisaadress;
    @XmlElement(name = "taisaadress_tapsustus")
    protected String taisaadressTapsustus;
    protected String tyyp;

    /**
     * Gets the value of the asukoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsukoht() {
        return asukoht;
    }

    /**
     * Sets the value of the asukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsukoht(String value) {
        this.asukoht = value;
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
     * Gets the value of the riik property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRiik() {
        return riik;
    }

    /**
     * Sets the value of the riik property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRiik(JAXBElement<String> value) {
        this.riik = value;
    }

    /**
     * Gets the value of the postiindeks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostiindeks() {
        return postiindeks;
    }

    /**
     * Sets the value of the postiindeks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostiindeks(String value) {
        this.postiindeks = value;
    }

    /**
     * Gets the value of the adobId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdobId() {
        return adobId;
    }

    /**
     * Sets the value of the adobId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdobId(String value) {
        this.adobId = value;
    }

    /**
     * Gets the value of the adrId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAdrId() {
        return adrId;
    }

    /**
     * Sets the value of the adrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAdrId(Integer value) {
        this.adrId = value;
    }

    /**
     * Gets the value of the tase6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTase6() {
        return tase6;
    }

    /**
     * Sets the value of the tase6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTase6(String value) {
        this.tase6 = value;
    }

    /**
     * Gets the value of the tase7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTase7() {
        return tase7;
    }

    /**
     * Sets the value of the tase7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTase7(String value) {
        this.tase7 = value;
    }

    /**
     * Gets the value of the tase8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTase8() {
        return tase8;
    }

    /**
     * Sets the value of the tase8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTase8(String value) {
        this.tase8 = value;
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
     * Gets the value of the koodaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoodaadress() {
        return koodaadress;
    }

    /**
     * Sets the value of the koodaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoodaadress(String value) {
        this.koodaadress = value;
    }

    /**
     * Gets the value of the katastriyksus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKatastriyksus() {
        return katastriyksus;
    }

    /**
     * Sets the value of the katastriyksus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKatastriyksus(String value) {
        this.katastriyksus = value;
    }

    /**
     * Gets the value of the taisaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaisaadress() {
        return taisaadress;
    }

    /**
     * Sets the value of the taisaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaisaadress(String value) {
        this.taisaadress = value;
    }

    /**
     * Gets the value of the taisaadressTapsustus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaisaadressTapsustus() {
        return taisaadressTapsustus;
    }

    /**
     * Sets the value of the taisaadressTapsustus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaisaadressTapsustus(String value) {
        this.taisaadressTapsustus = value;
    }

    /**
     * Gets the value of the tyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyyp() {
        return tyyp;
    }

    /**
     * Sets the value of the tyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyyp(String value) {
        this.tyyp = value;
    }

}
