
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR65HMIsikResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR65HMIsikResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikupnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikuenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SurmaKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KodakondsusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KodakondsusNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EmakeelKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EmakeelNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ElamislubaOn" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ElamislubaTahtaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR65HMIsikResponseType", propOrder = {
    "isikukood",
    "isikupnimi",
    "isikuenimi",
    "surmaKuup",
    "kodakondsusKood",
    "kodakondsusNimetus",
    "emakeelKood",
    "emakeelNimetus",
    "elamislubaOn",
    "elamislubaTahtaeg",
    "veakood",
    "veatekst"
})
public class RR65HMIsikResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Isikupnimi", required = true)
    protected String isikupnimi;
    @XmlElement(name = "Isikuenimi", required = true)
    protected String isikuenimi;
    @XmlElement(name = "SurmaKuup", required = true)
    protected String surmaKuup;
    @XmlElement(name = "KodakondsusKood", required = true)
    protected String kodakondsusKood;
    @XmlElement(name = "KodakondsusNimetus", required = true)
    protected String kodakondsusNimetus;
    @XmlElement(name = "EmakeelKood", required = true)
    protected String emakeelKood;
    @XmlElement(name = "EmakeelNimetus", required = true)
    protected String emakeelNimetus;
    @XmlElement(name = "ElamislubaOn", required = true)
    protected BigInteger elamislubaOn;
    @XmlElement(name = "ElamislubaTahtaeg", required = true)
    protected String elamislubaTahtaeg;
    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;

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
     * Gets the value of the isikupnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikupnimi() {
        return isikupnimi;
    }

    /**
     * Sets the value of the isikupnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikupnimi(String value) {
        this.isikupnimi = value;
    }

    /**
     * Gets the value of the isikuenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuenimi() {
        return isikuenimi;
    }

    /**
     * Sets the value of the isikuenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuenimi(String value) {
        this.isikuenimi = value;
    }

    /**
     * Gets the value of the surmaKuup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurmaKuup() {
        return surmaKuup;
    }

    /**
     * Sets the value of the surmaKuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurmaKuup(String value) {
        this.surmaKuup = value;
    }

    /**
     * Gets the value of the kodakondsusKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsusKood() {
        return kodakondsusKood;
    }

    /**
     * Sets the value of the kodakondsusKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsusKood(String value) {
        this.kodakondsusKood = value;
    }

    /**
     * Gets the value of the kodakondsusNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsusNimetus() {
        return kodakondsusNimetus;
    }

    /**
     * Sets the value of the kodakondsusNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsusNimetus(String value) {
        this.kodakondsusNimetus = value;
    }

    /**
     * Gets the value of the emakeelKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmakeelKood() {
        return emakeelKood;
    }

    /**
     * Sets the value of the emakeelKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmakeelKood(String value) {
        this.emakeelKood = value;
    }

    /**
     * Gets the value of the emakeelNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmakeelNimetus() {
        return emakeelNimetus;
    }

    /**
     * Sets the value of the emakeelNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmakeelNimetus(String value) {
        this.emakeelNimetus = value;
    }

    /**
     * Gets the value of the elamislubaOn property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getElamislubaOn() {
        return elamislubaOn;
    }

    /**
     * Sets the value of the elamislubaOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setElamislubaOn(BigInteger value) {
        this.elamislubaOn = value;
    }

    /**
     * Gets the value of the elamislubaTahtaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElamislubaTahtaeg() {
        return elamislubaTahtaeg;
    }

    /**
     * Sets the value of the elamislubaTahtaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElamislubaTahtaeg(String value) {
        this.elamislubaTahtaeg = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

}
