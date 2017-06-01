
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlKorgharidusMuuda complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlKorgharidusMuuda"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="isikuandmeteMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlIsikuandmeteMuutus"/&gt;
 *           &lt;element name="rkKohaleVastuvotmine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlRkKohaleVastuvotmine"/&gt;
 *           &lt;element name="kursuseMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlKursuseMuutus"/&gt;
 *           &lt;element name="oppevormiMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOppevormiMuutus"/&gt;
 *           &lt;element name="oppekavaMuutus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOppekavaMuutus"/&gt;
 *           &lt;element name="juhendamised" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlJuhendamineArr"/&gt;
 *           &lt;element name="oppeasutuseLopetamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOppeasutuseLopetamine"/&gt;
 *           &lt;element name="oppeasutusestValjaarvamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOppeasutusestValjaarvamine"/&gt;
 *           &lt;element name="akadPuhkusAlgus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlAkadPuhkusAlgus"/&gt;
 *           &lt;element name="akadPuhkusLopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *           &lt;element name="oppeasutusReorganiseerimine" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *           &lt;element name="riigikeelSuvaope30EAP" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *           &lt;element name="riigikeelSuvaope60EAP" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *           &lt;element name="erivajadused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlErivajadusedArr"/&gt;
 *           &lt;element name="muudAndmedMuutmine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlMuudAndmedMuutmine"/&gt;
 *           &lt;element name="ennistamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlEnnistamine"/&gt;
 *           &lt;element name="voorkeeled" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlVoorkeeledArr"/&gt;
 *           &lt;element name="oppekavaTaitmine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOppekavaTaitmine"/&gt;
 *           &lt;element name="lyhiajaliseltValismaal" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlLyhiajaliseltValismaal"/&gt;
 *           &lt;element name="VOTAKirjed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlVOTAArr"/&gt;
 *           &lt;element name="stipendiumid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlStipendiumArr"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlKorgharidusMuuda", propOrder = {
    "isikuandmeteMuutus",
    "rkKohaleVastuvotmine",
    "kursuseMuutus",
    "oppevormiMuutus",
    "oppekavaMuutus",
    "juhendamised",
    "oppeasutuseLopetamine",
    "oppeasutusestValjaarvamine",
    "akadPuhkusAlgus",
    "akadPuhkusLopp",
    "oppeasutusReorganiseerimine",
    "riigikeelSuvaope30EAP",
    "riigikeelSuvaope60EAP",
    "erivajadused",
    "muudAndmedMuutmine",
    "ennistamine",
    "voorkeeled",
    "oppekavaTaitmine",
    "lyhiajaliseltValismaal",
    "votaKirjed",
    "stipendiumid"
})
public class KhlKorgharidusMuuda {

    protected KhlIsikuandmeteMuutus isikuandmeteMuutus;
    protected KhlRkKohaleVastuvotmine rkKohaleVastuvotmine;
    protected KhlKursuseMuutus kursuseMuutus;
    protected KhlOppevormiMuutus oppevormiMuutus;
    protected KhlOppekavaMuutus oppekavaMuutus;
    protected KhlJuhendamineArr juhendamised;
    protected KhlOppeasutuseLopetamine oppeasutuseLopetamine;
    protected KhlOppeasutusestValjaarvamine oppeasutusestValjaarvamine;
    protected KhlAkadPuhkusAlgus akadPuhkusAlgus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar akadPuhkusLopp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppeasutusReorganiseerimine;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar riigikeelSuvaope30EAP;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar riigikeelSuvaope60EAP;
    protected KhlErivajadusedArr erivajadused;
    protected KhlMuudAndmedMuutmine muudAndmedMuutmine;
    protected KhlEnnistamine ennistamine;
    protected KhlVoorkeeledArr voorkeeled;
    protected KhlOppekavaTaitmine oppekavaTaitmine;
    protected KhlLyhiajaliseltValismaal lyhiajaliseltValismaal;
    @XmlElement(name = "VOTAKirjed")
    protected KhlVOTAArr votaKirjed;
    protected KhlStipendiumArr stipendiumid;

    /**
     * Gets the value of the isikuandmeteMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link KhlIsikuandmeteMuutus }
     *     
     */
    public KhlIsikuandmeteMuutus getIsikuandmeteMuutus() {
        return isikuandmeteMuutus;
    }

    /**
     * Sets the value of the isikuandmeteMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlIsikuandmeteMuutus }
     *     
     */
    public void setIsikuandmeteMuutus(KhlIsikuandmeteMuutus value) {
        this.isikuandmeteMuutus = value;
    }

