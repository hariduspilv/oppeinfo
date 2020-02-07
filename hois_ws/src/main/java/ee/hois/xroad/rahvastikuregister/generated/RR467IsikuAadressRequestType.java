
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR467isikuAadressRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR467isikuAadressRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ADS_OID" type="{http://rr.x-road.eu/producer}AdsOidCode" minOccurs="0"/&gt;
 *         &lt;element name="maakond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vald" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tanav" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="majaNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="korteriNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pohjus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR467isikuAadressRequestType", propOrder = {
    "adsoid",
    "maakond",
    "vald",
    "asula",
    "vaikekoht",
    "tanav",
    "nimi",
    "majaNumber",
    "korteriNumber",
    "pohjus"
})
public class RR467IsikuAadressRequestType {

    @XmlElement(name = "ADS_OID")
    protected String adsoid;
    protected String maakond;
    protected String vald;
    protected String asula;
    protected String vaikekoht;
    protected String tanav;
    protected String nimi;
    protected String majaNumber;
    protected String korteriNumber;
    protected String pohjus;

    /**
     * Gets the value of the adsoid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADSOID() {
        return adsoid;
    }

    /**
     * Sets the value of the adsoid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADSOID(String value) {
        this.adsoid = value;
    }

    /**
     * Gets the value of the maakond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaakond() {
        return maakond;
    }

    /**
     * Sets the value of the maakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaakond(String value) {
        this.maakond = value;
    }

    /**
     * Gets the value of the vald property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVald() {
        return vald;
    }

    /**
     * Sets the value of the vald property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVald(String value) {
        this.vald = value;
    }

    /**
     * Gets the value of the asula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsula() {
        return asula;
    }

    /**
     * Sets the value of the asula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsula(String value) {
        this.asula = value;
    }

    /**
     * Gets the value of the vaikekoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaikekoht() {
        return vaikekoht;
    }

    /**
     * Sets the value of the vaikekoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaikekoht(String value) {
        this.vaikekoht = value;
    }

    /**
     * Gets the value of the tanav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTanav() {
        return tanav;
    }

    /**
     * Sets the value of the tanav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTanav(String value) {
        this.tanav = value;
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
     * Gets the value of the majaNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajaNumber() {
        return majaNumber;
    }

    /**
     * Sets the value of the majaNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajaNumber(String value) {
        this.majaNumber = value;
    }

    /**
     * Gets the value of the korteriNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorteriNumber() {
        return korteriNumber;
    }

    /**
     * Sets the value of the korteriNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorteriNumber(String value) {
        this.korteriNumber = value;
    }

    /**
     * Gets the value of the pohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohjus() {
        return pohjus;
    }

    /**
     * Sets the value of the pohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohjus(String value) {
        this.pohjus = value;
    }

}
