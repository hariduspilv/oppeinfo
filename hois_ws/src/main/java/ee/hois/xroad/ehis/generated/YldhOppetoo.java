
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yldhOppetoo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yldhOppetoo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="haridusSisseastumisel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esimesseKlassi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeaasta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppimaKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rahastamiseAllikas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paralleeliTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liitklassiTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kooliaste" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klassiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kaugusOppeasutusest" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koolOpilaskodus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="osaleminePikapaevaryhmas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="opilaskoduKohaFinantseerimine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusestLahkumine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pohikooliViimaneKlass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="gymnaasiumiViimaneKlass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekohtId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="lahkumiseKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="onOppiv" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean"/&gt;
 *         &lt;element name="pohjusetaPuudumised" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pohjusetaPuudumised" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yldhOppetoo", propOrder = {
    "haridusSisseastumisel",
    "esimesseKlassi",
    "oppeaasta",
    "oppimaKp",
    "oppeasutus",
    "oppekava",
    "oppekeel",
    "oppevorm",
    "rahastamiseAllikas",
    "klass",
    "paralleeliTunnus",
    "liitklassiTunnus",
    "kooliaste",
    "klassiLiik",
    "kaugusOppeasutusest",
    "koolOpilaskodus",
    "osaleminePikapaevaryhmas",
    "opilaskoduKohaFinantseerimine",
    "oppeasutusestLahkumine",
    "pohikooliViimaneKlass",
    "gymnaasiumiViimaneKlass",
    "muutusKp",
    "oppekohtId",
    "lahkumiseKp",
    "onOppiv",
    "pohjusetaPuudumised"
})
public class YldhOppetoo {

    @XmlElement(required = true, nillable = true)
    protected String haridusSisseastumisel;
    @XmlElement(required = true, nillable = true)
    protected String esimesseKlassi;
    @XmlElement(required = true, nillable = true)
    protected String oppeaasta;
    @XmlElement(required = true, nillable = true)
    protected String oppimaKp;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutus;
    @XmlElement(required = true, nillable = true)
    protected String oppekava;
    @XmlElement(required = true, nillable = true)
    protected String oppekeel;
    @XmlElement(required = true, nillable = true)
    protected String oppevorm;
    @XmlElement(required = true, nillable = true)
    protected String rahastamiseAllikas;
    @XmlElement(required = true, nillable = true)
    protected String klass;
    @XmlElement(required = true, nillable = true)
    protected String paralleeliTunnus;
    @XmlElement(required = true, nillable = true)
    protected String liitklassiTunnus;
    @XmlElement(required = true, nillable = true)
    protected String kooliaste;
    @XmlElement(required = true, nillable = true)
    protected String klassiLiik;
    @XmlElement(required = true, nillable = true)
    protected String kaugusOppeasutusest;
    @XmlElement(required = true, nillable = true)
    protected String koolOpilaskodus;
    @XmlElement(required = true, nillable = true)
    protected String osaleminePikapaevaryhmas;
    @XmlElement(required = true, nillable = true)
    protected String opilaskoduKohaFinantseerimine;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutusestLahkumine;
    @XmlElement(required = true, nillable = true)
    protected String pohikooliViimaneKlass;
    @XmlElement(required = true, nillable = true)
    protected String gymnaasiumiViimaneKlass;
    @XmlElement(required = true, nillable = true)
    protected String muutusKp;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long oppekohtId;
    @XmlElement(required = true, nillable = true)
    protected String lahkumiseKp;
    @XmlElement(required = true, nillable = true)
    protected String onOppiv;
    @XmlElement(nillable = true)
    protected List<PohjusetaPuudumised> pohjusetaPuudumised;

