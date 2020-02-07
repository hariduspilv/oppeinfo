
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppurV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppurV2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ruhmaLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kohasuurus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ruhmaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paralleeliTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavatase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppimaAsumiseKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutuseOmandivorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekoormus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ehak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppurV2", propOrder = {
    "isikukood",
    "eesnimi",
    "perenimi",
    "haridus",
    "ruhmaLiik",
    "kohasuurus",
    "ruhmaNimi",
    "klass",
    "paralleeliTunnus",
    "oppekavatase",
    "oppevorm",
    "oppimaAsumiseKp",
    "oppeasutusKood",
    "oppeasutusNimetus",
    "oppeasutuseOmandivorm",
    "oppekoormus",
    "ehak"
})
public class OppurV2 {

    @XmlElement(required = true)
    protected String isikukood;
    @XmlElement(required = true)
    protected String eesnimi;
    @XmlElement(required = true)
    protected String perenimi;
    @XmlElement(required = true)
    protected String haridus;
    @XmlElement(required = true, nillable = true)
    protected String ruhmaLiik;
    @XmlElement(required = true, nillable = true)
    protected String kohasuurus;
    @XmlElement(required = true, nillable = true)
    protected String ruhmaNimi;
    @XmlElement(required = true, nillable = true)
    protected String klass;
    @XmlElement(required = true, nillable = true)
    protected String paralleeliTunnus;
    @XmlElement(required = true, nillable = true)
    protected String oppekavatase;
    @XmlElement(required = true, nillable = true)
    protected String oppevorm;
    @XmlElement(required = true, nillable = true)
    protected String oppimaAsumiseKp;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutusKood;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutusNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseOmandivorm;
    @XmlElement(required = true, nillable = true)
    protected String oppekoormus;
    @XmlElement(required = true, nillable = true)
    protected String ehak;

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
     * Gets the value of the haridus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaridus() {
        return haridus;
    }

    /**
     * Sets the value of the haridus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaridus(String value) {
        this.haridus = value;
    }

    /**
     * Gets the value of the ruhmaLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuhmaLiik() {
        return ruhmaLiik;
    }

    /**
     * Sets the value of the ruhmaLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuhmaLiik(String value) {
        this.ruhmaLiik = value;
    }

    /**
     * Gets the value of the kohasuurus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKohasuurus() {
        return kohasuurus;
    }

    /**
     * Sets the value of the kohasuurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKohasuurus(String value) {
        this.kohasuurus = value;
    }

    /**
     * Gets the value of the ruhmaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuhmaNimi() {
        return ruhmaNimi;
    }

    /**
     * Sets the value of the ruhmaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuhmaNimi(String value) {
        this.ruhmaNimi = value;
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
     * Gets the value of the oppekavatase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavatase() {
        return oppekavatase;
    }

    /**
     * Sets the value of the oppekavatase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavatase(String value) {
        this.oppekavatase = value;
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
     * Gets the value of the oppimaAsumiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppimaAsumiseKp() {
        return oppimaAsumiseKp;
    }

    /**
     * Sets the value of the oppimaAsumiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppimaAsumiseKp(String value) {
        this.oppimaAsumiseKp = value;
    }

    /**
     * Gets the value of the oppeasutusKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusKood() {
        return oppeasutusKood;
    }

    /**
     * Sets the value of the oppeasutusKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusKood(String value) {
        this.oppeasutusKood = value;
    }

    /**
     * Gets the value of the oppeasutusNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusNimetus() {
        return oppeasutusNimetus;
    }

    /**
     * Sets the value of the oppeasutusNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusNimetus(String value) {
        this.oppeasutusNimetus = value;
    }

    /**
     * Gets the value of the oppeasutuseOmandivorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseOmandivorm() {
        return oppeasutuseOmandivorm;
    }

    /**
     * Sets the value of the oppeasutuseOmandivorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseOmandivorm(String value) {
        this.oppeasutuseOmandivorm = value;
    }

    /**
     * Gets the value of the oppekoormus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekoormus() {
        return oppekoormus;
    }

    /**
     * Sets the value of the oppekoormus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekoormus(String value) {
        this.oppekoormus = value;
    }

    /**
     * Gets the value of the ehak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEhak() {
        return ehak;
    }

    /**
     * Sets the value of the ehak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEhak(String value) {
        this.ehak = value;
    }

}
