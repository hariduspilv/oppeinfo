
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_yldandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_yldandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotteregistri_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="esmaregistreerimise_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kustutamise_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="staatus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="piirkond" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="piirkond_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="piirkond_tekstina_pikk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegutseb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegutseb_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mkr_reg_kpv" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_mkrreg_kuupaevad" minOccurs="0"/&gt;
 *         &lt;element name="raamatupidamiskohustused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_raamatupidamiskohustused" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_tegevuse_aeg" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_teg_ajad" minOccurs="0"/&gt;
 *         &lt;element name="evks_registreeritud" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="evks_registreeritud_kande_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oigusliku_vormi_alaliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oigusliku_vormi_alaliik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sundlopetamine" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_sundlopetamine" minOccurs="0"/&gt;
 *         &lt;element name="staatused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_staatused" minOccurs="0"/&gt;
 *         &lt;element name="valismaa_ariyhing" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_valismaa_ariyhing" minOccurs="0"/&gt;
 *         &lt;element name="arinimed" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_arinimed" minOccurs="0"/&gt;
 *         &lt;element name="aadressid" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_aadressid" minOccurs="0"/&gt;
 *         &lt;element name="kinnisasjad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kinnisasjad" minOccurs="0"/&gt;
 *         &lt;element name="oiguslikud_vormid" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_oiguslikud_vormid" minOccurs="0"/&gt;
 *         &lt;element name="oigusliku_vormi_alaliigid" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_oigusliku_vormi_alaliigid" minOccurs="0"/&gt;
 *         &lt;element name="kapitalid" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kapitalid" minOccurs="0"/&gt;
 *         &lt;element name="nimivaartuseta_aktsiad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_nimivaartusetaaktsiad" minOccurs="0"/&gt;
 *         &lt;element name="oigusjargsused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_oigusjargsused" minOccurs="0"/&gt;
 *         &lt;element name="seisundi_muutused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_seisundi_muutused" minOccurs="0"/&gt;
 *         &lt;element name="majandusaastad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_majandusaastad" minOccurs="0"/&gt;
 *         &lt;element name="pohikirjad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_pohikirjad" minOccurs="0"/&gt;
 *         &lt;element name="kompromissi_tahtajad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kompromissi_tahtajad" minOccurs="0"/&gt;
 *         &lt;element name="asutamise_ajad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_asutamise_ajad" minOccurs="0"/&gt;
 *         &lt;element name="tegutsemise_tahtajad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_tegutsemise_tahtajad" minOccurs="0"/&gt;
 *         &lt;element name="esmanimetamise_ajad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_esmanimetamise_ajad" minOccurs="0"/&gt;
 *         &lt;element name="valislepingud" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_valislepingud" minOccurs="0"/&gt;
 *         &lt;element name="markused_kaardil" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_markused_kaardil" minOccurs="0"/&gt;
 *         &lt;element name="tegevusalad_kaardil" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_tegevusalad_kaardil" minOccurs="0"/&gt;
 *         &lt;element name="eesmargid_kaardil" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_eesmargid_kaardil" minOccurs="0"/&gt;
 *         &lt;element name="sidevahendid" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_sidevahendid" minOccurs="0"/&gt;
 *         &lt;element name="teatatud_tegevusalad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_teatatud_tegevusalad" minOccurs="0"/&gt;
 *         &lt;element name="info_majandusaasta_aruannetest" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_aruande_infod" minOccurs="0"/&gt;
 *         &lt;element name="asutatud_sissemakset_tegemata" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="on_raamatupidamiskohustuslane" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_yldandmed", propOrder = {
    "ettevotteregistriNr",
    "esmaregistreerimiseKpv",
    "kustutamiseKpv",
    "staatus",
    "staatusTekstina",
    "piirkond",
    "piirkondTekstina",
    "piirkondTekstinaPikk",
    "tegutseb",
    "tegutsebTekstina",
    "mkrRegKpv",
    "raamatupidamiskohustused",
    "ettevotjaTegevuseAeg",
    "evksRegistreeritud",
    "evksRegistreeritudKandeKpv",
    "oiguslikVorm",
    "oiguslikVormNr",
    "oiguslikVormTekstina",
    "oiguslikuVormiAlaliik",
    "oiguslikuVormiAlaliikTekstina",
    "sundlopetamine",
    "staatused",
    "valismaaAriyhing",
    "arinimed",
    "aadressid",
    "kinnisasjad",
    "oiguslikudVormid",
    "oiguslikuVormiAlaliigid",
    "kapitalid",
    "nimivaartusetaAktsiad",
    "oigusjargsused",
    "seisundiMuutused",
    "majandusaastad",
    "pohikirjad",
    "kompromissiTahtajad",
    "asutamiseAjad",
    "tegutsemiseTahtajad",
    "esmanimetamiseAjad",
    "valislepingud",
    "markusedKaardil",
    "tegevusaladKaardil",
    "eesmargidKaardil",
    "sidevahendid",
    "teatatudTegevusalad",
    "infoMajandusaastaAruannetest",
    "asutatudSissemaksetTegemata",
    "onRaamatupidamiskohustuslane"
})
public class DetailandmedV5Yldandmed {