    /**
     * Gets the value of the haridusSisseastumisel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaridusSisseastumisel() {
        return haridusSisseastumisel;
    }

    /**
     * Sets the value of the haridusSisseastumisel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaridusSisseastumisel(String value) {
        this.haridusSisseastumisel = value;
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
     * Gets the value of the oppeaasta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeaasta() {
        return oppeaasta;
    }

    /**
     * Sets the value of the oppeaasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeaasta(String value) {
        this.oppeaasta = value;
    }

    /**
     * Gets the value of the oppimaKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppimaKp() {
        return oppimaKp;
    }

    /**
     * Sets the value of the oppimaKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppimaKp(String value) {
        this.oppimaKp = value;
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
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekava(String value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the oppekeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekeel() {
        return oppekeel;
    }

    /**
     * Sets the value of the oppekeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekeel(String value) {
        this.oppekeel = value;
    }

    /**
     * Gets the value of the oppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppevorm() {
        return oppevorm;
    }

    /**
     * Sets the value of the oppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppevorm(String value) {
        this.oppevorm = value;
    }

    /**
     * Gets the value of the rahastamiseAllikas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRahastamiseAllikas() {
        return rahastamiseAllikas;
    }

    /**
     * Sets the value of the rahastamiseAllikas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRahastamiseAllikas(String value) {
        this.rahastamiseAllikas = value;
    }

    /**
     * Gets the value of the klass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlass() {
        return klass;
    }

    /**
     * Sets the value of the klass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlass(String value) {
        this.klass = value;
    }

    /**
     * Gets the value of the paralleeliTunnus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParalleeliTunnus() {
        return paralleeliTunnus;
    }

    /**
     * Sets the value of the paralleeliTunnus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParalleeliTunnus(String value) {
        this.paralleeliTunnus = value;
    }

    /**
     * Gets the value of the liitklassiTunnus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiitklassiTunnus() {
        return liitklassiTunnus;
    }

    /**
     * Sets the value of the liitklassiTunnus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiitklassiTunnus(String value) {
        this.liitklassiTunnus = value;
    }

    /**
     * Gets the value of the kooliaste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliaste() {
        return kooliaste;
    }

    /**
     * Sets the value of the kooliaste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliaste(String value) {
        this.kooliaste = value;
    }

    /**
     * Gets the value of the klassiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassiLiik() {
        return klassiLiik;
    }

    /**
     * Sets the value of the klassiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassiLiik(String value) {
        this.klassiLiik = value;
    }

    /**
     * Gets the value of the kaugusOppeasutusest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKaugusOppeasutusest() {
        return kaugusOppeasutusest;
    }

    /**
     * Sets the value of the kaugusOppeasutusest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKaugusOppeasutusest(String value) {
        this.kaugusOppeasutusest = value;
    }

    /**
     * Gets the value of the koolOpilaskodus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoolOpilaskodus() {
        return koolOpilaskodus;
    }

    /**
     * Sets the value of the koolOpilaskodus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoolOpilaskodus(String value) {
        this.koolOpilaskodus = value;
    }

    /**
     * Gets the value of the osaleminePikapaevaryhmas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOsaleminePikapaevaryhmas() {
        return osaleminePikapaevaryhmas;
    }

    /**
     * Sets the value of the osaleminePikapaevaryhmas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOsaleminePikapaevaryhmas(String value) {
        this.osaleminePikapaevaryhmas = value;
    }

    /**
     * Gets the value of the opilaskoduKohaFinantseerimine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpilaskoduKohaFinantseerimine() {
        return opilaskoduKohaFinantseerimine;
    }

    /**
     * Sets the value of the opilaskoduKohaFinantseerimine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpilaskoduKohaFinantseerimine(String value) {
        this.opilaskoduKohaFinantseerimine = value;
    }

    /**
     * Gets the value of the oppeasutusestLahkumine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusestLahkumine() {
        return oppeasutusestLahkumine;
    }

    /**
     * Sets the value of the oppeasutusestLahkumine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusestLahkumine(String value) {
        this.oppeasutusestLahkumine = value;
    }

    /**
     * Gets the value of the pohikooliViimaneKlass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohikooliViimaneKlass() {
        return pohikooliViimaneKlass;
    }

    /**
     * Sets the value of the pohikooliViimaneKlass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohikooliViimaneKlass(String value) {
        this.pohikooliViimaneKlass = value;
    }

    /**
     * Gets the value of the gymnaasiumiViimaneKlass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGymnaasiumiViimaneKlass() {
        return gymnaasiumiViimaneKlass;
    }

    /**
     * Sets the value of the gymnaasiumiViimaneKlass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGymnaasiumiViimaneKlass(String value) {
        this.gymnaasiumiViimaneKlass = value;
    }

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuutusKp(String value) {
        this.muutusKp = value;
    }

    /**
     * Gets the value of the oppekohtId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOppekohtId() {
        return oppekohtId;
    }

    /**
     * Sets the value of the oppekohtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOppekohtId(Long value) {
        this.oppekohtId = value;
    }

    /**
     * Gets the value of the lahkumiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLahkumiseKp() {
        return lahkumiseKp;
    }

    /**
     * Sets the value of the lahkumiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLahkumiseKp(String value) {
        this.lahkumiseKp = value;
    }

    /**
     * Gets the value of the onOppiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnOppiv() {
        return onOppiv;
    }

    /**
     * Sets the value of the onOppiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnOppiv(String value) {
        this.onOppiv = value;
    }

    /**
     * Gets the value of the pohjusetaPuudumised property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pohjusetaPuudumised property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPohjusetaPuudumised().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PohjusetaPuudumised }
     * 
     * 
     */
    public List<PohjusetaPuudumised> getPohjusetaPuudumised() {
        if (pohjusetaPuudumised == null) {
            pohjusetaPuudumised = new ArrayList<PohjusetaPuudumised>();
        }
        return this.pohjusetaPuudumised;
    }

}
