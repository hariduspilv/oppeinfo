
package ee.hois.xroad.ariregister.generated;

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
 * <p>Java class for SooritaKanne_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SooritaKanne_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kande_paritolu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kande_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ekande_liik" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="on_paranduskanne" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="eelmine_lahend_legacy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eelmine_paevikukande_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eelmine_ekande_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="on_kiirmenetlus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="kr_menetluse_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="esitaja_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esitaja_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asutamisnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ametitoiming" type="{http://arireg.x-road.eu/producer/}sooritakanne_ametitoiming_v4" minOccurs="0"/&gt;
 *         &lt;element name="riigiloivud" type="{http://arireg.x-road.eu/producer/}riigiloivType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="viitenumber_loiv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="viitenumber_okap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registri_piirkond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisa_arp_markus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="eemalda_arp_markus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="ettevotjad" type="{http://arireg.x-road.eu/producer/}sooritakanne_ettevotjad_v4" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SooritaKanne_v4", propOrder = {
    "kandeParitolu",
    "kandeId",
    "ekandeLiik",
    "onParanduskanne",
    "eelmineLahendLegacy",
    "eelminePaevikukandeId",
    "eelmineEkandeId",
    "onKiirmenetlus",
    "krMenetluseNumber",
    "kandeKuupaev",
    "esitajaNimi",
    "esitajaKood",
    "asutamisnumber",
    "ametitoiming",
    "riigiloivud",
    "viitenumberLoiv",
    "viitenumberOkap",
    "registriPiirkond",
    "lisaArpMarkus",
    "eemaldaArpMarkus",
    "ettevotjad"
})
public class SooritaKanneV4 {