    @XmlElement(name = "ettevotteregistri_nr")
    protected BigInteger ettevotteregistriNr;
    @XmlElement(name = "esmaregistreerimise_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esmaregistreerimiseKpv;
    @XmlElement(name = "kustutamise_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kustutamiseKpv;
    protected String staatus;
    @XmlElement(name = "staatus_tekstina")
    protected String staatusTekstina;
    protected BigInteger piirkond;
    @XmlElement(name = "piirkond_tekstina")
    protected String piirkondTekstina;
    @XmlElement(name = "piirkond_tekstina_pikk")
    protected String piirkondTekstinaPikk;
    protected String tegutseb;
    @XmlElement(name = "tegutseb_tekstina")
    protected String tegutsebTekstina;
    @XmlElement(name = "mkr_reg_kpv")
    protected DetailandmedV5MkrregKuupaevad mkrRegKpv;
    protected DetailandmedV5Raamatupidamiskohustused raamatupidamiskohustused;
    @XmlElement(name = "ettevotja_tegevuse_aeg")
    protected DetailandmedV5TegAjad ettevotjaTegevuseAeg;
    @XmlElement(name = "evks_registreeritud")
    protected Boolean evksRegistreeritud;
    @XmlElement(name = "evks_registreeritud_kande_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar evksRegistreeritudKandeKpv;
    @XmlElement(name = "oiguslik_vorm")
    protected String oiguslikVorm;
    @XmlElement(name = "oiguslik_vorm_nr")
    protected BigInteger oiguslikVormNr;
    @XmlElement(name = "oiguslik_vorm_tekstina")
    protected String oiguslikVormTekstina;
    @XmlElement(name = "oigusliku_vormi_alaliik")
    protected String oiguslikuVormiAlaliik;
    @XmlElement(name = "oigusliku_vormi_alaliik_tekstina")
    protected String oiguslikuVormiAlaliikTekstina;
    protected DetailandmedV5Sundlopetamine sundlopetamine;
    protected DetailandmedV5Staatused staatused;
    @XmlElement(name = "valismaa_ariyhing")
    protected DetailandmedV5ValismaaAriyhing valismaaAriyhing;
    protected DetailandmedV5Arinimed arinimed;
    protected DetailandmedV5Aadressid aadressid;
    protected DetailandmedV5Kinnisasjad kinnisasjad;
    @XmlElement(name = "oiguslikud_vormid")
    protected DetailandmedV5OiguslikudVormid oiguslikudVormid;
    @XmlElement(name = "oigusliku_vormi_alaliigid")
    protected DetailandmedV5OiguslikuVormiAlaliigid oiguslikuVormiAlaliigid;
    protected DetailandmedV5Kapitalid kapitalid;
    @XmlElement(name = "nimivaartuseta_aktsiad")
    protected DetailandmedV5Nimivaartusetaaktsiad nimivaartusetaAktsiad;
    protected DetailandmedV5Oigusjargsused oigusjargsused;
    @XmlElement(name = "seisundi_muutused")
    protected DetailandmedV5SeisundiMuutused seisundiMuutused;
    protected DetailandmedV5Majandusaastad majandusaastad;
    protected DetailandmedV5Pohikirjad pohikirjad;
    @XmlElement(name = "kompromissi_tahtajad")
    protected DetailandmedV5KompromissiTahtajad kompromissiTahtajad;
    @XmlElement(name = "asutamise_ajad")
    protected DetailandmedV5AsutamiseAjad asutamiseAjad;
    @XmlElement(name = "tegutsemise_tahtajad")
    protected DetailandmedV5TegutsemiseTahtajad tegutsemiseTahtajad;
    @XmlElement(name = "esmanimetamise_ajad")
    protected DetailandmedV5EsmanimetamiseAjad esmanimetamiseAjad;
    protected DetailandmedV5Valislepingud valislepingud;
    @XmlElement(name = "markused_kaardil")
    protected DetailandmedV5MarkusedKaardil markusedKaardil;
    @XmlElement(name = "tegevusalad_kaardil")
    protected DetailandmedV5TegevusaladKaardil tegevusaladKaardil;
    @XmlElement(name = "eesmargid_kaardil")
    protected DetailandmedV5EesmargidKaardil eesmargidKaardil;
    protected DetailandmedV5Sidevahendid sidevahendid;
    @XmlElement(name = "teatatud_tegevusalad")
    protected DetailandmedV5TeatatudTegevusalad teatatudTegevusalad;
    @XmlElement(name = "info_majandusaasta_aruannetest")
    protected DetailandmedV5AruandeInfod infoMajandusaastaAruannetest;
    @XmlElement(name = "asutatud_sissemakset_tegemata", required = true, type = Boolean.class, nillable = true)
    protected Boolean asutatudSissemaksetTegemata;
    @XmlElementRef(name = "on_raamatupidamiskohustuslane", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> onRaamatupidamiskohustuslane;

    /**
     * Gets the value of the ettevotteregistriNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEttevotteregistriNr() {
        return ettevotteregistriNr;
    }

    /**
     * Sets the value of the ettevotteregistriNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEttevotteregistriNr(BigInteger value) {
        this.ettevotteregistriNr = value;
    }

    /**
     * Gets the value of the esmaregistreerimiseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsmaregistreerimiseKpv() {
        return esmaregistreerimiseKpv;
    }

    /**
     * Sets the value of the esmaregistreerimiseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsmaregistreerimiseKpv(XMLGregorianCalendar value) {
        this.esmaregistreerimiseKpv = value;
    }

    /**
     * Gets the value of the kustutamiseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKustutamiseKpv() {
        return kustutamiseKpv;
    }

    /**
     * Sets the value of the kustutamiseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKustutamiseKpv(XMLGregorianCalendar value) {
        this.kustutamiseKpv = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatus(String value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the staatusTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatusTekstina() {
        return staatusTekstina;
    }

    /**
     * Sets the value of the staatusTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatusTekstina(String value) {
        this.staatusTekstina = value;
    }

    /**
     * Gets the value of the piirkond property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPiirkond() {
        return piirkond;
    }

    /**
     * Sets the value of the piirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPiirkond(BigInteger value) {
        this.piirkond = value;
    }

    /**
     * Gets the value of the piirkondTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiirkondTekstina() {
        return piirkondTekstina;
    }

    /**
     * Sets the value of the piirkondTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiirkondTekstina(String value) {
        this.piirkondTekstina = value;
    }

    /**
     * Gets the value of the piirkondTekstinaPikk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiirkondTekstinaPikk() {
        return piirkondTekstinaPikk;
    }

    /**
     * Sets the value of the piirkondTekstinaPikk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiirkondTekstinaPikk(String value) {
        this.piirkondTekstinaPikk = value;
    }

    /**
     * Gets the value of the tegutseb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegutseb() {
        return tegutseb;
    }

    /**
     * Sets the value of the tegutseb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegutseb(String value) {
        this.tegutseb = value;
    }

    /**
     * Gets the value of the tegutsebTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegutsebTekstina() {
        return tegutsebTekstina;
    }

    /**
     * Sets the value of the tegutsebTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegutsebTekstina(String value) {
        this.tegutsebTekstina = value;
    }

    /**
     * Gets the value of the mkrRegKpv property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5MkrregKuupaevad }
     *     
     */
    public DetailandmedV5MkrregKuupaevad getMkrRegKpv() {
        return mkrRegKpv;
    }

    /**
     * Sets the value of the mkrRegKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5MkrregKuupaevad }
     *     
     */
    public void setMkrRegKpv(DetailandmedV5MkrregKuupaevad value) {
        this.mkrRegKpv = value;
    }

    /**
     * Gets the value of the raamatupidamiskohustused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Raamatupidamiskohustused }
     *     
     */
    public DetailandmedV5Raamatupidamiskohustused getRaamatupidamiskohustused() {
        return raamatupidamiskohustused;
    }

    /**
     * Sets the value of the raamatupidamiskohustused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Raamatupidamiskohustused }
     *     
     */
    public void setRaamatupidamiskohustused(DetailandmedV5Raamatupidamiskohustused value) {
        this.raamatupidamiskohustused = value;
    }

    /**
     * Gets the value of the ettevotjaTegevuseAeg property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5TegAjad }
     *     
     */
    public DetailandmedV5TegAjad getEttevotjaTegevuseAeg() {
        return ettevotjaTegevuseAeg;
    }

    /**
     * Sets the value of the ettevotjaTegevuseAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5TegAjad }
     *     
     */
    public void setEttevotjaTegevuseAeg(DetailandmedV5TegAjad value) {
        this.ettevotjaTegevuseAeg = value;
    }

    /**
     * Gets the value of the evksRegistreeritud property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEvksRegistreeritud() {
        return evksRegistreeritud;
    }

    /**
     * Sets the value of the evksRegistreeritud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEvksRegistreeritud(Boolean value) {
        this.evksRegistreeritud = value;
    }

    /**
     * Gets the value of the evksRegistreeritudKandeKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEvksRegistreeritudKandeKpv() {
        return evksRegistreeritudKandeKpv;
    }

    /**
     * Sets the value of the evksRegistreeritudKandeKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEvksRegistreeritudKandeKpv(XMLGregorianCalendar value) {
        this.evksRegistreeritudKandeKpv = value;
    }

    /**
     * Gets the value of the oiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikVorm() {
        return oiguslikVorm;
    }

    /**
     * Sets the value of the oiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikVorm(String value) {
        this.oiguslikVorm = value;
    }

    /**
     * Gets the value of the oiguslikVormNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOiguslikVormNr() {
        return oiguslikVormNr;
    }

    /**
     * Sets the value of the oiguslikVormNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOiguslikVormNr(BigInteger value) {
        this.oiguslikVormNr = value;
    }

    /**
     * Gets the value of the oiguslikVormTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikVormTekstina() {
        return oiguslikVormTekstina;
    }

    /**
     * Sets the value of the oiguslikVormTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikVormTekstina(String value) {
        this.oiguslikVormTekstina = value;
    }

    /**
     * Gets the value of the oiguslikuVormiAlaliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikuVormiAlaliik() {
        return oiguslikuVormiAlaliik;
    }

    /**
     * Sets the value of the oiguslikuVormiAlaliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikuVormiAlaliik(String value) {
        this.oiguslikuVormiAlaliik = value;
    }

    /**
     * Gets the value of the oiguslikuVormiAlaliikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikuVormiAlaliikTekstina() {
        return oiguslikuVormiAlaliikTekstina;
    }

    /**
     * Sets the value of the oiguslikuVormiAlaliikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikuVormiAlaliikTekstina(String value) {
        this.oiguslikuVormiAlaliikTekstina = value;
    }

    /**
     * Gets the value of the sundlopetamine property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Sundlopetamine }
     *     
     */
    public DetailandmedV5Sundlopetamine getSundlopetamine() {
        return sundlopetamine;
    }

    /**
     * Sets the value of the sundlopetamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Sundlopetamine }
     *     
     */
    public void setSundlopetamine(DetailandmedV5Sundlopetamine value) {
        this.sundlopetamine = value;
    }

    /**
     * Gets the value of the staatused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Staatused }
     *     
     */
    public DetailandmedV5Staatused getStaatused() {
        return staatused;
    }

    /**
     * Sets the value of the staatused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Staatused }
     *     
     */
    public void setStaatused(DetailandmedV5Staatused value) {
        this.staatused = value;
    }

    /**
     * Gets the value of the valismaaAriyhing property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5ValismaaAriyhing }
     *     
     */
    public DetailandmedV5ValismaaAriyhing getValismaaAriyhing() {
        return valismaaAriyhing;
    }

    /**
     * Sets the value of the valismaaAriyhing property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5ValismaaAriyhing }
     *     
     */
    public void setValismaaAriyhing(DetailandmedV5ValismaaAriyhing value) {
        this.valismaaAriyhing = value;
    }

    /**
     * Gets the value of the arinimed property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Arinimed }
     *     
     */
    public DetailandmedV5Arinimed getArinimed() {
        return arinimed;
    }

    /**
     * Sets the value of the arinimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Arinimed }
     *     
     */
    public void setArinimed(DetailandmedV5Arinimed value) {
        this.arinimed = value;
    }

    /**
     * Gets the value of the aadressid property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Aadressid }
     *     
     */
    public DetailandmedV5Aadressid getAadressid() {
        return aadressid;
    }

    /**
     * Sets the value of the aadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Aadressid }
     *     
     */
    public void setAadressid(DetailandmedV5Aadressid value) {
        this.aadressid = value;
    }

    /**
     * Gets the value of the kinnisasjad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Kinnisasjad }
     *     
     */
    public DetailandmedV5Kinnisasjad getKinnisasjad() {
        return kinnisasjad;
    }

    /**
     * Sets the value of the kinnisasjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Kinnisasjad }
     *     
     */
    public void setKinnisasjad(DetailandmedV5Kinnisasjad value) {
        this.kinnisasjad = value;
    }

    /**
     * Gets the value of the oiguslikudVormid property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5OiguslikudVormid }
     *     
     */
    public DetailandmedV5OiguslikudVormid getOiguslikudVormid() {
        return oiguslikudVormid;
    }

    /**
     * Sets the value of the oiguslikudVormid property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5OiguslikudVormid }
     *     
     */
    public void setOiguslikudVormid(DetailandmedV5OiguslikudVormid value) {
        this.oiguslikudVormid = value;
    }

    /**
     * Gets the value of the oiguslikuVormiAlaliigid property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5OiguslikuVormiAlaliigid }
     *     
     */
    public DetailandmedV5OiguslikuVormiAlaliigid getOiguslikuVormiAlaliigid() {
        return oiguslikuVormiAlaliigid;
    }

    /**
     * Sets the value of the oiguslikuVormiAlaliigid property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5OiguslikuVormiAlaliigid }
     *     
     */
    public void setOiguslikuVormiAlaliigid(DetailandmedV5OiguslikuVormiAlaliigid value) {
        this.oiguslikuVormiAlaliigid = value;
    }

    /**
     * Gets the value of the kapitalid property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Kapitalid }
     *     
     */
    public DetailandmedV5Kapitalid getKapitalid() {
        return kapitalid;
    }

    /**
     * Sets the value of the kapitalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Kapitalid }
     *     
     */
    public void setKapitalid(DetailandmedV5Kapitalid value) {
        this.kapitalid = value;
    }

    /**
     * Gets the value of the nimivaartusetaAktsiad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Nimivaartusetaaktsiad }
     *     
     */
    public DetailandmedV5Nimivaartusetaaktsiad getNimivaartusetaAktsiad() {
        return nimivaartusetaAktsiad;
    }

    /**
     * Sets the value of the nimivaartusetaAktsiad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Nimivaartusetaaktsiad }
     *     
     */
    public void setNimivaartusetaAktsiad(DetailandmedV5Nimivaartusetaaktsiad value) {
        this.nimivaartusetaAktsiad = value;
    }

    /**
     * Gets the value of the oigusjargsused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Oigusjargsused }
     *     
     */
    public DetailandmedV5Oigusjargsused getOigusjargsused() {
        return oigusjargsused;
    }

    /**
     * Sets the value of the oigusjargsused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Oigusjargsused }
     *     
     */
    public void setOigusjargsused(DetailandmedV5Oigusjargsused value) {
        this.oigusjargsused = value;
    }

    /**
     * Gets the value of the seisundiMuutused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5SeisundiMuutused }
     *     
     */
    public DetailandmedV5SeisundiMuutused getSeisundiMuutused() {
        return seisundiMuutused;
    }

    /**
     * Sets the value of the seisundiMuutused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5SeisundiMuutused }
     *     
     */
    public void setSeisundiMuutused(DetailandmedV5SeisundiMuutused value) {
        this.seisundiMuutused = value;
    }

    /**
     * Gets the value of the majandusaastad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Majandusaastad }
     *     
     */
    public DetailandmedV5Majandusaastad getMajandusaastad() {
        return majandusaastad;
    }

    /**
     * Sets the value of the majandusaastad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Majandusaastad }
     *     
     */
    public void setMajandusaastad(DetailandmedV5Majandusaastad value) {
        this.majandusaastad = value;
    }

    /**
     * Gets the value of the pohikirjad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Pohikirjad }
     *     
     */
    public DetailandmedV5Pohikirjad getPohikirjad() {
        return pohikirjad;
    }

    /**
     * Sets the value of the pohikirjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Pohikirjad }
     *     
     */
    public void setPohikirjad(DetailandmedV5Pohikirjad value) {
        this.pohikirjad = value;
    }

    /**
     * Gets the value of the kompromissiTahtajad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5KompromissiTahtajad }
     *     
     */
    public DetailandmedV5KompromissiTahtajad getKompromissiTahtajad() {
        return kompromissiTahtajad;
    }

    /**
     * Sets the value of the kompromissiTahtajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5KompromissiTahtajad }
     *     
     */
    public void setKompromissiTahtajad(DetailandmedV5KompromissiTahtajad value) {
        this.kompromissiTahtajad = value;
    }

    /**
     * Gets the value of the asutamiseAjad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5AsutamiseAjad }
     *     
     */
    public DetailandmedV5AsutamiseAjad getAsutamiseAjad() {
        return asutamiseAjad;
    }

    /**
     * Sets the value of the asutamiseAjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5AsutamiseAjad }
     *     
     */
    public void setAsutamiseAjad(DetailandmedV5AsutamiseAjad value) {
        this.asutamiseAjad = value;
    }

    /**
     * Gets the value of the tegutsemiseTahtajad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5TegutsemiseTahtajad }
     *     
     */
    public DetailandmedV5TegutsemiseTahtajad getTegutsemiseTahtajad() {
        return tegutsemiseTahtajad;
    }

    /**
     * Sets the value of the tegutsemiseTahtajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5TegutsemiseTahtajad }
     *     
     */
    public void setTegutsemiseTahtajad(DetailandmedV5TegutsemiseTahtajad value) {
        this.tegutsemiseTahtajad = value;
    }

    /**
     * Gets the value of the esmanimetamiseAjad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5EsmanimetamiseAjad }
     *     
     */
    public DetailandmedV5EsmanimetamiseAjad getEsmanimetamiseAjad() {
        return esmanimetamiseAjad;
    }

    /**
     * Sets the value of the esmanimetamiseAjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5EsmanimetamiseAjad }
     *     
     */
    public void setEsmanimetamiseAjad(DetailandmedV5EsmanimetamiseAjad value) {
        this.esmanimetamiseAjad = value;
    }

    /**
     * Gets the value of the valislepingud property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Valislepingud }
     *     
     */
    public DetailandmedV5Valislepingud getValislepingud() {
        return valislepingud;
    }

    /**
     * Sets the value of the valislepingud property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Valislepingud }
     *     
     */
    public void setValislepingud(DetailandmedV5Valislepingud value) {
        this.valislepingud = value;
    }

    /**
     * Gets the value of the markusedKaardil property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5MarkusedKaardil }
     *     
     */
    public DetailandmedV5MarkusedKaardil getMarkusedKaardil() {
        return markusedKaardil;
    }

    /**
     * Sets the value of the markusedKaardil property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5MarkusedKaardil }
     *     
     */
    public void setMarkusedKaardil(DetailandmedV5MarkusedKaardil value) {
        this.markusedKaardil = value;
    }

    /**
     * Gets the value of the tegevusaladKaardil property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5TegevusaladKaardil }
     *     
     */
    public DetailandmedV5TegevusaladKaardil getTegevusaladKaardil() {
        return tegevusaladKaardil;
    }

    /**
     * Sets the value of the tegevusaladKaardil property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5TegevusaladKaardil }
     *     
     */
    public void setTegevusaladKaardil(DetailandmedV5TegevusaladKaardil value) {
        this.tegevusaladKaardil = value;
    }

    /**
     * Gets the value of the eesmargidKaardil property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5EesmargidKaardil }
     *     
     */
    public DetailandmedV5EesmargidKaardil getEesmargidKaardil() {
        return eesmargidKaardil;
    }

    /**
     * Sets the value of the eesmargidKaardil property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5EesmargidKaardil }
     *     
     */
    public void setEesmargidKaardil(DetailandmedV5EesmargidKaardil value) {
        this.eesmargidKaardil = value;
    }

    /**
     * Gets the value of the sidevahendid property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Sidevahendid }
     *     
     */
    public DetailandmedV5Sidevahendid getSidevahendid() {
        return sidevahendid;
    }

    /**
     * Sets the value of the sidevahendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Sidevahendid }
     *     
     */
    public void setSidevahendid(DetailandmedV5Sidevahendid value) {
        this.sidevahendid = value;
    }

    /**
     * Gets the value of the teatatudTegevusalad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5TeatatudTegevusalad }
     *     
     */
    public DetailandmedV5TeatatudTegevusalad getTeatatudTegevusalad() {
        return teatatudTegevusalad;
    }

    /**
     * Sets the value of the teatatudTegevusalad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5TeatatudTegevusalad }
     *     
     */
    public void setTeatatudTegevusalad(DetailandmedV5TeatatudTegevusalad value) {
        this.teatatudTegevusalad = value;
    }

    /**
     * Gets the value of the infoMajandusaastaAruannetest property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5AruandeInfod }
     *     
     */
    public DetailandmedV5AruandeInfod getInfoMajandusaastaAruannetest() {
        return infoMajandusaastaAruannetest;
    }

    /**
     * Sets the value of the infoMajandusaastaAruannetest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5AruandeInfod }
     *     
     */
    public void setInfoMajandusaastaAruannetest(DetailandmedV5AruandeInfod value) {
        this.infoMajandusaastaAruannetest = value;
    }

    /**
     * Gets the value of the asutatudSissemaksetTegemata property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAsutatudSissemaksetTegemata() {
        return asutatudSissemaksetTegemata;
    }

    /**
     * Sets the value of the asutatudSissemaksetTegemata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAsutatudSissemaksetTegemata(Boolean value) {
        this.asutatudSissemaksetTegemata = value;
    }

    /**
     * Gets the value of the onRaamatupidamiskohustuslane property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getOnRaamatupidamiskohustuslane() {
        return onRaamatupidamiskohustuslane;
    }

    /**
     * Sets the value of the onRaamatupidamiskohustuslane property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setOnRaamatupidamiskohustuslane(JAXBElement<Boolean> value) {
        this.onRaamatupidamiskohustuslane = value;
    }

}
