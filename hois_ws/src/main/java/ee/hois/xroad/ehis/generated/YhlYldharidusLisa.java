
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlYldharidusLisa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlYldharidusLisa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlIsikuandmed"/&gt;
 *         &lt;element name="klEelnevHaridus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esimKlAasta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisAastaType" minOccurs="0"/&gt;
 *         &lt;element name="oppimaAsumKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klOppekava" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klOppekeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klOppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klKlass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="paralleeliTunnus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="liitklassiTunnus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klKlassAste" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klKlassLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="voorkeeled" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlVoorkeeled" minOccurs="0"/&gt;
 *         &lt;element name="lisaandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlLisaandmed" minOccurs="0"/&gt;
 *         &lt;element name="kaugusOppeasutusest" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusFil" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="opilaskoduKool" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="omandabHaridKinnipidamisasutuses" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="puudulikudAastahinded" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlPuudulikudAastahinded" minOccurs="0"/&gt;
 *         &lt;element name="erivajadusedTugiteenused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlErivajadusedTugiteenused" minOccurs="0"/&gt;
 *         &lt;element name="vanemaNousolekud" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlVanemaNousolekud" minOccurs="0"/&gt;
 *         &lt;element name="oppevormiMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlOppevormMuutus" minOccurs="0"/&gt;
 *         &lt;element name="klOpilaskoduFin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pikapaevaruhm" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ujumisandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlUjumisandmed" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlYldharidusLisa", propOrder = {
    "isikuandmed",
    "klEelnevHaridus",
    "esimKlAasta",
    "oppimaAsumKp",
    "klOppekava",
    "klOppekeel",
    "klOppevorm",
    "klKlass",
    "paralleeliTunnus",
    "liitklassiTunnus",
    "klKlassAste",
    "klKlassLiik",
    "voorkeeled",
    "lisaandmed",
    "kaugusOppeasutusest",
    "oppeasutusFil",
    "opilaskoduKool",
    "omandabHaridKinnipidamisasutuses",
    "puudulikudAastahinded",
    "erivajadusedTugiteenused",
    "vanemaNousolekud",
    "oppevormiMuutus",
    "klOpilaskoduFin",
    "pikapaevaruhm",
    "lisainfo1",
    "lisainfo2",
    "lisainfo3",
    "ujumisandmed"
})
public class YhlYldharidusLisa {

