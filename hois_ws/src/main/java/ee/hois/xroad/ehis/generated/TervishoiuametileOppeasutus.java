
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tervishoiuametileOppeasutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tervishoiuametileOppeasutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ehisKood" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimetusInglise" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asutamiseAasta" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="pidajaLiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omandivorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oasLiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oasAlamliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="keeled" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tervishoiuametileKeeled" minOccurs="0"/&gt;
 *         &lt;element name="kontakt" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}kontakt"/&gt;
 *         &lt;element name="jurKontakt" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}juriidiline"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tervishoiuametileOppeasutus", propOrder = {
    "ehisKood",
    "registrikood",
    "nimetus",
    "nimetusInglise",
    "asutamiseAasta",
    "pidajaLiik",
    "omandivorm",
    "oasLiik",
    "oasAlamliik",
    "keeled",
    "kontakt",
    "jurKontakt"
})
public class TervishoiuametileOppeasutus {

    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger ehisKood;
    protected String registrikood;
    protected String nimetus;
    protected String nimetusInglise;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger asutamiseAasta;
    protected String pidajaLiik;
    protected String omandivorm;
    protected String oasLiik;
    protected String oasAlamliik;
    protected TervishoiuametileKeeled keeled;
    @XmlElement(required = true)
    protected Kontakt kontakt;
    @XmlElement(required = true)
    protected Juriidiline jurKontakt;

    /**
     * Gets the value of the ehisKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEhisKood() {
        return ehisKood;
    }

    /**
     * Sets the value of the ehisKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEhisKood(BigInteger value) {
        this.ehisKood = value;
    }

    /**
     * Gets the value of the registrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrikood() {
        return registrikood;
    }

    /**
     * Sets the value of the registrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrikood(String value) {
        this.registrikood = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the nimetusInglise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetusInglise() {
        return nimetusInglise;
    }

    /**
     * Sets the value of the nimetusInglise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetusInglise(String value) {
        this.nimetusInglise = value;
    }

    /**
     * Gets the value of the asutamiseAasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAsutamiseAasta() {
        return asutamiseAasta;
    }

    /**
     * Sets the value of the asutamiseAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAsutamiseAasta(BigInteger value) {
        this.asutamiseAasta = value;
    }

    /**
     * Gets the value of the pidajaLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaLiik() {
        return pidajaLiik;
    }

    /**
     * Sets the value of the pidajaLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaLiik(String value) {
        this.pidajaLiik = value;
    }

    /**
     * Gets the value of the omandivorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandivorm() {
        return omandivorm;
    }

    /**
     * Sets the value of the omandivorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandivorm(String value) {
        this.omandivorm = value;
    }

    /**
     * Gets the value of the oasLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasLiik() {
        return oasLiik;
    }

    /**
     * Sets the value of the oasLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasLiik(String value) {
        this.oasLiik = value;
    }

    /**
     * Gets the value of the oasAlamliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasAlamliik() {
        return oasAlamliik;
    }

    /**
     * Sets the value of the oasAlamliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasAlamliik(String value) {
        this.oasAlamliik = value;
    }

    /**
     * Gets the value of the keeled property.
     * 
     * @return
     *     possible object is
     *     {@link TervishoiuametileKeeled }
     *     
     */
    public TervishoiuametileKeeled getKeeled() {
        return keeled;
    }

    /**
     * Sets the value of the keeled property.
     * 
     * @param value
     *     allowed object is
     *     {@link TervishoiuametileKeeled }
     *     
     */
    public void setKeeled(TervishoiuametileKeeled value) {
        this.keeled = value;
    }

    /**
     * Gets the value of the kontakt property.
     * 
     * @return
     *     possible object is
     *     {@link Kontakt }
     *     
     */
    public Kontakt getKontakt() {
        return kontakt;
    }

    /**
     * Sets the value of the kontakt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Kontakt }
     *     
     */
    public void setKontakt(Kontakt value) {
        this.kontakt = value;
    }

    /**
     * Gets the value of the jurKontakt property.
     * 
     * @return
     *     possible object is
     *     {@link Juriidiline }
     *     
     */
    public Juriidiline getJurKontakt() {
        return jurKontakt;
    }

    /**
     * Sets the value of the jurKontakt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Juriidiline }
     *     
     */
    public void setJurKontakt(Juriidiline value) {
        this.jurKontakt = value;
    }

}
