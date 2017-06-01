
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for oppimine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppimine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pohiLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="gymLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="intensiivsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppimaAsumiseKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="muutKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="muutKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tekkKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="registriTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeaasta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppimine", propOrder = {
    "oppeasutuseKood",
    "oppeasutuseNimi",
    "nomA",
    "pohiLopp",
    "gymLopp",
    "klass",
    "oppevorm",
    "intensiivsus",
    "oppimaAsumiseKuupaev",
    "muutKuupaev",
    "muutKood",
    "tekkKuupaev",
    "registriTunnus",
    "oppeaasta",
    "tase"
})
public class Oppimine {

    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseKood;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseNimi;
    @XmlElement(required = true, nillable = true)
    protected String nomA;
    @XmlElement(required = true, nillable = true)
    protected String pohiLopp;
    @XmlElement(required = true, nillable = true)
    protected String gymLopp;
    @XmlElement(required = true, nillable = true)
    protected String klass;
    @XmlElement(required = true, nillable = true)
    protected String oppevorm;
    @XmlElement(required = true, nillable = true)
    protected String intensiivsus;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppimaAsumiseKuupaev;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutKuupaev;
    @XmlElement(required = true, nillable = true)
    protected String muutKood;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tekkKuupaev;
    @XmlElement(required = true, nillable = true)
    protected String registriTunnus;
    @XmlElement(required = true, nillable = true)
    protected String oppeaasta;
    @XmlElement(required = true, nillable = true)
    protected String tase;

    /**
     * Gets the value of the oppeasutuseKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseKood() {
        return oppeasutuseKood;
    }

    /**
     * Sets the value of the oppeasutuseKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseKood(String value) {
        this.oppeasutuseKood = value;
    }

    /**
     * Gets the value of the oppeasutuseNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseNimi() {
        return oppeasutuseNimi;
    }

    /**
     * Sets the value of the oppeasutuseNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseNimi(String value) {
        this.oppeasutuseNimi = value;
    }

    /**
     * Gets the value of the nomA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomA() {
        return nomA;
    }

    /**
     * Sets the value of the nomA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomA(String value) {
        this.nomA = value;
    }

    /**
     * Gets the value of the pohiLopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohiLopp() {
        return pohiLopp;
    }

    /**
     * Sets the value of the pohiLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohiLopp(String value) {
        this.pohiLopp = value;
    }

    /**
     * Gets the value of the gymLopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGymLopp() {
        return gymLopp;
    }

    /**
     * Sets the value of the gymLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGymLopp(String value) {
        this.gymLopp = value;
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
     * Gets the value of the intensiivsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntensiivsus() {
        return intensiivsus;
    }

    /**
     * Sets the value of the intensiivsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntensiivsus(String value) {
        this.intensiivsus = value;
    }

    /**
     * Gets the value of the oppimaAsumiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppimaAsumiseKuupaev() {
        return oppimaAsumiseKuupaev;
    }

    /**
     * Sets the value of the oppimaAsumiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppimaAsumiseKuupaev(XMLGregorianCalendar value) {
        this.oppimaAsumiseKuupaev = value;
    }

    /**
     * Gets the value of the muutKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutKuupaev() {
        return muutKuupaev;
    }

    /**
     * Sets the value of the muutKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutKuupaev(XMLGregorianCalendar value) {
        this.muutKuupaev = value;
    }

    /**
     * Gets the value of the muutKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuutKood() {
        return muutKood;
    }

    /**
     * Sets the value of the muutKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuutKood(String value) {
        this.muutKood = value;
    }

    /**
     * Gets the value of the tekkKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTekkKuupaev() {
        return tekkKuupaev;
    }

    /**
     * Sets the value of the tekkKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTekkKuupaev(XMLGregorianCalendar value) {
        this.tekkKuupaev = value;
    }

    /**
     * Gets the value of the registriTunnus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistriTunnus() {
        return registriTunnus;
    }

    /**
     * Sets the value of the registriTunnus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistriTunnus(String value) {
        this.registriTunnus = value;
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

}