    /**
     * Gets the value of the rkKohaleVastuvotmine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlRkKohaleVastuvotmine }
     *     
     */
    public KhlRkKohaleVastuvotmine getRkKohaleVastuvotmine() {
        return rkKohaleVastuvotmine;
    }

    /**
     * Sets the value of the rkKohaleVastuvotmine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlRkKohaleVastuvotmine }
     *     
     */
    public void setRkKohaleVastuvotmine(KhlRkKohaleVastuvotmine value) {
        this.rkKohaleVastuvotmine = value;
    }

    /**
     * Gets the value of the kursuseMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link KhlKursuseMuutus }
     *     
     */
    public KhlKursuseMuutus getKursuseMuutus() {
        return kursuseMuutus;
    }

    /**
     * Sets the value of the kursuseMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlKursuseMuutus }
     *     
     */
    public void setKursuseMuutus(KhlKursuseMuutus value) {
        this.kursuseMuutus = value;
    }

    /**
     * Gets the value of the oppevormiMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link KhlOppevormiMuutus }
     *     
     */
    public KhlOppevormiMuutus getOppevormiMuutus() {
        return oppevormiMuutus;
    }

    /**
     * Sets the value of the oppevormiMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlOppevormiMuutus }
     *     
     */
    public void setOppevormiMuutus(KhlOppevormiMuutus value) {
        this.oppevormiMuutus = value;
    }

    /**
     * Gets the value of the oppekavaMuutus property.
     * 
     * @return
     *     possible object is
     *     {@link KhlOppekavaMuutus }
     *     
     */
    public KhlOppekavaMuutus getOppekavaMuutus() {
        return oppekavaMuutus;
    }

    /**
     * Sets the value of the oppekavaMuutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlOppekavaMuutus }
     *     
     */
    public void setOppekavaMuutus(KhlOppekavaMuutus value) {
        this.oppekavaMuutus = value;
    }

    /**
     * Gets the value of the juhendamised property.
     * 
     * @return
     *     possible object is
     *     {@link KhlJuhendamineArr }
     *     
     */
    public KhlJuhendamineArr getJuhendamised() {
        return juhendamised;
    }

    /**
     * Sets the value of the juhendamised property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlJuhendamineArr }
     *     
     */
    public void setJuhendamised(KhlJuhendamineArr value) {
        this.juhendamised = value;
    }

    /**
     * Gets the value of the oppeasutuseLopetamine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlOppeasutuseLopetamine }
     *     
     */
    public KhlOppeasutuseLopetamine getOppeasutuseLopetamine() {
        return oppeasutuseLopetamine;
    }

    /**
     * Sets the value of the oppeasutuseLopetamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlOppeasutuseLopetamine }
     *     
     */
    public void setOppeasutuseLopetamine(KhlOppeasutuseLopetamine value) {
        this.oppeasutuseLopetamine = value;
    }

    /**
     * Gets the value of the oppeasutusestValjaarvamine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlOppeasutusestValjaarvamine }
     *     
     */
    public KhlOppeasutusestValjaarvamine getOppeasutusestValjaarvamine() {
        return oppeasutusestValjaarvamine;
    }

    /**
     * Sets the value of the oppeasutusestValjaarvamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlOppeasutusestValjaarvamine }
     *     
     */
    public void setOppeasutusestValjaarvamine(KhlOppeasutusestValjaarvamine value) {
        this.oppeasutusestValjaarvamine = value;
    }

    /**
     * Gets the value of the akadPuhkusAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link KhlAkadPuhkusAlgus }
     *     
     */
    public KhlAkadPuhkusAlgus getAkadPuhkusAlgus() {
        return akadPuhkusAlgus;
    }

    /**
     * Sets the value of the akadPuhkusAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlAkadPuhkusAlgus }
     *     
     */
    public void setAkadPuhkusAlgus(KhlAkadPuhkusAlgus value) {
        this.akadPuhkusAlgus = value;
    }

