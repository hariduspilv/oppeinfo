
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppejoudKvalifikatsioon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppejoudKvalifikatsioon"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klKvalifikatsioon" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klKvalifikatsioonNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kvalifikatsioonNimetusMuu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aasta" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="klEestiOppeasutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klEestiOppeasutusEndine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutusMuu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppejoudKvalifikatsioon", propOrder = {
    "klKvalifikatsioon",
    "klKvalifikatsioonNimetus",
    "kvalifikatsioonNimetusMuu",
    "klRiik",
    "aasta",
    "klEestiOppeasutus",
    "klEestiOppeasutusEndine",
    "oppeasutusMuu"
})
public class OppejoudKvalifikatsioon {

    @XmlElement(required = true)
    protected String klKvalifikatsioon;
    @XmlElement(required = true)
    protected String klKvalifikatsioonNimetus;
    protected String kvalifikatsioonNimetusMuu;
    @XmlElement(required = true)
    protected String klRiik;
    @XmlElement(required = true)
    protected BigInteger aasta;
    protected String klEestiOppeasutus;
    protected String klEestiOppeasutusEndine;
    protected String oppeasutusMuu;

    /**
     * Gets the value of the klKvalifikatsioon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKvalifikatsioon() {
        return klKvalifikatsioon;
    }

    /**
     * Sets the value of the klKvalifikatsioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKvalifikatsioon(String value) {
        this.klKvalifikatsioon = value;
    }

    /**
     * Gets the value of the klKvalifikatsioonNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKvalifikatsioonNimetus() {
        return klKvalifikatsioonNimetus;
    }

    /**
     * Sets the value of the klKvalifikatsioonNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKvalifikatsioonNimetus(String value) {
        this.klKvalifikatsioonNimetus = value;
    }

    /**
     * Gets the value of the kvalifikatsioonNimetusMuu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKvalifikatsioonNimetusMuu() {
        return kvalifikatsioonNimetusMuu;
    }

    /**
     * Sets the value of the kvalifikatsioonNimetusMuu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKvalifikatsioonNimetusMuu(String value) {
        this.kvalifikatsioonNimetusMuu = value;
    }

    /**
     * Gets the value of the klRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlRiik() {
        return klRiik;
    }

    /**
     * Sets the value of the klRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlRiik(String value) {
        this.klRiik = value;
    }

    /**
     * Gets the value of the aasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAasta() {
        return aasta;
    }

    /**
     * Sets the value of the aasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAasta(BigInteger value) {
        this.aasta = value;
    }

    /**
     * Gets the value of the klEestiOppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEestiOppeasutus() {
        return klEestiOppeasutus;
    }

    /**
     * Sets the value of the klEestiOppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEestiOppeasutus(String value) {
        this.klEestiOppeasutus = value;
    }

    /**
     * Gets the value of the klEestiOppeasutusEndine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEestiOppeasutusEndine() {
        return klEestiOppeasutusEndine;
    }

    /**
     * Sets the value of the klEestiOppeasutusEndine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEestiOppeasutusEndine(String value) {
        this.klEestiOppeasutusEndine = value;
    }

    /**
     * Gets the value of the oppeasutusMuu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusMuu() {
        return oppeasutusMuu;
    }

    /**
     * Sets the value of the oppeasutusMuu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusMuu(String value) {
        this.oppeasutusMuu = value;
    }

}
