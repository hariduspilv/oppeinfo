
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yldandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yldandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="valjaandja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hoolealune" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orb" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yldandmed", propOrder = {
    "isikukood",
    "valjaandja",
    "synniaeg",
    "sugu",
    "eesnimi",
    "perekonnanimi",
    "kodakondsus",
    "emakeel",
    "hoolealune",
    "orb"
})
public class Yldandmed {

    @XmlElement(required = true, nillable = true)
    protected String isikukood;
    @XmlElement(required = true, nillable = true)
    protected String valjaandja;
    @XmlElement(required = true, nillable = true)
    protected String synniaeg;
    @XmlElement(required = true, nillable = true)
    protected String sugu;
    @XmlElement(required = true, nillable = true)
    protected String eesnimi;
    @XmlElement(required = true, nillable = true)
    protected String perekonnanimi;
    @XmlElement(required = true, nillable = true)
    protected String kodakondsus;
    @XmlElement(required = true, nillable = true)
    protected String emakeel;
    @XmlElement(required = true, nillable = true)
    protected String hoolealune;
    @XmlElement(required = true, nillable = true)
    protected String orb;

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
     * Gets the value of the valjaandja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValjaandja() {
        return valjaandja;
    }

    /**
     * Sets the value of the valjaandja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjaandja(String value) {
        this.valjaandja = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynniaeg(String value) {
        this.synniaeg = value;
    }

    /**
     * Gets the value of the sugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSugu() {
        return sugu;
    }

    /**
     * Sets the value of the sugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSugu(String value) {
        this.sugu = value;
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
     * Gets the value of the perekonnanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerekonnanimi() {
        return perekonnanimi;
    }

    /**
     * Sets the value of the perekonnanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerekonnanimi(String value) {
        this.perekonnanimi = value;
    }

    /**
     * Gets the value of the kodakondsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsus() {
        return kodakondsus;
    }

    /**
     * Sets the value of the kodakondsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsus(String value) {
        this.kodakondsus = value;
    }

    /**
     * Gets the value of the emakeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmakeel() {
        return emakeel;
    }

    /**
     * Sets the value of the emakeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmakeel(String value) {
        this.emakeel = value;
    }

    /**
     * Gets the value of the hoolealune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoolealune() {
        return hoolealune;
    }

    /**
     * Sets the value of the hoolealune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoolealune(String value) {
        this.hoolealune = value;
    }

    /**
     * Gets the value of the orb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrb() {
        return orb;
    }

    /**
     * Sets the value of the orb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrb(String value) {
        this.orb = value;
    }

}