    @XmlElement(name = "kande_paritolu", required = true)
    protected String kandeParitolu;
    @XmlElement(name = "kande_id", required = true)
    protected String kandeId;
    @XmlElement(name = "ekande_liik")
    protected int ekandeLiik;
    @XmlElement(name = "on_paranduskanne")
    protected Boolean onParanduskanne;
    @XmlElement(name = "eelmine_lahend_legacy")
    protected String eelmineLahendLegacy;
    @XmlElement(name = "eelmine_paevikukande_id")
    protected String eelminePaevikukandeId;
    @XmlElement(name = "eelmine_ekande_id")
    protected String eelmineEkandeId;
    @XmlElement(name = "on_kiirmenetlus")
    protected Boolean onKiirmenetlus;
    @XmlElement(name = "kr_menetluse_number")
    protected String krMenetluseNumber;
    @XmlElement(name = "kande_kuupaev", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kandeKuupaev;
    @XmlElement(name = "esitaja_nimi")
    protected String esitajaNimi;
    @XmlElement(name = "esitaja_kood")
    protected String esitajaKood;
    protected String asutamisnumber;
    protected SooritakanneAmetitoimingV4 ametitoiming;
    @XmlElement(nillable = true)
    protected List<RiigiloivTypeV4> riigiloivud;
    @XmlElement(name = "viitenumber_loiv")
    protected String viitenumberLoiv;
    @XmlElement(name = "viitenumber_okap")
    protected String viitenumberOkap;
    @XmlElement(name = "registri_piirkond")
    protected String registriPiirkond;
    @XmlElementRef(name = "lisa_arp_markus", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> lisaArpMarkus;
    @XmlElementRef(name = "eemalda_arp_markus", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> eemaldaArpMarkus;
    @XmlElement(required = true)
    protected List<SooritakanneEttevotjadV4> ettevotjad;

    /**
     * Gets the value of the kandeParitolu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeParitolu() {
        return kandeParitolu;
    }

    /**
     * Sets the value of the kandeParitolu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeParitolu(String value) {
        this.kandeParitolu = value;
    }

    /**
     * Gets the value of the kandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeId() {
        return kandeId;
    }

    /**
     * Sets the value of the kandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeId(String value) {
        this.kandeId = value;
    }

    /**
     * Gets the value of the ekandeLiik property.
     * 
     */
    public int getEkandeLiik() {
        return ekandeLiik;
    }

    /**
     * Sets the value of the ekandeLiik property.
     * 
     */
    public void setEkandeLiik(int value) {
        this.ekandeLiik = value;
    }

    /**
     * Gets the value of the onParanduskanne property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOnParanduskanne() {
        return onParanduskanne;
    }

    /**
     * Sets the value of the onParanduskanne property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnParanduskanne(Boolean value) {
        this.onParanduskanne = value;
    }

    /**
     * Gets the value of the eelmineLahendLegacy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelmineLahendLegacy() {
        return eelmineLahendLegacy;
    }

    /**
     * Sets the value of the eelmineLahendLegacy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelmineLahendLegacy(String value) {
        this.eelmineLahendLegacy = value;
    }

    /**
     * Gets the value of the eelminePaevikukandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelminePaevikukandeId() {
        return eelminePaevikukandeId;
    }

    /**
     * Sets the value of the eelminePaevikukandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelminePaevikukandeId(String value) {
        this.eelminePaevikukandeId = value;
    }

    /**
     * Gets the value of the eelmineEkandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelmineEkandeId() {
        return eelmineEkandeId;
    }

    /**
     * Sets the value of the eelmineEkandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelmineEkandeId(String value) {
        this.eelmineEkandeId = value;
    }

    /**
     * Gets the value of the onKiirmenetlus property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOnKiirmenetlus() {
        return onKiirmenetlus;
    }

    /**
     * Sets the value of the onKiirmenetlus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnKiirmenetlus(Boolean value) {
        this.onKiirmenetlus = value;
    }

    /**
     * Gets the value of the krMenetluseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKrMenetluseNumber() {
        return krMenetluseNumber;
    }

    /**
     * Sets the value of the krMenetluseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKrMenetluseNumber(String value) {
        this.krMenetluseNumber = value;
    }

    /**
     * Gets the value of the kandeKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKandeKuupaev() {
        return kandeKuupaev;
    }

    /**
     * Sets the value of the kandeKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKandeKuupaev(XMLGregorianCalendar value) {
        this.kandeKuupaev = value;
    }

    /**
     * Gets the value of the esitajaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaNimi() {
        return esitajaNimi;
    }

    /**
     * Sets the value of the esitajaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaNimi(String value) {
        this.esitajaNimi = value;
    }

    /**
     * Gets the value of the esitajaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaKood() {
        return esitajaKood;
    }

    /**
     * Sets the value of the esitajaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaKood(String value) {
        this.esitajaKood = value;
    }

    /**
     * Gets the value of the asutamisnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsutamisnumber() {
        return asutamisnumber;
    }

    /**
     * Sets the value of the asutamisnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsutamisnumber(String value) {
        this.asutamisnumber = value;
    }

    /**
     * Gets the value of the ametitoiming property.
     * 
     * @return
     *     possible object is
     *     {@link SooritakanneAmetitoimingV4 }
     *     
     */
    public SooritakanneAmetitoimingV4 getAmetitoiming() {
        return ametitoiming;
    }

    /**
     * Sets the value of the ametitoiming property.
     * 
     * @param value
     *     allowed object is
     *     {@link SooritakanneAmetitoimingV4 }
     *     
     */
    public void setAmetitoiming(SooritakanneAmetitoimingV4 value) {
        this.ametitoiming = value;
    }

    /**
     * Gets the value of the riigiloivud property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riigiloivud property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiigiloivud().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RiigiloivTypeV4 }
     * 
     * 
     */
    public List<RiigiloivTypeV4> getRiigiloivud() {
        if (riigiloivud == null) {
            riigiloivud = new ArrayList<RiigiloivTypeV4>();
        }
        return this.riigiloivud;
    }

    /**
     * Gets the value of the viitenumberLoiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViitenumberLoiv() {
        return viitenumberLoiv;
    }

    /**
     * Sets the value of the viitenumberLoiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViitenumberLoiv(String value) {
        this.viitenumberLoiv = value;
    }

    /**
     * Gets the value of the viitenumberOkap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViitenumberOkap() {
        return viitenumberOkap;
    }

    /**
     * Sets the value of the viitenumberOkap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViitenumberOkap(String value) {
        this.viitenumberOkap = value;
    }

    /**
     * Gets the value of the registriPiirkond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistriPiirkond() {
        return registriPiirkond;
    }

    /**
     * Sets the value of the registriPiirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistriPiirkond(String value) {
        this.registriPiirkond = value;
    }

    /**
     * Gets the value of the lisaArpMarkus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getLisaArpMarkus() {
        return lisaArpMarkus;
    }

    /**
     * Sets the value of the lisaArpMarkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setLisaArpMarkus(JAXBElement<Boolean> value) {
        this.lisaArpMarkus = value;
    }

    /**
     * Gets the value of the eemaldaArpMarkus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getEemaldaArpMarkus() {
        return eemaldaArpMarkus;
    }

    /**
     * Sets the value of the eemaldaArpMarkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setEemaldaArpMarkus(JAXBElement<Boolean> value) {
        this.eemaldaArpMarkus = value;
    }

    /**
     * Gets the value of the ettevotjad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ettevotjad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEttevotjad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SooritakanneEttevotjadV4 }
     * 
     * 
     */
    public List<SooritakanneEttevotjadV4> getEttevotjad() {
        if (ettevotjad == null) {
            ettevotjad = new ArrayList<SooritakanneEttevotjadV4>();
        }
        return this.ettevotjad;
    }

}
