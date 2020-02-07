
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for korgkOppetoo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="korgkOppetoo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppimaKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eelnevHaridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kursus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="finantsAllikas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppelaenuoiguslikkus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eelmiseLopetamiseAasta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eelmiseOppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lopetamineValismaal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="filiaal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="riiklTellAasta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="yhiselamuKasut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kinnipidamisasutuses" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekohtId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="lahkumiseKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="onOppiv" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "korgkOppetoo", propOrder = {
    "oppeasutus",
    "oppimaKp",
    "eelnevHaridus",
    "kursus",
    "oppekava",
    "tase",
    "oppekeel",
    "oppevorm",
    "finantsAllikas",
    "oppelaenuoiguslikkus",
    "eelmiseLopetamiseAasta",
    "eelmiseOppevorm",
    "lopetamineValismaal",
    "filiaal",
    "riiklTellAasta",
    "yhiselamuKasut",
    "kinnipidamisasutuses",
    "muutusKp",
    "oppekohtId",
    "lahkumiseKp",
    "onOppiv"
})
public class KorgkOppetoo {

    @XmlElement(required = true, nillable = true)
    protected String oppeasutus;
    @XmlElement(required = true, nillable = true)
    protected String oppimaKp;
    @XmlElement(required = true, nillable = true)
    protected String eelnevHaridus;
    @XmlElement(required = true, nillable = true)
    protected String kursus;
    @XmlElement(required = true, nillable = true)
    protected String oppekava;
    @XmlElement(required = true, nillable = true)
    protected String tase;
    @XmlElement(required = true, nillable = true)
    protected String oppekeel;
    @XmlElement(required = true, nillable = true)
    protected String oppevorm;
    @XmlElement(required = true, nillable = true)
    protected String finantsAllikas;
    @XmlElement(required = true, nillable = true)
    protected String oppelaenuoiguslikkus;
    @XmlElement(required = true, nillable = true)
    protected String eelmiseLopetamiseAasta;
    @XmlElement(required = true, nillable = true)
    protected String eelmiseOppevorm;
    @XmlElement(required = true, nillable = true)
    protected String lopetamineValismaal;
    @XmlElement(required = true, nillable = true)
    protected String filiaal;
    @XmlElement(required = true, nillable = true)
    protected String riiklTellAasta;
    @XmlElement(required = true, nillable = true)
    protected String yhiselamuKasut;
    @XmlElement(required = true, nillable = true)
    protected String kinnipidamisasutuses;
    @XmlElement(required = true, nillable = true)
    protected String muutusKp;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long oppekohtId;
    @XmlElement(required = true, nillable = true)
    protected String lahkumiseKp;
    @XmlElement(required = true, nillable = true)
    protected String onOppiv;

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
     * Gets the value of the eelnevHaridus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelnevHaridus() {
        return eelnevHaridus;
    }

    /**
     * Sets the value of the eelnevHaridus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelnevHaridus(String value) {
        this.eelnevHaridus = value;
    }

    /**
     * Gets the value of the kursus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKursus() {
        return kursus;
    }

    /**
     * Sets the value of the kursus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKursus(String value) {
        this.kursus = value;
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
     * Gets the value of the tase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTase() {
        return tase;
    }

    /**
     * Sets the value of the tase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTase(String value) {
        this.tase = value;
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
     * Gets the value of the finantsAllikas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinantsAllikas() {
        return finantsAllikas;
    }

    /**
     * Sets the value of the finantsAllikas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinantsAllikas(String value) {
        this.finantsAllikas = value;
    }

    /**
     * Gets the value of the oppelaenuoiguslikkus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppelaenuoiguslikkus() {
        return oppelaenuoiguslikkus;
    }

    /**
     * Sets the value of the oppelaenuoiguslikkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppelaenuoiguslikkus(String value) {
        this.oppelaenuoiguslikkus = value;
    }

    /**
     * Gets the value of the eelmiseLopetamiseAasta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelmiseLopetamiseAasta() {
        return eelmiseLopetamiseAasta;
    }

    /**
     * Sets the value of the eelmiseLopetamiseAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelmiseLopetamiseAasta(String value) {
        this.eelmiseLopetamiseAasta = value;
    }

    /**
     * Gets the value of the eelmiseOppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelmiseOppevorm() {
        return eelmiseOppevorm;
    }

    /**
     * Sets the value of the eelmiseOppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelmiseOppevorm(String value) {
        this.eelmiseOppevorm = value;
    }

    /**
     * Gets the value of the lopetamineValismaal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLopetamineValismaal() {
        return lopetamineValismaal;
    }

    /**
     * Sets the value of the lopetamineValismaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLopetamineValismaal(String value) {
        this.lopetamineValismaal = value;
    }

    /**
     * Gets the value of the filiaal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiliaal() {
        return filiaal;
    }

    /**
     * Sets the value of the filiaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiliaal(String value) {
        this.filiaal = value;
    }

    /**
     * Gets the value of the riiklTellAasta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiiklTellAasta() {
        return riiklTellAasta;
    }

    /**
     * Sets the value of the riiklTellAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiiklTellAasta(String value) {
        this.riiklTellAasta = value;
    }

    /**
     * Gets the value of the yhiselamuKasut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYhiselamuKasut() {
        return yhiselamuKasut;
    }

    /**
     * Sets the value of the yhiselamuKasut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYhiselamuKasut(String value) {
        this.yhiselamuKasut = value;
    }

    /**
     * Gets the value of the kinnipidamisasutuses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKinnipidamisasutuses() {
        return kinnipidamisasutuses;
    }

    /**
     * Sets the value of the kinnipidamisasutuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKinnipidamisasutuses(String value) {
        this.kinnipidamisasutuses = value;
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

}
