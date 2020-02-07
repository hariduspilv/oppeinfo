
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR81KMAisikkontrollResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR81KMAisikkontrollResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vanaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vanaperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Istaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Istaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kstaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EOkuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EOalates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EOstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Pereseisukuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Surmaaktnr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Surmakuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikudokid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikudok" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikudok.Dokliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikudok.Doknr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Seotud_isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Seotud_isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Seotud_isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.IstaatusKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Istaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Kstaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seotud_isikud.SuheTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Sama_aadressiga"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Sama_aadress" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Sama_aadressiga.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sama_aadressiga.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sama_aadressiga.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sama_aadressiga.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sama_aadressiga.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Elukohad"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Elukoht.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Vkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR81KMAisikkontrollResponseType", propOrder = {
    "eesnimi",
    "perenimi",
    "isikukood",
    "sugu",
    "synniaeg",
    "vanaeesnimi",
    "vanaperenimi",
    "istaatuskd",
    "istaatus",
    "kstaatuskd",
    "kstaatus",
    "kodakondsus",
    "synnikoht",
    "eOkuni",
    "eOalates",
    "eOstaatus",
    "pereseis",
    "pereseisukuup",
    "surmaaktnr",
    "surmakuupaev",
    "veakood",
    "veatekst",
    "isikudokid",
    "seotudIsikud",
    "samaAadressiga",
    "elukohad"
})
public class RR81KMAisikkontrollResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Eesnimi", required = true)
    protected String eesnimi;
    @XmlElement(name = "Perenimi", required = true)
    protected String perenimi;
    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Sugu", required = true)
    protected String sugu;
    @XmlElement(name = "Synniaeg", required = true)
    protected String synniaeg;
    @XmlElement(name = "Vanaeesnimi", required = true)
    protected String vanaeesnimi;
    @XmlElement(name = "Vanaperenimi", required = true)
    protected String vanaperenimi;
    @XmlElement(name = "Istaatuskd", required = true)
    protected String istaatuskd;
    @XmlElement(name = "Istaatus", required = true)
    protected String istaatus;
    @XmlElement(name = "Kstaatuskd", required = true)
    protected String kstaatuskd;
    @XmlElement(name = "Kstaatus", required = true)
    protected String kstaatus;
    @XmlElement(name = "Kodakondsus", required = true)
    protected String kodakondsus;
    @XmlElement(name = "Synnikoht", required = true)
    protected String synnikoht;
    @XmlElement(name = "EOkuni", required = true)
    protected String eOkuni;
    @XmlElement(name = "EOalates", required = true)
    protected String eOalates;
    @XmlElement(name = "EOstaatus", required = true)
    protected String eOstaatus;
    @XmlElement(name = "Pereseis", required = true)
    protected String pereseis;
    @XmlElement(name = "Pereseisukuup", required = true)
    protected String pereseisukuup;
    @XmlElement(name = "Surmaaktnr", required = true)
    protected String surmaaktnr;
    @XmlElement(name = "Surmakuupaev", required = true)
    protected String surmakuupaev;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;
    @XmlElement(name = "Isikudokid", required = true)
    protected RR81KMAisikkontrollResponseType.Isikudokid isikudokid;
    @XmlElement(name = "Seotud_isikud", required = true)
    protected RR81KMAisikkontrollResponseType.SeotudIsikud seotudIsikud;
    @XmlElement(name = "Sama_aadressiga", required = true)
    protected RR81KMAisikkontrollResponseType.SamaAadressiga samaAadressiga;
    @XmlElement(name = "Elukohad", required = true)
    protected RR81KMAisikkontrollResponseType.Elukohad elukohad;

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
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the sugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSugu() {
        return sugu;
    }

    /**
     * Sets the value of the sugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSugu(String value) {
        this.sugu = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynniaeg(String value) {
        this.synniaeg = value;
    }

    /**
     * Gets the value of the vanaeesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVanaeesnimi() {
        return vanaeesnimi;
    }

    /**
     * Sets the value of the vanaeesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVanaeesnimi(String value) {
        this.vanaeesnimi = value;
    }

    /**
     * Gets the value of the vanaperenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVanaperenimi() {
        return vanaperenimi;
    }

    /**
     * Sets the value of the vanaperenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVanaperenimi(String value) {
        this.vanaperenimi = value;
    }

    /**
     * Gets the value of the istaatuskd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstaatuskd() {
        return istaatuskd;
    }

    /**
     * Sets the value of the istaatuskd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstaatuskd(String value) {
        this.istaatuskd = value;
    }

    /**
     * Gets the value of the istaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstaatus() {
        return istaatus;
    }

    /**
     * Sets the value of the istaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstaatus(String value) {
        this.istaatus = value;
    }

    /**
     * Gets the value of the kstaatuskd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKstaatuskd() {
        return kstaatuskd;
    }

    /**
     * Sets the value of the kstaatuskd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKstaatuskd(String value) {
        this.kstaatuskd = value;
    }

    /**
     * Gets the value of the kstaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKstaatus() {
        return kstaatus;
    }

    /**
     * Sets the value of the kstaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKstaatus(String value) {
        this.kstaatus = value;
    }

    /**
     * Gets the value of the kodakondsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsus() {
        return kodakondsus;
    }

    /**
     * Sets the value of the kodakondsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsus(String value) {
        this.kodakondsus = value;
    }

    /**
     * Gets the value of the synnikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynnikoht() {
        return synnikoht;
    }

    /**
     * Sets the value of the synnikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynnikoht(String value) {
        this.synnikoht = value;
    }

    /**
     * Gets the value of the eOkuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEOkuni() {
        return eOkuni;
    }

    /**
     * Sets the value of the eOkuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEOkuni(String value) {
        this.eOkuni = value;
    }

    /**
     * Gets the value of the eOalates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEOalates() {
        return eOalates;
    }

    /**
     * Sets the value of the eOalates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEOalates(String value) {
        this.eOalates = value;
    }

    /**
     * Gets the value of the eOstaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEOstaatus() {
        return eOstaatus;
    }

    /**
     * Sets the value of the eOstaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEOstaatus(String value) {
        this.eOstaatus = value;
    }

    /**
     * Gets the value of the pereseis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPereseis() {
        return pereseis;
    }

    /**
     * Sets the value of the pereseis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPereseis(String value) {
        this.pereseis = value;
    }

    /**
     * Gets the value of the pereseisukuup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPereseisukuup() {
        return pereseisukuup;
    }

    /**
     * Sets the value of the pereseisukuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPereseisukuup(String value) {
        this.pereseisukuup = value;
    }

    /**
     * Gets the value of the surmaaktnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurmaaktnr() {
        return surmaaktnr;
    }

    /**
     * Sets the value of the surmaaktnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurmaaktnr(String value) {
        this.surmaaktnr = value;
    }

    /**
     * Gets the value of the surmakuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurmakuupaev() {
        return surmakuupaev;
    }

    /**
     * Sets the value of the surmakuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurmakuupaev(String value) {
        this.surmakuupaev = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the isikudokid property.
     * 
     * @return
     *     possible object is
     *     {@link RR81KMAisikkontrollResponseType.Isikudokid }
     *     
     */
    public RR81KMAisikkontrollResponseType.Isikudokid getIsikudokid() {
        return isikudokid;
    }

    /**
     * Sets the value of the isikudokid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR81KMAisikkontrollResponseType.Isikudokid }
     *     
     */
    public void setIsikudokid(RR81KMAisikkontrollResponseType.Isikudokid value) {
        this.isikudokid = value;
    }

    /**
     * Gets the value of the seotudIsikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR81KMAisikkontrollResponseType.SeotudIsikud }
     *     
     */
    public RR81KMAisikkontrollResponseType.SeotudIsikud getSeotudIsikud() {
        return seotudIsikud;
    }

    /**
     * Sets the value of the seotudIsikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR81KMAisikkontrollResponseType.SeotudIsikud }
     *     
     */
    public void setSeotudIsikud(RR81KMAisikkontrollResponseType.SeotudIsikud value) {
        this.seotudIsikud = value;
    }

    /**
     * Gets the value of the samaAadressiga property.
     * 
     * @return
     *     possible object is
     *     {@link RR81KMAisikkontrollResponseType.SamaAadressiga }
     *     
     */
    public RR81KMAisikkontrollResponseType.SamaAadressiga getSamaAadressiga() {
        return samaAadressiga;
    }

    /**
     * Sets the value of the samaAadressiga property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR81KMAisikkontrollResponseType.SamaAadressiga }
     *     
     */
    public void setSamaAadressiga(RR81KMAisikkontrollResponseType.SamaAadressiga value) {
        this.samaAadressiga = value;
    }

    /**
     * Gets the value of the elukohad property.
     * 
     * @return
     *     possible object is
     *     {@link RR81KMAisikkontrollResponseType.Elukohad }
     *     
     */
    public RR81KMAisikkontrollResponseType.Elukohad getElukohad() {
        return elukohad;
    }

    /**
     * Sets the value of the elukohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR81KMAisikkontrollResponseType.Elukohad }
     *     
     */
    public void setElukohad(RR81KMAisikkontrollResponseType.Elukohad value) {
        this.elukohad = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Elukoht.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Vkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "elukoht"
    })
    public static class Elukohad {

        @XmlElement(name = "Elukoht")
        protected List<RR81KMAisikkontrollResponseType.Elukohad.Elukoht> elukoht;

        /**
         * Gets the value of the elukoht property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the elukoht property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getElukoht().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR81KMAisikkontrollResponseType.Elukohad.Elukoht }
         * 
         * 
         */
        public List<RR81KMAisikkontrollResponseType.Elukohad.Elukoht> getElukoht() {
            if (elukoht == null) {
                elukoht = new ArrayList<RR81KMAisikkontrollResponseType.Elukohad.Elukoht>();
            }
            return this.elukoht;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Elukoht.Riikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Linn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Vkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Sihtnumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "elukohtRiikkd",
            "elukohtMaakond",
            "elukohtLinn",
            "elukohtAsula",
            "elukohtVkoht",
            "elukohtTanav",
            "elukohtNimi",
            "elukohtMaja",
            "elukohtKorter",
            "elukohtSihtnumber",
            "elukohtAlates",
            "elukohtKuni"
        })
        public static class Elukoht {

            @XmlElement(name = "Elukoht.Riikkd", required = true)
            protected String elukohtRiikkd;
            @XmlElement(name = "Elukoht.Maakond", required = true)
            protected String elukohtMaakond;
            @XmlElement(name = "Elukoht.Linn", required = true)
            protected String elukohtLinn;
            @XmlElement(name = "Elukoht.Asula", required = true)
            protected String elukohtAsula;
            @XmlElement(name = "Elukoht.Vkoht", required = true)
            protected String elukohtVkoht;
            @XmlElement(name = "Elukoht.Tanav", required = true)
            protected String elukohtTanav;
            @XmlElement(name = "Elukoht.Nimi", required = true)
            protected String elukohtNimi;
            @XmlElement(name = "Elukoht.Maja", required = true)
            protected String elukohtMaja;
            @XmlElement(name = "Elukoht.Korter", required = true)
            protected String elukohtKorter;
            @XmlElement(name = "Elukoht.Sihtnumber", required = true)
            protected String elukohtSihtnumber;
            @XmlElement(name = "Elukoht.Alates", required = true)
            protected String elukohtAlates;
            @XmlElement(name = "Elukoht.Kuni", required = true)
            protected String elukohtKuni;

            /**
             * Gets the value of the elukohtRiikkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtRiikkd() {
                return elukohtRiikkd;
            }

            /**
             * Sets the value of the elukohtRiikkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtRiikkd(String value) {
                this.elukohtRiikkd = value;
            }

            /**
             * Gets the value of the elukohtMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaakond() {
                return elukohtMaakond;
            }

            /**
             * Sets the value of the elukohtMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaakond(String value) {
                this.elukohtMaakond = value;
            }

            /**
             * Gets the value of the elukohtLinn property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtLinn() {
                return elukohtLinn;
            }

            /**
             * Sets the value of the elukohtLinn property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtLinn(String value) {
                this.elukohtLinn = value;
            }

            /**
             * Gets the value of the elukohtAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAsula() {
                return elukohtAsula;
            }

            /**
             * Sets the value of the elukohtAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAsula(String value) {
                this.elukohtAsula = value;
            }

            /**
             * Gets the value of the elukohtVkoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtVkoht() {
                return elukohtVkoht;
            }

            /**
             * Sets the value of the elukohtVkoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtVkoht(String value) {
                this.elukohtVkoht = value;
            }

            /**
             * Gets the value of the elukohtTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtTanav() {
                return elukohtTanav;
            }

            /**
             * Sets the value of the elukohtTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtTanav(String value) {
                this.elukohtTanav = value;
            }

            /**
             * Gets the value of the elukohtNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtNimi() {
                return elukohtNimi;
            }

            /**
             * Sets the value of the elukohtNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtNimi(String value) {
                this.elukohtNimi = value;
            }

            /**
             * Gets the value of the elukohtMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaja() {
                return elukohtMaja;
            }

            /**
             * Sets the value of the elukohtMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaja(String value) {
                this.elukohtMaja = value;
            }

            /**
             * Gets the value of the elukohtKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtKorter() {
                return elukohtKorter;
            }

            /**
             * Sets the value of the elukohtKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtKorter(String value) {
                this.elukohtKorter = value;
            }

            /**
             * Gets the value of the elukohtSihtnumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtSihtnumber() {
                return elukohtSihtnumber;
            }

            /**
             * Sets the value of the elukohtSihtnumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtSihtnumber(String value) {
                this.elukohtSihtnumber = value;
            }

            /**
             * Gets the value of the elukohtAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAlates() {
                return elukohtAlates;
            }

            /**
             * Sets the value of the elukohtAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAlates(String value) {
                this.elukohtAlates = value;
            }

            /**
             * Gets the value of the elukohtKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtKuni() {
                return elukohtKuni;
            }

            /**
             * Sets the value of the elukohtKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtKuni(String value) {
                this.elukohtKuni = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Isikudok" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikudok.Dokliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikudok.Doknr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "isikudok"
    })
    public static class Isikudokid {

        @XmlElement(name = "Isikudok")
        protected List<RR81KMAisikkontrollResponseType.Isikudokid.Isikudok> isikudok;

        /**
         * Gets the value of the isikudok property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isikudok property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsikudok().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR81KMAisikkontrollResponseType.Isikudokid.Isikudok }
         * 
         * 
         */
        public List<RR81KMAisikkontrollResponseType.Isikudokid.Isikudok> getIsikudok() {
            if (isikudok == null) {
                isikudok = new ArrayList<RR81KMAisikkontrollResponseType.Isikudokid.Isikudok>();
            }
            return this.isikudok;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Isikudok.Dokliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikudok.Doknr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "isikudokDokliik",
            "isikudokDoknr"
        })
        public static class Isikudok {

            @XmlElement(name = "Isikudok.Dokliik", required = true)
            protected String isikudokDokliik;
            @XmlElement(name = "Isikudok.Doknr", required = true)
            protected String isikudokDoknr;

            /**
             * Gets the value of the isikudokDokliik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudokDokliik() {
                return isikudokDokliik;
            }

            /**
             * Sets the value of the isikudokDokliik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudokDokliik(String value) {
                this.isikudokDokliik = value;
            }

            /**
             * Gets the value of the isikudokDoknr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikudokDoknr() {
                return isikudokDoknr;
            }

            /**
             * Sets the value of the isikudokDoknr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikudokDoknr(String value) {
                this.isikudokDoknr = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Sama_aadress" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Sama_aadressiga.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sama_aadressiga.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sama_aadressiga.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sama_aadressiga.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sama_aadressiga.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "samaAadress"
    })
    public static class SamaAadressiga {

        @XmlElement(name = "Sama_aadress")
        protected List<RR81KMAisikkontrollResponseType.SamaAadressiga.SamaAadress> samaAadress;

        /**
         * Gets the value of the samaAadress property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the samaAadress property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSamaAadress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR81KMAisikkontrollResponseType.SamaAadressiga.SamaAadress }
         * 
         * 
         */
        public List<RR81KMAisikkontrollResponseType.SamaAadressiga.SamaAadress> getSamaAadress() {
            if (samaAadress == null) {
                samaAadress = new ArrayList<RR81KMAisikkontrollResponseType.SamaAadressiga.SamaAadress>();
            }
            return this.samaAadress;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Sama_aadressiga.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sama_aadressiga.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sama_aadressiga.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sama_aadressiga.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sama_aadressiga.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "samaAadressigaEesnimi",
            "samaAadressigaPerenimi",
            "samaAadressigaIsikukood",
            "samaAadressigaSugu",
            "samaAadressigaSynniaeg"
        })
        public static class SamaAadress {

            @XmlElement(name = "Sama_aadressiga.Eesnimi", required = true)
            protected String samaAadressigaEesnimi;
            @XmlElement(name = "Sama_aadressiga.Perenimi", required = true)
            protected String samaAadressigaPerenimi;
            @XmlElement(name = "Sama_aadressiga.Isikukood", required = true)
            protected String samaAadressigaIsikukood;
            @XmlElement(name = "Sama_aadressiga.Sugu", required = true)
            protected String samaAadressigaSugu;
            @XmlElement(name = "Sama_aadressiga.Synniaeg", required = true)
            protected String samaAadressigaSynniaeg;

            /**
             * Gets the value of the samaAadressigaEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSamaAadressigaEesnimi() {
                return samaAadressigaEesnimi;
            }

            /**
             * Sets the value of the samaAadressigaEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSamaAadressigaEesnimi(String value) {
                this.samaAadressigaEesnimi = value;
            }

            /**
             * Gets the value of the samaAadressigaPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSamaAadressigaPerenimi() {
                return samaAadressigaPerenimi;
            }

            /**
             * Sets the value of the samaAadressigaPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSamaAadressigaPerenimi(String value) {
                this.samaAadressigaPerenimi = value;
            }

            /**
             * Gets the value of the samaAadressigaIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSamaAadressigaIsikukood() {
                return samaAadressigaIsikukood;
            }

            /**
             * Sets the value of the samaAadressigaIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSamaAadressigaIsikukood(String value) {
                this.samaAadressigaIsikukood = value;
            }

            /**
             * Gets the value of the samaAadressigaSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSamaAadressigaSugu() {
                return samaAadressigaSugu;
            }

            /**
             * Sets the value of the samaAadressigaSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSamaAadressigaSugu(String value) {
                this.samaAadressigaSugu = value;
            }

            /**
             * Gets the value of the samaAadressigaSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSamaAadressigaSynniaeg() {
                return samaAadressigaSynniaeg;
            }

            /**
             * Sets the value of the samaAadressigaSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSamaAadressigaSynniaeg(String value) {
                this.samaAadressigaSynniaeg = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Seotud_isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Seotud_isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.IstaatusKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Istaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Kstaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seotud_isikud.SuheTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "seotudIsik"
    })
    public static class SeotudIsikud {

        @XmlElement(name = "Seotud_isik")
        protected List<RR81KMAisikkontrollResponseType.SeotudIsikud.SeotudIsik> seotudIsik;

        /**
         * Gets the value of the seotudIsik property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the seotudIsik property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSeotudIsik().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR81KMAisikkontrollResponseType.SeotudIsikud.SeotudIsik }
         * 
         * 
         */
        public List<RR81KMAisikkontrollResponseType.SeotudIsikud.SeotudIsik> getSeotudIsik() {
            if (seotudIsik == null) {
                seotudIsik = new ArrayList<RR81KMAisikkontrollResponseType.SeotudIsikud.SeotudIsik>();
            }
            return this.seotudIsik;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Seotud_isikud.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.IstaatusKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Istaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Kstaatuskd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Kstaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.Kuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seotud_isikud.SuheTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "seotudIsikudEesnimi",
            "seotudIsikudPerenimi",
            "seotudIsikudIsikukood",
            "seotudIsikudSugu",
            "seotudIsikudSynniaeg",
            "seotudIsikudIstaatusKd",
            "seotudIsikudIstaatus",
            "seotudIsikudKstaatuskd",
            "seotudIsikudKstaatus",
            "seotudIsikudAlates",
            "seotudIsikudKuni",
            "seotudIsikudSuheTyyp"
        })
        public static class SeotudIsik {

            @XmlElement(name = "Seotud_isikud.Eesnimi", required = true)
            protected String seotudIsikudEesnimi;
            @XmlElement(name = "Seotud_isikud.Perenimi", required = true)
            protected String seotudIsikudPerenimi;
            @XmlElement(name = "Seotud_isikud.Isikukood", required = true)
            protected String seotudIsikudIsikukood;
            @XmlElement(name = "Seotud_isikud.Sugu", required = true)
            protected String seotudIsikudSugu;
            @XmlElement(name = "Seotud_isikud.Synniaeg", required = true)
            protected String seotudIsikudSynniaeg;
            @XmlElement(name = "Seotud_isikud.IstaatusKd", required = true)
            protected String seotudIsikudIstaatusKd;
            @XmlElement(name = "Seotud_isikud.Istaatus", required = true)
            protected String seotudIsikudIstaatus;
            @XmlElement(name = "Seotud_isikud.Kstaatuskd", required = true)
            protected String seotudIsikudKstaatuskd;
            @XmlElement(name = "Seotud_isikud.Kstaatus", required = true)
            protected String seotudIsikudKstaatus;
            @XmlElement(name = "Seotud_isikud.Alates", required = true)
            protected String seotudIsikudAlates;
            @XmlElement(name = "Seotud_isikud.Kuni", required = true)
            protected String seotudIsikudKuni;
            @XmlElement(name = "Seotud_isikud.SuheTyyp", required = true)
            protected String seotudIsikudSuheTyyp;

            /**
             * Gets the value of the seotudIsikudEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudEesnimi() {
                return seotudIsikudEesnimi;
            }

            /**
             * Sets the value of the seotudIsikudEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudEesnimi(String value) {
                this.seotudIsikudEesnimi = value;
            }

            /**
             * Gets the value of the seotudIsikudPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudPerenimi() {
                return seotudIsikudPerenimi;
            }

            /**
             * Sets the value of the seotudIsikudPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudPerenimi(String value) {
                this.seotudIsikudPerenimi = value;
            }

            /**
             * Gets the value of the seotudIsikudIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudIsikukood() {
                return seotudIsikudIsikukood;
            }

            /**
             * Sets the value of the seotudIsikudIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudIsikukood(String value) {
                this.seotudIsikudIsikukood = value;
            }

            /**
             * Gets the value of the seotudIsikudSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudSugu() {
                return seotudIsikudSugu;
            }

            /**
             * Sets the value of the seotudIsikudSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudSugu(String value) {
                this.seotudIsikudSugu = value;
            }

            /**
             * Gets the value of the seotudIsikudSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudSynniaeg() {
                return seotudIsikudSynniaeg;
            }

            /**
             * Sets the value of the seotudIsikudSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudSynniaeg(String value) {
                this.seotudIsikudSynniaeg = value;
            }

            /**
             * Gets the value of the seotudIsikudIstaatusKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudIstaatusKd() {
                return seotudIsikudIstaatusKd;
            }

            /**
             * Sets the value of the seotudIsikudIstaatusKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudIstaatusKd(String value) {
                this.seotudIsikudIstaatusKd = value;
            }

            /**
             * Gets the value of the seotudIsikudIstaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudIstaatus() {
                return seotudIsikudIstaatus;
            }

            /**
             * Sets the value of the seotudIsikudIstaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudIstaatus(String value) {
                this.seotudIsikudIstaatus = value;
            }

            /**
             * Gets the value of the seotudIsikudKstaatuskd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudKstaatuskd() {
                return seotudIsikudKstaatuskd;
            }

            /**
             * Sets the value of the seotudIsikudKstaatuskd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudKstaatuskd(String value) {
                this.seotudIsikudKstaatuskd = value;
            }

            /**
             * Gets the value of the seotudIsikudKstaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudKstaatus() {
                return seotudIsikudKstaatus;
            }

            /**
             * Sets the value of the seotudIsikudKstaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudKstaatus(String value) {
                this.seotudIsikudKstaatus = value;
            }

            /**
             * Gets the value of the seotudIsikudAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudAlates() {
                return seotudIsikudAlates;
            }

            /**
             * Sets the value of the seotudIsikudAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudAlates(String value) {
                this.seotudIsikudAlates = value;
            }

            /**
             * Gets the value of the seotudIsikudKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudKuni() {
                return seotudIsikudKuni;
            }

            /**
             * Sets the value of the seotudIsikudKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudKuni(String value) {
                this.seotudIsikudKuni = value;
            }

            /**
             * Gets the value of the seotudIsikudSuheTyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeotudIsikudSuheTyyp() {
                return seotudIsikudSuheTyyp;
            }

            /**
             * Sets the value of the seotudIsikudSuheTyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeotudIsikudSuheTyyp(String value) {
                this.seotudIsikudSuheTyyp = value;
            }

        }

    }

}
