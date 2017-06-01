
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtsysOppeasutusAndmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtsysOppeasutusAndmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="omanik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutuseNimetusIngliseKeeles" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klPidajaLiik" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="klOmandivorm" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="klOppeasutuseLiik" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtsysOppeasutusAndmed", propOrder = {
    "omanik",
    "oppeasutuseNimetus",
    "oppeasutuseNimetusIngliseKeeles",
    "klPidajaLiik",
    "klOmandivorm",
    "klOppeasutuseLiik"
})
public class MtsysOppeasutusAndmed {

    protected String omanik;
    @XmlElement(required = true)
    protected String oppeasutuseNimetus;
    protected String oppeasutuseNimetusIngliseKeeles;
    protected int klPidajaLiik;
    protected int klOmandivorm;
    protected int klOppeasutuseLiik;

    /**
     * Gets the value of the omanik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmanik() {
        return omanik;
    }

    /**
     * Sets the value of the omanik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmanik(String value) {
        this.omanik = value;
    }

    /**
     * Gets the value of the oppeasutuseNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseNimetus() {
        return oppeasutuseNimetus;
    }

    /**
     * Sets the value of the oppeasutuseNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseNimetus(String value) {
        this.oppeasutuseNimetus = value;
    }

    /**
     * Gets the value of the oppeasutuseNimetusIngliseKeeles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseNimetusIngliseKeeles() {
        return oppeasutuseNimetusIngliseKeeles;
    }

    /**
     * Sets the value of the oppeasutuseNimetusIngliseKeeles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseNimetusIngliseKeeles(String value) {
        this.oppeasutuseNimetusIngliseKeeles = value;
    }

    /**
     * Gets the value of the klPidajaLiik property.
     * 
     */
    public int getKlPidajaLiik() {
        return klPidajaLiik;
    }

    /**
     * Sets the value of the klPidajaLiik property.
     * 
     */
    public void setKlPidajaLiik(int value) {
        this.klPidajaLiik = value;
    }

    /**
     * Gets the value of the klOmandivorm property.
     * 
     */
    public int getKlOmandivorm() {
        return klOmandivorm;
    }

    /**
     * Sets the value of the klOmandivorm property.
     * 
     */
    public void setKlOmandivorm(int value) {
        this.klOmandivorm = value;
    }

    /**
     * Gets the value of the klOppeasutuseLiik property.
     * 
     */
    public int getKlOppeasutuseLiik() {
        return klOppeasutuseLiik;
    }

    /**
     * Sets the value of the klOppeasutuseLiik property.
     * 
     */
    public void setKlOppeasutuseLiik(int value) {
        this.klOppeasutuseLiik = value;
    }

}
