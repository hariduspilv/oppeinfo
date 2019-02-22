
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for sooritakanne_kandesisu_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sooritakanne_kandesisu_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="uus_arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="taiendav_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oigusliku_vormi_alaliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asukoht" type="{http://arireg.x-road.eu/producer/}aadressType_v4" minOccurs="0"/&gt;
 *         &lt;element name="ev_seisund" type="{http://arireg.x-road.eu/producer/}ev_seisundType_v4" minOccurs="0"/&gt;
 *         &lt;element name="pohitegevusala" type="{http://arireg.x-road.eu/producer/}tegevusalaType_v4" minOccurs="0"/&gt;
 *         &lt;element name="lisategevusala" type="{http://arireg.x-road.eu/producer/}tegevusalaType_v4" minOccurs="0"/&gt;
 *         &lt;element name="eesmargid" type="{http://arireg.x-road.eu/producer/}eesmargidType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta" type="{http://arireg.x-road.eu/producer/}sooritakanne_majaasta_v4" minOccurs="0"/&gt;
 *         &lt;element name="kapital" type="{http://arireg.x-road.eu/producer/}sooritakanne_kapital_v4" minOccurs="0"/&gt;
 *         &lt;element name="kapitali_tingimuslik_suurendamine" type="{http://arireg.x-road.eu/producer/}sooritakanne_kapitali_tingimuslik_suurendamine_v4" minOccurs="0"/&gt;
 *         &lt;element name="nimivaartuseta_aktsiate_arv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="asutatud_sissemakset_tegemata" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="omandi_osad" type="{http://arireg.x-road.eu/producer/}omandiOsadType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="isikud" type="{http://arireg.x-road.eu/producer/}isikType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="isikud_esindajad_stork" type="{http://arireg.x-road.eu/producer/}isikesindajastorkType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="esindusoigused" type="{http://arireg.x-road.eu/producer/}esindusoigusType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="pohikiri" type="{http://arireg.x-road.eu/producer/}sooritakanne_pohikiri_v4" minOccurs="0"/&gt;
 *         &lt;element name="sidevahendid" type="{http://arireg.x-road.eu/producer/}sidevahendType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="markused" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="tegutsemise_algus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="tegutsemise_tahtaeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="asutamise_aeg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="viide_eelkaijale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="on_raamatupidamiskohuslane" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kinnisasjad" type="{http://arireg.x-road.eu/producer/}kinnisasiType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="valismaa_emaettevote" type="{http://arireg.x-road.eu/producer/}valismaa_emaettevoteType_v4" minOccurs="0"/&gt;
 *         &lt;element name="fie_seisundid" type="{http://arireg.x-road.eu/producer/}fie_seisundType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="maks_reg_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="elamu_aadressid" type="{http://arireg.x-road.eu/producer/}aadresskatastrigaType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="korteriomandi_kinnistud" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="asutalus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kustalus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sooritakanne_kandesisu_v4", propOrder = {
    "uusArinimi",
    "taiendavNimi",
    "oiguslikVorm",
    "oiguslikuVormiAlaliik",
    "asukoht",
    "evSeisund",
    "pohitegevusala",
    "lisategevusala",
    "eesmargid",
    "majandusaasta",
    "kapital",
    "kapitaliTingimuslikSuurendamine",
    "nimivaartusetaAktsiateArv",
    "asutatudSissemaksetTegemata",
    "omandiOsad",
    "isikud",
    "isikudEsindajadStork",
    "esindusoigused",
    "pohikiri",
    "sidevahendid",
    "markused",
    "tegutsemiseAlgus",
    "tegutsemiseTahtaeg",
    "asutamiseAeg",
    "viideEelkaijale",
    "onRaamatupidamiskohuslane",
    "kinnisasjad",
    "valismaaEmaettevote",
    "fieSeisundid",
    "maksRegKpv",
    "elamuAadressid",
    "korteriomandiKinnistud",
    "asutalusTekstina",
    "kustalusTekstina"
})
public class SooritakanneKandesisuV4 {

    @XmlElement(name = "uus_arinimi")
    protected String uusArinimi;
    @XmlElement(name = "taiendav_nimi")
    protected String taiendavNimi;
    @XmlElement(name = "oiguslik_vorm")
    protected String oiguslikVorm;
    @XmlElementRef(name = "oigusliku_vormi_alaliik", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oiguslikuVormiAlaliik;
    protected AadressTypeV4 asukoht;
    @XmlElement(name = "ev_seisund")
    protected EvSeisundTypeV4 evSeisund;
    protected TegevusalaTypeV4 pohitegevusala;
    protected TegevusalaTypeV4 lisategevusala;
    @XmlElement(nillable = true)
    protected List<EesmargidTypeV4> eesmargid;
    protected SooritakanneMajaastaV4 majandusaasta;
    protected SooritakanneKapitalV4 kapital;
    @XmlElement(name = "kapitali_tingimuslik_suurendamine")
    protected SooritakanneKapitaliTingimuslikSuurendamineV4 kapitaliTingimuslikSuurendamine;
    @XmlElement(name = "nimivaartuseta_aktsiate_arv")
    protected BigInteger nimivaartusetaAktsiateArv;
    @XmlElementRef(name = "asutatud_sissemakset_tegemata", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> asutatudSissemaksetTegemata;
    @XmlElement(name = "omandi_osad", nillable = true)
    protected List<OmandiOsadTypeV4> omandiOsad;
    protected List<IsikTypeV4> isikud;
    @XmlElement(name = "isikud_esindajad_stork")
    protected List<IsikesindajastorkTypeV4> isikudEsindajadStork;
    @XmlElement(nillable = true)
    protected List<EsindusoigusTypeV4> esindusoigused;
    protected SooritakannePohikiriV4 pohikiri;
    @XmlElement(nillable = true)
    protected List<SidevahendTypeV4> sidevahendid;
    @XmlElement(nillable = true)
    protected List<String> markused;
    @XmlElement(name = "tegutsemise_algus")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tegutsemiseAlgus;
    @XmlElement(name = "tegutsemise_tahtaeg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tegutsemiseTahtaeg;
    @XmlElement(name = "asutamise_aeg")
    protected String asutamiseAeg;
    @XmlElement(name = "viide_eelkaijale")
    protected String viideEelkaijale;
    @XmlElement(name = "on_raamatupidamiskohuslane")
    protected String onRaamatupidamiskohuslane;
    @XmlElement(nillable = true)
    protected List<KinnisasiTypeV4> kinnisasjad;
    @XmlElement(name = "valismaa_emaettevote")
    protected ValismaaEmaettevoteTypeV4 valismaaEmaettevote;
    @XmlElement(name = "fie_seisundid", nillable = true)
    protected List<FieSeisundTypeV4> fieSeisundid;
    @XmlElement(name = "maks_reg_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maksRegKpv;
    @XmlElement(name = "elamu_aadressid", nillable = true)
    protected List<AadresskatastrigaTypeV4> elamuAadressid;
    @XmlElement(name = "korteriomandi_kinnistud", nillable = true)
    protected List<String> korteriomandiKinnistud;
    @XmlElementRef(name = "asutalus_tekstina", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> asutalusTekstina;
    @XmlElementRef(name = "kustalus_tekstina", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> kustalusTekstina;

    /**
     * Gets the value of the uusArinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUusArinimi() {
        return uusArinimi;
    }

    /**
     * Sets the value of the uusArinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUusArinimi(String value) {
        this.uusArinimi = value;
    }

    /**
     * Gets the value of the taiendavNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaiendavNimi() {
        return taiendavNimi;
    }

    /**
     * Sets the value of the taiendavNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaiendavNimi(String value) {
        this.taiendavNimi = value;
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
     * Gets the value of the oiguslikuVormiAlaliik property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOiguslikuVormiAlaliik() {
        return oiguslikuVormiAlaliik;
    }

    /**
     * Sets the value of the oiguslikuVormiAlaliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOiguslikuVormiAlaliik(JAXBElement<String> value) {
        this.oiguslikuVormiAlaliik = value;
    }

    /**
     * Gets the value of the asukoht property.
     * 
     * @return
     *     possible object is
     *     {@link AadressTypeV4 }
     *     
     */
    public AadressTypeV4 getAsukoht() {
        return asukoht;
    }

    /**
     * Sets the value of the asukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link AadressTypeV4 }
     *     
     */
    public void setAsukoht(AadressTypeV4 value) {
        this.asukoht = value;
    }

    /**
     * Gets the value of the evSeisund property.
     * 
     * @return
     *     possible object is
     *     {@link EvSeisundTypeV4 }
     *     
     */
    public EvSeisundTypeV4 getEvSeisund() {
        return evSeisund;
    }

    /**
     * Sets the value of the evSeisund property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvSeisundTypeV4 }
     *     
     */
    public void setEvSeisund(EvSeisundTypeV4 value) {
        this.evSeisund = value;
    }

    /**
     * Gets the value of the pohitegevusala property.
     * 
     * @return
     *     possible object is
     *     {@link TegevusalaTypeV4 }
     *     
     */
    public TegevusalaTypeV4 getPohitegevusala() {
        return pohitegevusala;
    }

    /**
     * Sets the value of the pohitegevusala property.
     * 
     * @param value
     *     allowed object is
     *     {@link TegevusalaTypeV4 }
     *     
     */
    public void setPohitegevusala(TegevusalaTypeV4 value) {
        this.pohitegevusala = value;
    }

    /**
     * Gets the value of the lisategevusala property.
     * 
     * @return
     *     possible object is
     *     {@link TegevusalaTypeV4 }
     *     
     */
    public TegevusalaTypeV4 getLisategevusala() {
        return lisategevusala;
    }

    /**
     * Sets the value of the lisategevusala property.
     * 
     * @param value
     *     allowed object is
     *     {@link TegevusalaTypeV4 }
     *     
     */
    public void setLisategevusala(TegevusalaTypeV4 value) {
        this.lisategevusala = value;
    }

    /**
     * Gets the value of the eesmargid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eesmargid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEesmargid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EesmargidTypeV4 }
     * 
     * 
     */
    public List<EesmargidTypeV4> getEesmargid() {
        if (eesmargid == null) {
            eesmargid = new ArrayList<EesmargidTypeV4>();
        }
        return this.eesmargid;
    }

    /**
     * Gets the value of the majandusaasta property.
     * 
     * @return
     *     possible object is
     *     {@link SooritakanneMajaastaV4 }
     *     
     */
    public SooritakanneMajaastaV4 getMajandusaasta() {
        return majandusaasta;
    }

    /**
     * Sets the value of the majandusaasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link SooritakanneMajaastaV4 }
     *     
     */
    public void setMajandusaasta(SooritakanneMajaastaV4 value) {
        this.majandusaasta = value;
    }

    /**
     * Gets the value of the kapital property.
     * 
     * @return
     *     possible object is
     *     {@link SooritakanneKapitalV4 }
     *     
     */
    public SooritakanneKapitalV4 getKapital() {
        return kapital;
    }

    /**
     * Sets the value of the kapital property.
     * 
     * @param value
     *     allowed object is
     *     {@link SooritakanneKapitalV4 }
     *     
     */
    public void setKapital(SooritakanneKapitalV4 value) {
        this.kapital = value;
    }

    /**
     * Gets the value of the kapitaliTingimuslikSuurendamine property.
     * 
     * @return
     *     possible object is
     *     {@link SooritakanneKapitaliTingimuslikSuurendamineV4 }
     *     
     */
    public SooritakanneKapitaliTingimuslikSuurendamineV4 getKapitaliTingimuslikSuurendamine() {
        return kapitaliTingimuslikSuurendamine;
    }

    /**
     * Sets the value of the kapitaliTingimuslikSuurendamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link SooritakanneKapitaliTingimuslikSuurendamineV4 }
     *     
     */
    public void setKapitaliTingimuslikSuurendamine(SooritakanneKapitaliTingimuslikSuurendamineV4 value) {
        this.kapitaliTingimuslikSuurendamine = value;
    }

    /**
     * Gets the value of the nimivaartusetaAktsiateArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNimivaartusetaAktsiateArv() {
        return nimivaartusetaAktsiateArv;
    }

    /**
     * Sets the value of the nimivaartusetaAktsiateArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNimivaartusetaAktsiateArv(BigInteger value) {
        this.nimivaartusetaAktsiateArv = value;
    }

    /**
     * Gets the value of the asutatudSissemaksetTegemata property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getAsutatudSissemaksetTegemata() {
        return asutatudSissemaksetTegemata;
    }

    /**
     * Sets the value of the asutatudSissemaksetTegemata property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setAsutatudSissemaksetTegemata(JAXBElement<Boolean> value) {
        this.asutatudSissemaksetTegemata = value;
    }

    /**
     * Gets the value of the omandiOsad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the omandiOsad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOmandiOsad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OmandiOsadTypeV4 }
     * 
     * 
     */
    public List<OmandiOsadTypeV4> getOmandiOsad() {
        if (omandiOsad == null) {
            omandiOsad = new ArrayList<OmandiOsadTypeV4>();
        }
        return this.omandiOsad;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the isikud property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIsikud().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IsikTypeV4 }
     * 
     * 
     */
    public List<IsikTypeV4> getIsikud() {
        if (isikud == null) {
            isikud = new ArrayList<IsikTypeV4>();
        }
        return this.isikud;
    }

    /**
     * Gets the value of the isikudEsindajadStork property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the isikudEsindajadStork property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIsikudEsindajadStork().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IsikesindajastorkTypeV4 }
     * 
     * 
     */
    public List<IsikesindajastorkTypeV4> getIsikudEsindajadStork() {
        if (isikudEsindajadStork == null) {
            isikudEsindajadStork = new ArrayList<IsikesindajastorkTypeV4>();
        }
        return this.isikudEsindajadStork;
    }

    /**
     * Gets the value of the esindusoigused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esindusoigused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsindusoigused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsindusoigusTypeV4 }
     * 
     * 
     */
    public List<EsindusoigusTypeV4> getEsindusoigused() {
        if (esindusoigused == null) {
            esindusoigused = new ArrayList<EsindusoigusTypeV4>();
        }
        return this.esindusoigused;
    }

    /**
     * Gets the value of the pohikiri property.
     * 
     * @return
     *     possible object is
     *     {@link SooritakannePohikiriV4 }
     *     
     */
    public SooritakannePohikiriV4 getPohikiri() {
        return pohikiri;
    }

    /**
     * Sets the value of the pohikiri property.
     * 
     * @param value
     *     allowed object is
     *     {@link SooritakannePohikiriV4 }
     *     
     */
    public void setPohikiri(SooritakannePohikiriV4 value) {
        this.pohikiri = value;
    }

    /**
     * Gets the value of the sidevahendid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sidevahendid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSidevahendid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SidevahendTypeV4 }
     * 
     * 
     */
    public List<SidevahendTypeV4> getSidevahendid() {
        if (sidevahendid == null) {
            sidevahendid = new ArrayList<SidevahendTypeV4>();
        }
        return this.sidevahendid;
    }

    /**
     * Gets the value of the markused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the markused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMarkused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMarkused() {
        if (markused == null) {
            markused = new ArrayList<String>();
        }
        return this.markused;
    }

    /**
     * Gets the value of the tegutsemiseAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTegutsemiseAlgus() {
        return tegutsemiseAlgus;
    }

    /**
     * Sets the value of the tegutsemiseAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTegutsemiseAlgus(XMLGregorianCalendar value) {
        this.tegutsemiseAlgus = value;
    }

    /**
     * Gets the value of the tegutsemiseTahtaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTegutsemiseTahtaeg() {
        return tegutsemiseTahtaeg;
    }

    /**
     * Sets the value of the tegutsemiseTahtaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTegutsemiseTahtaeg(XMLGregorianCalendar value) {
        this.tegutsemiseTahtaeg = value;
    }

    /**
     * Gets the value of the asutamiseAeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsutamiseAeg() {
        return asutamiseAeg;
    }

    /**
     * Sets the value of the asutamiseAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsutamiseAeg(String value) {
        this.asutamiseAeg = value;
    }

    /**
     * Gets the value of the viideEelkaijale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViideEelkaijale() {
        return viideEelkaijale;
    }

    /**
     * Sets the value of the viideEelkaijale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViideEelkaijale(String value) {
        this.viideEelkaijale = value;
    }

    /**
     * Gets the value of the onRaamatupidamiskohuslane property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnRaamatupidamiskohuslane() {
        return onRaamatupidamiskohuslane;
    }

    /**
     * Sets the value of the onRaamatupidamiskohuslane property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnRaamatupidamiskohuslane(String value) {
        this.onRaamatupidamiskohuslane = value;
    }

    /**
     * Gets the value of the kinnisasjad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kinnisasjad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKinnisasjad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KinnisasiTypeV4 }
     * 
     * 
     */
    public List<KinnisasiTypeV4> getKinnisasjad() {
        if (kinnisasjad == null) {
            kinnisasjad = new ArrayList<KinnisasiTypeV4>();
        }
        return this.kinnisasjad;
    }

    /**
     * Gets the value of the valismaaEmaettevote property.
     * 
     * @return
     *     possible object is
     *     {@link ValismaaEmaettevoteTypeV4 }
     *     
     */
    public ValismaaEmaettevoteTypeV4 getValismaaEmaettevote() {
        return valismaaEmaettevote;
    }

    /**
     * Sets the value of the valismaaEmaettevote property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValismaaEmaettevoteTypeV4 }
     *     
     */
    public void setValismaaEmaettevote(ValismaaEmaettevoteTypeV4 value) {
        this.valismaaEmaettevote = value;
    }

    /**
     * Gets the value of the fieSeisundid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieSeisundid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieSeisundid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieSeisundTypeV4 }
     * 
     * 
     */
    public List<FieSeisundTypeV4> getFieSeisundid() {
        if (fieSeisundid == null) {
            fieSeisundid = new ArrayList<FieSeisundTypeV4>();
        }
        return this.fieSeisundid;
    }

    /**
     * Gets the value of the maksRegKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaksRegKpv() {
        return maksRegKpv;
    }

    /**
     * Sets the value of the maksRegKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaksRegKpv(XMLGregorianCalendar value) {
        this.maksRegKpv = value;
    }

    /**
     * Gets the value of the elamuAadressid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elamuAadressid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElamuAadressid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AadresskatastrigaTypeV4 }
     * 
     * 
     */
    public List<AadresskatastrigaTypeV4> getElamuAadressid() {
        if (elamuAadressid == null) {
            elamuAadressid = new ArrayList<AadresskatastrigaTypeV4>();
        }
        return this.elamuAadressid;
    }

    /**
     * Gets the value of the korteriomandiKinnistud property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the korteriomandiKinnistud property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKorteriomandiKinnistud().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKorteriomandiKinnistud() {
        if (korteriomandiKinnistud == null) {
            korteriomandiKinnistud = new ArrayList<String>();
        }
        return this.korteriomandiKinnistud;
    }

    /**
     * Gets the value of the asutalusTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAsutalusTekstina() {
        return asutalusTekstina;
    }

    /**
     * Sets the value of the asutalusTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAsutalusTekstina(JAXBElement<String> value) {
        this.asutalusTekstina = value;
    }

    /**
     * Gets the value of the kustalusTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKustalusTekstina() {
        return kustalusTekstina;
    }

    /**
     * Sets the value of the kustalusTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKustalusTekstina(JAXBElement<String> value) {
        this.kustalusTekstina = value;
    }

}
