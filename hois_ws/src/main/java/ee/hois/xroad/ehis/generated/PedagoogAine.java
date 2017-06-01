
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pedagoogAine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogAine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="klAine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *           &lt;element name="aineNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="klAste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;choice minOccurs="0"&gt;
 *           &lt;element name="klOppekava" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *           &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="tunde" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="norm" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="klKeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vastavusKval" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="tunnidErivajadus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogAine", propOrder = {
    "klAine",
    "aineNimetus",
    "klAste",
    "klOppekava",
    "oppekavaKood",
    "tunde",
    "norm",
    "klKeel",
    "vastavusKval",
    "tunnidErivajadus"
})
public class PedagoogAine {

    protected String klAine;
    protected String aineNimetus;
    protected String klAste;
    protected String klOppekava;
    protected List<BigInteger> oppekavaKood;
    protected double tunde;
    protected Double norm;
    @XmlElement(required = true)
    protected String klKeel;
    protected String vastavusKval;
    @XmlElement(required = true)
    protected String tunnidErivajadus;

    /**
     * Gets the value of the klAine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlAine() {
        return klAine;
    }

    /**
     * Sets the value of the klAine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlAine(String value) {
        this.klAine = value;
    }

    /**
     * Gets the value of the aineNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAineNimetus() {
        return aineNimetus;
    }

    /**
     * Sets the value of the aineNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAineNimetus(String value) {
        this.aineNimetus = value;
    }

    /**
     * Gets the value of the klAste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlAste() {
        return klAste;
    }

    /**
     * Sets the value of the klAste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlAste(String value) {
        this.klAste = value;
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
     * Gets the value of the oppekavaKood property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppekavaKood property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppekavaKood().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getOppekavaKood() {
        if (oppekavaKood == null) {
            oppekavaKood = new ArrayList<BigInteger>();
        }
        return this.oppekavaKood;
    }

    /**
     * Gets the value of the tunde property.
     * 
     */
    public double getTunde() {
        return tunde;
    }

    /**
     * Sets the value of the tunde property.
     * 
     */
    public void setTunde(double value) {
        this.tunde = value;
    }

    /**
     * Gets the value of the norm property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNorm() {
        return norm;
    }

    /**
     * Sets the value of the norm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNorm(Double value) {
        this.norm = value;
    }

    /**
     * Gets the value of the klKeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKeel() {
        return klKeel;
    }

    /**
     * Sets the value of the klKeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKeel(String value) {
        this.klKeel = value;
    }

    /**
     * Gets the value of the vastavusKval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastavusKval() {
        return vastavusKval;
    }

    /**
     * Sets the value of the vastavusKval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastavusKval(String value) {
        this.vastavusKval = value;
    }

    /**
     * Gets the value of the tunnidErivajadus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTunnidErivajadus() {
        return tunnidErivajadus;
    }

    /**
     * Sets the value of the tunnidErivajadus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTunnidErivajadus(String value) {
        this.tunnidErivajadus = value;
    }

}