    /**
     * Gets the value of the akadPuhkusLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAkadPuhkusLopp() {
        return akadPuhkusLopp;
    }

    /**
     * Sets the value of the akadPuhkusLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAkadPuhkusLopp(XMLGregorianCalendar value) {
        this.akadPuhkusLopp = value;
    }

    /**
     * Gets the value of the oppeasutusReorganiseerimine property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppeasutusReorganiseerimine() {
        return oppeasutusReorganiseerimine;
    }

    /**
     * Sets the value of the oppeasutusReorganiseerimine property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppeasutusReorganiseerimine(XMLGregorianCalendar value) {
        this.oppeasutusReorganiseerimine = value;
    }

    /**
     * Gets the value of the riigikeelSuvaope30EAP property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRiigikeelSuvaope30EAP() {
        return riigikeelSuvaope30EAP;
    }

    /**
     * Sets the value of the riigikeelSuvaope30EAP property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRiigikeelSuvaope30EAP(XMLGregorianCalendar value) {
        this.riigikeelSuvaope30EAP = value;
    }

    /**
     * Gets the value of the riigikeelSuvaope60EAP property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRiigikeelSuvaope60EAP() {
        return riigikeelSuvaope60EAP;
    }

    /**
     * Sets the value of the riigikeelSuvaope60EAP property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRiigikeelSuvaope60EAP(XMLGregorianCalendar value) {
        this.riigikeelSuvaope60EAP = value;
    }

    /**
     * Gets the value of the erivajadused property.
     * 
     * @return
     *     possible object is
     *     {@link KhlErivajadusedArr }
     *     
     */
    public KhlErivajadusedArr getErivajadused() {
        return erivajadused;
    }

    /**
     * Sets the value of the erivajadused property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlErivajadusedArr }
     *     
     */
    public void setErivajadused(KhlErivajadusedArr value) {
        this.erivajadused = value;
    }

    /**
     * Gets the value of the muudAndmedMuutmine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlMuudAndmedMuutmine }
     *     
     */
    public KhlMuudAndmedMuutmine getMuudAndmedMuutmine() {
        return muudAndmedMuutmine;
    }

    /**
     * Sets the value of the muudAndmedMuutmine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlMuudAndmedMuutmine }
     *     
     */
    public void setMuudAndmedMuutmine(KhlMuudAndmedMuutmine value) {
        this.muudAndmedMuutmine = value;
    }

    /**
     * Gets the value of the ennistamine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlEnnistamine }
     *     
     */
    public KhlEnnistamine getEnnistamine() {
        return ennistamine;
    }

    /**
     * Sets the value of the ennistamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlEnnistamine }
     *     
     */
    public void setEnnistamine(KhlEnnistamine value) {
        this.ennistamine = value;
    }

    /**
     * Gets the value of the voorkeeled property.
     * 
     * @return
     *     possible object is
     *     {@link KhlVoorkeeledArr }
     *     
     */
    public KhlVoorkeeledArr getVoorkeeled() {
        return voorkeeled;
    }

    /**
     * Sets the value of the voorkeeled property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlVoorkeeledArr }
     *     
     */
    public void setVoorkeeled(KhlVoorkeeledArr value) {
        this.voorkeeled = value;
    }

    /**
     * Gets the value of the oppekavaTaitmine property.
     * 
     * @return
     *     possible object is
     *     {@link KhlOppekavaTaitmine }
     *     
     */
    public KhlOppekavaTaitmine getOppekavaTaitmine() {
        return oppekavaTaitmine;
    }

    /**
     * Sets the value of the oppekavaTaitmine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlOppekavaTaitmine }
     *     
     */
    public void setOppekavaTaitmine(KhlOppekavaTaitmine value) {
        this.oppekavaTaitmine = value;
    }

    /**
     * Gets the value of the lyhiajaliseltValismaal property.
     * 
     * @return
     *     possible object is
     *     {@link KhlLyhiajaliseltValismaal }
     *     
     */
    public KhlLyhiajaliseltValismaal getLyhiajaliseltValismaal() {
        return lyhiajaliseltValismaal;
    }

    /**
     * Sets the value of the lyhiajaliseltValismaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlLyhiajaliseltValismaal }
     *     
     */
    public void setLyhiajaliseltValismaal(KhlLyhiajaliseltValismaal value) {
        this.lyhiajaliseltValismaal = value;
    }

    /**
     * Gets the value of the votaKirjed property.
     * 
     * @return
     *     possible object is
     *     {@link KhlVOTAArr }
     *     
     */
    public KhlVOTAArr getVOTAKirjed() {
        return votaKirjed;
    }

    /**
     * Sets the value of the votaKirjed property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlVOTAArr }
     *     
     */
    public void setVOTAKirjed(KhlVOTAArr value) {
        this.votaKirjed = value;
    }

    /**
     * Gets the value of the stipendiumid property.
     * 
     * @return
     *     possible object is
     *     {@link KhlStipendiumArr }
     *     
     */
    public KhlStipendiumArr getStipendiumid() {
        return stipendiumid;
    }

    /**
     * Sets the value of the stipendiumid property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlStipendiumArr }
     *     
     */
    public void setStipendiumid(KhlStipendiumArr value) {
        this.stipendiumid = value;
    }

}
