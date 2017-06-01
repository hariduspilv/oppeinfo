
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="regNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekava" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekava"/&gt;
 *         &lt;element name="taotlus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}taotlus"/&gt;
 *         &lt;element name="kontaktandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}mtsysTaotlusKontaktandmed"/&gt;
 *         &lt;element name="dokumendid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}dokumendid" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "regNr",
    "oppekava",
    "taotlus",
    "kontaktandmed",
    "dokumendid"
})
@XmlRootElement(name = "mtsysLaeTegevusluba")
public class MtsysLaeTegevusluba {

    @XmlElement(required = true)
    protected String regNr;
    @XmlElement(required = true)
    protected Oppekava oppekava;
    @XmlElement(required = true)
    protected Taotlus taotlus;
    @XmlElement(required = true)
    protected MtsysTaotlusKontaktandmed kontaktandmed;
    protected Dokumendid dokumendid;

    /**
     * Gets the value of the regNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegNr() {
        return regNr;
    }

    /**
     * Sets the value of the regNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegNr(String value) {
        this.regNr = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link Oppekava }
     *     
     */
    public Oppekava getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link Oppekava }
     *     
     */
    public void setOppekava(Oppekava value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the taotlus property.
     * 
     * @return
     *     possible object is
     *     {@link Taotlus }
     *     
     */
    public Taotlus getTaotlus() {
        return taotlus;
    }

    /**
     * Sets the value of the taotlus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Taotlus }
     *     
     */
    public void setTaotlus(Taotlus value) {
        this.taotlus = value;
    }

    /**
     * Gets the value of the kontaktandmed property.
     * 
     * @return
     *     possible object is
     *     {@link MtsysTaotlusKontaktandmed }
     *     
     */
    public MtsysTaotlusKontaktandmed getKontaktandmed() {
        return kontaktandmed;
    }

    /**
     * Sets the value of the kontaktandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtsysTaotlusKontaktandmed }
     *     
     */
    public void setKontaktandmed(MtsysTaotlusKontaktandmed value) {
        this.kontaktandmed = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link Dokumendid }
     *     
     */
    public Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dokumendid }
     *     
     */
    public void setDokumendid(Dokumendid value) {
        this.dokumendid = value;
    }

}
