
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for tallhaIsik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tallhaIsik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="elukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="indeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esimesseKlassi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orb" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusRegnr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klassiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klassiTahis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sisseastumiseKuupaev" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pikapaevaryhm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="muutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lopetamiseKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lopetamisePohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="loputunnistuseKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="loputunnistuseNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eesnmi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tallhaIsik", propOrder = {
    "ik",
    "kodakondsus",
    "elukoht",
    "tanav",
    "maja",
    "korter",
    "indeks",
    "esimesseKlassi",
    "orb",
    "oppeasutus",
    "oppeasutusRegnr",
    "klassiNumber",
    "klassiTahis",
    "sisseastumiseKuupaev",
    "pikapaevaryhm",
    "muutus",
    "muutusKp",
    "lopetamiseKuupaev",
    "lopetamisePohjus",
    "loputunnistuseKuupaev",
    "loputunnistuseNr",
    "sugu",
    "eesnmi",
    "perenimi"
})
public class TallhaIsik {

    @XmlElement(required = true)
    protected String ik;
    @XmlElement(required = true, nillable = true)
    protected String kodakondsus;
    @XmlElement(required = true, nillable = true)
    protected String elukoht;
    @XmlElement(required = true, nillable = true)
    protected String tanav;
    @XmlElement(required = true, nillable = true)
    protected String maja;
    @XmlElement(required = true, nillable = true)
    protected String korter;
    @XmlElement(required = true, nillable = true)
    protected String indeks;
    @XmlElement(required = true, nillable = true)
    protected String esimesseKlassi;
    @XmlElement(required = true, nillable = true)
    protected String orb;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutus;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutusRegnr;
    @XmlElement(required = true, nillable = true)
    protected String klassiNumber;
    @XmlElement(required = true, nillable = true)
    protected String klassiTahis;
    @XmlElement(required = true, nillable = true)
    protected String sisseastumiseKuupaev;
    @XmlElement(required = true, nillable = true)
    protected String pikapaevaryhm;
    @XmlElement(required = true, nillable = true)
    protected String muutus;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lopetamiseKuupaev;
    @XmlElement(required = true, nillable = true)
    protected String lopetamisePohjus;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loputunnistuseKuupaev;
    @XmlElement(required = true, nillable = true)
    protected String loputunnistuseNr;
    @XmlElement(required = true, nillable = true)
    protected String sugu;
    @XmlElement(required = true, nillable = true)
    protected String eesnmi;
    @XmlElement(required = true, nillable = true)
    protected String perenimi;

    /**
     * Gets the value of the ik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIk() {
        return ik;
    }

    /**
     * Sets the value of the ik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIk(String value) {
        this.ik = value;
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
     * Gets the value of the maja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaja() {
        return maja;
    }

    /**
     * Sets the value of the maja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaja(String value) {
        this.maja = value;
    }

    /**
     * Gets the value of the korter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorter() {
        return korter;
    }

    /**
     * Sets the value of the korter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorter(String value) {
        this.korter = value;
    }

    /**
     * Gets the value of the indeks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndeks() {
        return indeks;
    }

    /**
     * Sets the value of the indeks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndeks(String value) {
        this.indeks = value;
    }

    /**
     * Gets the value of the esimesseKlassi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsimesseKlassi() {
        return esimesseKlassi;
    }

    /**
     * Sets the value of the esimesseKlassi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsimesseKlassi(String value) {
        this.esimesseKlassi = value;
    }

    /**
     * Gets the value of the orb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrb() {
        return orb;
    }

    /**
     * Sets the value of the orb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrb(String value) {
        this.orb = value;
    }

    /**
     * Gets the value of the oppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutus() {
        return oppeasutus;
    }

    /**
     * Sets the value of the oppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutus(String value) {
        this.oppeasutus = value;
    }

    /**
     * Gets the value of the oppeasutusRegnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusRegnr() {
        return oppeasutusRegnr;
    }

    /**
     * Sets the value of the oppeasutusRegnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusRegnr(String value) {
        this.oppeasutusRegnr = value;
    }

    /**
     * Gets the value of the klassiNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassiNumber() {
        return klassiNumber;
    }

    /**
     * Sets the value of the klassiNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassiNumber(String value) {
        this.klassiNumber = value;
    }

    /**
     * Gets the value of the klassiTahis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassiTahis() {
        return klassiTahis;
    }

    /**
     * Sets the value of the klassiTahis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassiTahis(String value) {
        this.klassiTahis = value;
    }

    /**
     * Gets the value of the sisseastumiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSisseastumiseKuupaev() {
        return sisseastumiseKuupaev;
    }

    /**
     * Sets the value of the sisseastumiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSisseastumiseKuupaev(String value) {
        this.sisseastumiseKuupaev = value;
    }

    /**
     * Gets the value of the pikapaevaryhm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPikapaevaryhm() {
        return pikapaevaryhm;
    }

    /**
     * Sets the value of the pikapaevaryhm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPikapaevaryhm(String value) {
        this.pikapaevaryhm = value;
    }

    /**
     * Gets the value of the muutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuutus() {
        return muutus;
    }

    /**
     * Sets the value of the muutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuutus(String value) {
        this.muutus = value;
    }

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutusKp(XMLGregorianCalendar value) {
        this.muutusKp = value;
    }

    /**
     * Gets the value of the lopetamiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLopetamiseKuupaev() {
        return lopetamiseKuupaev;
    }

    /**
     * Sets the value of the lopetamiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLopetamiseKuupaev(XMLGregorianCalendar value) {
        this.lopetamiseKuupaev = value;
    }

    /**
     * Gets the value of the lopetamisePohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLopetamisePohjus() {
        return lopetamisePohjus;
    }

    /**
     * Sets the value of the lopetamisePohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLopetamisePohjus(String value) {
        this.lopetamisePohjus = value;
    }

    /**
     * Gets the value of the loputunnistuseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoputunnistuseKuupaev() {
        return loputunnistuseKuupaev;
    }

    /**
     * Sets the value of the loputunnistuseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoputunnistuseKuupaev(XMLGregorianCalendar value) {
        this.loputunnistuseKuupaev = value;
    }

    /**
     * Gets the value of the loputunnistuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoputunnistuseNr() {
        return loputunnistuseNr;
    }

    /**
     * Sets the value of the loputunnistuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoputunnistuseNr(String value) {
        this.loputunnistuseNr = value;
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
     * Gets the value of the eesnmi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnmi() {
        return eesnmi;
    }

    /**
     * Sets the value of the eesnmi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnmi(String value) {
        this.eesnmi = value;
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

}