    @XmlElement(required = true)
    protected YhlIsikuandmed isikuandmed;
    protected String klEelnevHaridus;
    @XmlSchemaType(name = "positiveInteger")
    protected Integer esimKlAasta;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppimaAsumKp;
    @XmlElement(required = true)
    protected String klOppekava;
    @XmlElement(required = true)
    protected String klOppekeel;
    @XmlElement(required = true)
    protected String klOppevorm;
    protected String klKlass;
    protected String paralleeliTunnus;
    protected String liitklassiTunnus;
    @XmlElement(required = true)
    protected String klKlassAste;
    @XmlElement(required = true)
    protected String klKlassLiik;
    protected YhlVoorkeeled voorkeeled;
    protected YhlLisaandmed lisaandmed;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger kaugusOppeasutusest;
    protected BigInteger oppeasutusFil;
    protected BigInteger opilaskoduKool;
    protected String omandabHaridKinnipidamisasutuses;
    protected YhlPuudulikudAastahinded puudulikudAastahinded;
    protected YhlErivajadusedTugiteenused erivajadusedTugiteenused;
    protected YhlVanemaNousolekud vanemaNousolekud;
    protected YhlOppevormMuutus oppevormiMuutus;
    protected String klOpilaskoduFin;
    protected String pikapaevaruhm;
    protected String lisainfo1;
    protected String lisainfo2;
    protected String lisainfo3;
    protected YhlUjumisandmed ujumisandmed;

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link YhlIsikuandmed }
     *     
     */
    public YhlIsikuandmed getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlIsikuandmed }
     *     
     */
    public void setIsikuandmed(YhlIsikuandmed value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the klEelnevHaridus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEelnevHaridus() {
        return klEelnevHaridus;
    }

    /**
     * Sets the value of the klEelnevHaridus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEelnevHaridus(String value) {
        this.klEelnevHaridus = value;
    }

    /**
     * Gets the value of the esimKlAasta property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEsimKlAasta() {
        return esimKlAasta;
    }

    /**
     * Sets the value of the esimKlAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEsimKlAasta(Integer value) {
        this.esimKlAasta = value;
    }

    /**
     * Gets the value of the oppimaAsumKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppimaAsumKp() {
        return oppimaAsumKp;
    }

    /**
     * Sets the value of the oppimaAsumKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppimaAsumKp(XMLGregorianCalendar value) {
        this.oppimaAsumKp = value;
    }

    /**
     * Gets the value of the klOppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekava() {
        return klOppekava;
    }

    /**
     * Sets the value of the klOppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekava(String value) {
        this.klOppekava = value;
    }

    /**
     * Gets the value of the klOppekeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekeel() {
        return klOppekeel;
    }

    /**
     * Sets the value of the klOppekeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekeel(String value) {
        this.klOppekeel = value;
    }

    /**
     * Gets the value of the klOppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppevorm() {
        return klOppevorm;
    }

    /**
     * Sets the value of the klOppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppevorm(String value) {
        this.klOppevorm = value;
    }

    /**
     * Gets the value of the klKlass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKlass() {
        return klKlass;
    }

    /**
     * Sets the value of the klKlass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKlass(String value) {
        this.klKlass = value;
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
     * Gets the value of the klKlassAste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKlassAste() {
        return klKlassAste;
    }

    /**
     * Sets the value of the klKlassAste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKlassAste(String value) {
        this.klKlassAste = value;
    }

    /**
     * Gets the value of the klKlassLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKlassLiik() {
        return klKlassLiik;
    }

    /**
     * Sets the value of the klKlassLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKlassLiik(String value) {
        this.klKlassLiik = value;
    }

    /**
     * Gets the value of the voorkeeled property.
     * 
     * @return
     *     possible object is
     *     {@link YhlVoorkeeled }
     *     
     */
    public YhlVoorkeeled getVoorkeeled() {
        return voorkeeled;
    }

    /**
     * Sets the value of the voorkeeled property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlVoorkeeled }
     *     
     */
    public void setVoorkeeled(YhlVoorkeeled value) {
        this.voorkeeled = value;
    }

    /**
     * Gets the value of the lisaandmed property.
     * 
     * @return
     *     possible object is
     *     {@link YhlLisaandmed }
     *     
     */
    public YhlLisaandmed getLisaandmed() {
        return lisaandmed;
    }

    /**
     * Sets the value of the lisaandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlLisaandmed }
     *     
     */
    public void setLisaandmed(YhlLisaandmed value) {
        this.lisaandmed = value;
    }

    /**
     * Gets the value of the kaugusOppeasutusest property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKaugusOppeasutusest() {
        return kaugusOppeasutusest;
    }

    /**
     * Sets the value of the kaugusOppeasutusest property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKaugusOppeasutusest(BigInteger value) {
        this.kaugusOppeasutusest = value;
    }

    /**
     * Gets the value of the oppeasutusFil property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeasutusFil() {
        return oppeasutusFil;
    }

    /**
     * Sets the value of the oppeasutusFil property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeasutusFil(BigInteger value) {
        this.oppeasutusFil = value;
    }

    /**
     * Gets the value of the opilaskoduKool property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOpilaskoduKool() {
        return opilaskoduKool;
    }

    /**
     * Sets the value of the opilaskoduKool property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOpilaskoduKool(BigInteger value) {
        this.opilaskoduKool = value;
    }

    /**
     * Gets the value of the omandabHaridKinnipidamisasutuses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandabHaridKinnipidamisasutuses() {
        return omandabHaridKinnipidamisasutuses;
    }

    /**
     * Sets the value of the omandabHaridKinnipidamisasutuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandabHaridKinnipidamisasutuses(String value) {
        this.omandabHaridKinnipidamisasutuses = value;
    }

    /**
     * Gets the value of the puudulikudAastahinded property.
     * 
     * @return
     *     possible object is
     *     {@link YhlPuudulikudAastahinded }
     *     
     */
    public YhlPuudulikudAastahinded getPuudulikudAastahinded() {
        return puudulikudAastahinded;
    }

    /**
     * Sets the value of the puudulikudAastahinded property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlPuudulikudAastahinded }
     *     
     */
    public void setPuudulikudAastahinded(YhlPuudulikudAastahinded value) {
        this.puudulikudAastahinded = value;
    }

    /**
     * Gets the value of the erivajadusedTugiteenused property.
     * 
     * @return
     *     possible object is
     *     {@link YhlErivajadusedTugiteenused }
     *     
     */
    public YhlErivajadusedTugiteenused getErivajadusedTugiteenused() {
        return erivajadusedTugiteenused;
    }

    /**
     * Sets the value of the erivajadusedTugiteenused property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlErivajadusedTugiteenused }
     *     
     */
    public void setErivajadusedTugiteenused(YhlErivajadusedTugiteenused value) {
        this.erivajadusedTugiteenused = value;
    }

    /**
     * Gets the value of the vanemaNousolekud property.
     * 
     * @return
     *     possible object is
     *     {@link YhlVanemaNousolekud }
     *     
     */
    public YhlVanemaNousolekud getVanemaNousolekud() {
        return vanemaNousolekud;
    }

    /**
     * Sets the value of the vanemaNousolekud property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlVanemaNousolekud }
     *     
     */
    public void setVanemaNousolekud(YhlVanemaNousolekud value) {
        this.vanemaNousolekud = value;
    }

    /**
     * Gets the value of the oppevormiMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link YhlOppevormMuutus }
     *     
     */
    public YhlOppevormMuutus getOppevormiMuutus() {
        return oppevormiMuutus;
    }

    /**
     * Sets the value of the oppevormiMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlOppevormMuutus }
     *     
     */
    public void setOppevormiMuutus(YhlOppevormMuutus value) {
        this.oppevormiMuutus = value;
    }

    /**
     * Gets the value of the klOpilaskoduFin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOpilaskoduFin() {
        return klOpilaskoduFin;
    }

    /**
     * Sets the value of the klOpilaskoduFin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOpilaskoduFin(String value) {
        this.klOpilaskoduFin = value;
    }

    /**
     * Gets the value of the pikapaevaruhm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPikapaevaruhm() {
        return pikapaevaruhm;
    }

    /**
     * Sets the value of the pikapaevaruhm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPikapaevaruhm(String value) {
        this.pikapaevaruhm = value;
    }

    /**
     * Gets the value of the lisainfo1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo1() {
        return lisainfo1;
    }

    /**
     * Sets the value of the lisainfo1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo1(String value) {
        this.lisainfo1 = value;
    }

    /**
     * Gets the value of the lisainfo2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo2() {
        return lisainfo2;
    }

    /**
     * Sets the value of the lisainfo2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo2(String value) {
        this.lisainfo2 = value;
    }

    /**
     * Gets the value of the lisainfo3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo3() {
        return lisainfo3;
    }

    /**
     * Sets the value of the lisainfo3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo3(String value) {
        this.lisainfo3 = value;
    }

    /**
     * Gets the value of the ujumisandmed property.
     * 
     * @return
     *     possible object is
     *     {@link YhlUjumisandmed }
     *     
     */
    public YhlUjumisandmed getUjumisandmed() {
        return ujumisandmed;
    }

    /**
     * Sets the value of the ujumisandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlUjumisandmed }
     *     
     */
    public void setUjumisandmed(YhlUjumisandmed value) {
        this.ujumisandmed = value;
    }

}
