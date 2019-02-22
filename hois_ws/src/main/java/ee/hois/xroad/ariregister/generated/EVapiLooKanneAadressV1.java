
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiLooKanneAadress_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiLooKanneAadress_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asukoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ehak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="adob_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="adr_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="ads_oid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiLooKanneAadress_v1", propOrder = {
    "asukoht",
    "ehak",
    "riik",
    "postiindeks",
    "adobId",
    "adrId",
    "adsOid",
    "koodaadress"
})
public class EVapiLooKanneAadressV1 {

    protected String asukoht;
    protected String ehak;
    protected String riik;
    protected String postiindeks;
    @XmlElement(name = "adob_id")
    protected String adobId;
    @XmlElement(name = "adr_id")
    protected BigInteger adrId;
    @XmlElement(name = "ads_oid")
    protected String adsOid;
    protected String koodaadress;

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
     *     {@link String }
     *     
     */
    public String getRiik() {
        return riik;
    }

    /**
     * Sets the value of the riik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiik(String value) {
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
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAdrId() {
        return adrId;
    }

    /**
     * Sets the value of the adrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAdrId(BigInteger value) {
        this.adrId = value;
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

}
