
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiLooKanneIsik_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiLooKanneIsik_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="roll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sissemaksu_summa" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="sissemaksu_valuuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aadress" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneAadress_v1" minOccurs="0"/&gt;
 *         &lt;element name="residentsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kontakt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiLooKanneIsik_v1", propOrder = {
    "liik",
    "roll",
    "sissemaksuSumma",
    "sissemaksuValuuta",
    "eesnimi",
    "nimi",
    "kood",
    "aadress",
    "residentsus",
    "email",
    "kontakt"
})
public class EVapiLooKanneIsikV1 {

    @XmlElement(required = true)
    protected String liik;
    @XmlElement(required = true)
    protected String roll;
    @XmlElement(name = "sissemaksu_summa")
    protected BigInteger sissemaksuSumma;
    @XmlElement(name = "sissemaksu_valuuta")
    protected String sissemaksuValuuta;
    protected String eesnimi;
    @XmlElement(required = true)
    protected String nimi;
    @XmlElement(required = true)
    protected String kood;
    protected EVapiLooKanneAadressV1 aadress;
    protected String residentsus;
    protected String email;
    protected String kontakt;

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiik(String value) {
        this.liik = value;
    }

    /**
     * Gets the value of the roll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoll() {
        return roll;
    }

    /**
     * Sets the value of the roll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoll(String value) {
        this.roll = value;
    }

    /**
     * Gets the value of the sissemaksuSumma property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSissemaksuSumma() {
        return sissemaksuSumma;
    }

    /**
     * Sets the value of the sissemaksuSumma property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSissemaksuSumma(BigInteger value) {
        this.sissemaksuSumma = value;
    }

    /**
     * Gets the value of the sissemaksuValuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSissemaksuValuuta() {
        return sissemaksuValuuta;
    }

    /**
     * Sets the value of the sissemaksuValuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSissemaksuValuuta(String value) {
        this.sissemaksuValuuta = value;
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
     * Gets the value of the kood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKood() {
        return kood;
    }

    /**
     * Sets the value of the kood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKood(String value) {
        this.kood = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiLooKanneAadressV1 }
     *     
     */
    public EVapiLooKanneAadressV1 getAadress() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiLooKanneAadressV1 }
     *     
     */
    public void setAadress(EVapiLooKanneAadressV1 value) {
        this.aadress = value;
    }

    /**
     * Gets the value of the residentsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidentsus() {
        return residentsus;
    }

    /**
     * Sets the value of the residentsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidentsus(String value) {
        this.residentsus = value;
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

    /**
     * Gets the value of the kontakt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKontakt() {
        return kontakt;
    }

    /**
     * Sets the value of the kontakt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKontakt(String value) {
        this.kontakt = value;
    }

}
