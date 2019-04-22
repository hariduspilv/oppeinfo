
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR92IsikuDokidP08ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR92IsikuDokidP08ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AVVeaTekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsikuEnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuPnim" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Elukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="HaridusTase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MuudPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MuusEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kontaktandmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Dokumendid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Dokumendiandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.VajandAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Paringud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Paring" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Parija" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR92IsikuDokidP08ResponseType", propOrder = {
    "avVeaTekst",
    "isikuEnimi",
    "isikuPnim",
    "isikukood",
    "synniaeg",
    "synnikoht",
    "sugu",
    "elukoht",
    "kodakondsus",
    "rahvus",
    "haridusTase",
    "emakeel",
    "perekonnaseis",
    "tegevusala",
    "muudPerenimed",
    "muusEesnimed",
    "isanimi",
    "kontaktandmed",
    "sideaadress",
    "veakood",
    "veatekst",
    "dokumendid",
    "paringud"
})
public class RR92IsikuDokidP08ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "AVVeaTekst")
    protected String avVeaTekst;
    @XmlElement(name = "IsikuEnimi", required = true)
    protected String isikuEnimi;
    @XmlElement(name = "IsikuPnim", required = true)
    protected String isikuPnim;
    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Synniaeg", required = true)
    protected String synniaeg;
    @XmlElement(name = "Synnikoht", required = true)
    protected String synnikoht;
    @XmlElement(name = "Sugu", required = true)
    protected String sugu;
    @XmlElement(name = "Elukoht", required = true)
    protected String elukoht;
    @XmlElement(name = "Kodakondsus", required = true)
    protected String kodakondsus;
    @XmlElement(name = "Rahvus", required = true)
    protected String rahvus;
    @XmlElement(name = "HaridusTase", required = true)
    protected String haridusTase;
    @XmlElement(name = "Emakeel", required = true)
    protected String emakeel;
    @XmlElement(name = "Perekonnaseis", required = true)
    protected String perekonnaseis;
    @XmlElement(name = "Tegevusala", required = true)
    protected String tegevusala;
    @XmlElement(name = "MuudPerenimed", required = true)
    protected String muudPerenimed;
    @XmlElement(name = "MuusEesnimed", required = true)
    protected String muusEesnimed;
    @XmlElement(name = "Isanimi", required = true)
    protected String isanimi;
    @XmlElement(name = "Kontaktandmed", required = true)
    protected String kontaktandmed;
    @XmlElement(name = "Sideaadress", required = true)
    protected String sideaadress;
    @XmlElement(name = "Veakood")
    protected int veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;
    @XmlElement(name = "Dokumendid", required = true)
    protected RR92IsikuDokidP08ResponseType.Dokumendid dokumendid;
    @XmlElement(name = "Paringud", required = true)
    protected RR92IsikuDokidP08ResponseType.Paringud paringud;

    /**
     * Gets the value of the avVeaTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAVVeaTekst() {
        return avVeaTekst;
    }

    /**
     * Sets the value of the avVeaTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAVVeaTekst(String value) {
        this.avVeaTekst = value;
    }

    /**
     * Gets the value of the isikuEnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuEnimi() {
        return isikuEnimi;
    }

    /**
     * Sets the value of the isikuEnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuEnimi(String value) {
        this.isikuEnimi = value;
    }

    /**
     * Gets the value of the isikuPnim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuPnim() {
        return isikuPnim;
    }

    /**
     * Sets the value of the isikuPnim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuPnim(String value) {
        this.isikuPnim = value;
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
     * Gets the value of the elukoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElukoht() {
        return elukoht;
    }

    /**
     * Sets the value of the elukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElukoht(String value) {
        this.elukoht = value;
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
     * Gets the value of the rahvus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRahvus() {
        return rahvus;
    }

    /**
     * Sets the value of the rahvus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRahvus(String value) {
        this.rahvus = value;
    }

    /**
     * Gets the value of the haridusTase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaridusTase() {
        return haridusTase;
    }

    /**
     * Sets the value of the haridusTase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaridusTase(String value) {
        this.haridusTase = value;
    }

    /**
     * Gets the value of the emakeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmakeel() {
        return emakeel;
    }

    /**
     * Sets the value of the emakeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmakeel(String value) {
        this.emakeel = value;
    }

    /**
     * Gets the value of the perekonnaseis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerekonnaseis() {
        return perekonnaseis;
    }

    /**
     * Sets the value of the perekonnaseis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerekonnaseis(String value) {
        this.perekonnaseis = value;
    }

    /**
     * Gets the value of the tegevusala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevusala() {
        return tegevusala;
    }

    /**
     * Sets the value of the tegevusala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevusala(String value) {
        this.tegevusala = value;
    }

    /**
     * Gets the value of the muudPerenimed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuudPerenimed() {
        return muudPerenimed;
    }

    /**
     * Sets the value of the muudPerenimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuudPerenimed(String value) {
        this.muudPerenimed = value;
    }

    /**
     * Gets the value of the muusEesnimed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuusEesnimed() {
        return muusEesnimed;
    }

    /**
     * Sets the value of the muusEesnimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuusEesnimed(String value) {
        this.muusEesnimed = value;
    }

    /**
     * Gets the value of the isanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsanimi() {
        return isanimi;
    }

    /**
     * Sets the value of the isanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsanimi(String value) {
        this.isanimi = value;
    }

    /**
     * Gets the value of the kontaktandmed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKontaktandmed() {
        return kontaktandmed;
    }

    /**
     * Sets the value of the kontaktandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKontaktandmed(String value) {
        this.kontaktandmed = value;
    }

    /**
     * Gets the value of the sideaadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSideaadress() {
        return sideaadress;
    }

    /**
     * Sets the value of the sideaadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSideaadress(String value) {
        this.sideaadress = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     */
    public int getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     */
    public void setVeakood(int value) {
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
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR92IsikuDokidP08ResponseType.Dokumendid }
     *     
     */
    public RR92IsikuDokidP08ResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR92IsikuDokidP08ResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR92IsikuDokidP08ResponseType.Dokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the paringud property.
     * 
     * @return
     *     possible object is
     *     {@link RR92IsikuDokidP08ResponseType.Paringud }
     *     
     */
    public RR92IsikuDokidP08ResponseType.Paringud getParingud() {
        return paringud;
    }

    /**
     * Sets the value of the paringud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR92IsikuDokidP08ResponseType.Paringud }
     *     
     */
    public void setParingud(RR92IsikuDokidP08ResponseType.Paringud value) {
        this.paringud = value;
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
     *         &lt;element name="Dokumendiandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.VajandAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "dokumendiandmed"
    })
    public static class Dokumendid {

        @XmlElement(name = "Dokumendiandmed")
        protected List<RR92IsikuDokidP08ResponseType.Dokumendid.Dokumendiandmed> dokumendiandmed;

        /**
         * Gets the value of the dokumendiandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dokumendiandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDokumendiandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR92IsikuDokidP08ResponseType.Dokumendid.Dokumendiandmed }
         * 
         * 
         */
        public List<RR92IsikuDokidP08ResponseType.Dokumendid.Dokumendiandmed> getDokumendiandmed() {
            if (dokumendiandmed == null) {
                dokumendiandmed = new ArrayList<RR92IsikuDokidP08ResponseType.Dokumendid.Dokumendiandmed>();
            }
            return this.dokumendiandmed;
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
         *         &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.VajandAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumendiandmedKood",
            "dokumendiandmedNimetus",
            "dokumendiandmedSeeria",
            "dokumendiandmedNumber",
            "dokumendiandmedDokValjastamisKuup",
            "dokumendiandmedVajandAsutus",
            "dokumendiandmedKehtivAlates",
            "dokumendiandmedKehtivKuni"
        })
        public static class Dokumendiandmed {

            @XmlElement(name = "Dokumendiandmed.Kood", required = true)
            protected String dokumendiandmedKood;
            @XmlElement(name = "Dokumendiandmed.Nimetus", required = true)
            protected String dokumendiandmedNimetus;
            @XmlElement(name = "Dokumendiandmed.Seeria", required = true)
            protected String dokumendiandmedSeeria;
            @XmlElement(name = "Dokumendiandmed.Number", required = true)
            protected String dokumendiandmedNumber;
            @XmlElement(name = "Dokumendiandmed.DokValjastamisKuup", required = true)
            protected String dokumendiandmedDokValjastamisKuup;
            @XmlElement(name = "Dokumendiandmed.VajandAsutus", required = true)
            protected String dokumendiandmedVajandAsutus;
            @XmlElement(name = "Dokumendiandmed.KehtivAlates", required = true)
            protected String dokumendiandmedKehtivAlates;
            @XmlElement(name = "Dokumendiandmed.KehtivKuni", required = true)
            protected String dokumendiandmedKehtivKuni;

            /**
             * Gets the value of the dokumendiandmedKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKood() {
                return dokumendiandmedKood;
            }

            /**
             * Sets the value of the dokumendiandmedKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKood(String value) {
                this.dokumendiandmedKood = value;
            }

            /**
             * Gets the value of the dokumendiandmedNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedNimetus() {
                return dokumendiandmedNimetus;
            }

            /**
             * Sets the value of the dokumendiandmedNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedNimetus(String value) {
                this.dokumendiandmedNimetus = value;
            }

            /**
             * Gets the value of the dokumendiandmedSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedSeeria() {
                return dokumendiandmedSeeria;
            }

            /**
             * Sets the value of the dokumendiandmedSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedSeeria(String value) {
                this.dokumendiandmedSeeria = value;
            }

            /**
             * Gets the value of the dokumendiandmedNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedNumber() {
                return dokumendiandmedNumber;
            }

            /**
             * Sets the value of the dokumendiandmedNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedNumber(String value) {
                this.dokumendiandmedNumber = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokValjastamisKuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokValjastamisKuup() {
                return dokumendiandmedDokValjastamisKuup;
            }

            /**
             * Sets the value of the dokumendiandmedDokValjastamisKuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokValjastamisKuup(String value) {
                this.dokumendiandmedDokValjastamisKuup = value;
            }

            /**
             * Gets the value of the dokumendiandmedVajandAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedVajandAsutus() {
                return dokumendiandmedVajandAsutus;
            }

            /**
             * Sets the value of the dokumendiandmedVajandAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedVajandAsutus(String value) {
                this.dokumendiandmedVajandAsutus = value;
            }

            /**
             * Gets the value of the dokumendiandmedKehtivAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKehtivAlates() {
                return dokumendiandmedKehtivAlates;
            }

            /**
             * Sets the value of the dokumendiandmedKehtivAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKehtivAlates(String value) {
                this.dokumendiandmedKehtivAlates = value;
            }

            /**
             * Gets the value of the dokumendiandmedKehtivKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKehtivKuni() {
                return dokumendiandmedKehtivKuni;
            }

            /**
             * Sets the value of the dokumendiandmedKehtivKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKehtivKuni(String value) {
                this.dokumendiandmedKehtivKuni = value;
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
     *         &lt;element name="Paring" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Parija" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "paring"
    })
    public static class Paringud {

        @XmlElement(name = "Paring")
        protected List<RR92IsikuDokidP08ResponseType.Paringud.Paring> paring;

        /**
         * Gets the value of the paring property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the paring property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParing().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR92IsikuDokidP08ResponseType.Paringud.Paring }
         * 
         * 
         */
        public List<RR92IsikuDokidP08ResponseType.Paringud.Paring> getParing() {
            if (paring == null) {
                paring = new ArrayList<RR92IsikuDokidP08ResponseType.Paringud.Paring>();
            }
            return this.paring;
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
         *         &lt;element name="Kuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Parija" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "kuupaev",
            "parija"
        })
        public static class Paring {

            @XmlElement(name = "Kuupaev", required = true)
            protected String kuupaev;
            @XmlElement(name = "Parija", required = true)
            protected String parija;

            /**
             * Gets the value of the kuupaev property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKuupaev() {
                return kuupaev;
            }

            /**
             * Sets the value of the kuupaev property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKuupaev(String value) {
                this.kuupaev = value;
            }

            /**
             * Gets the value of the parija property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParija() {
                return parija;
            }

            /**
             * Sets the value of the parija property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParija(String value) {
                this.parija = value;
            }

        }

    }

}
