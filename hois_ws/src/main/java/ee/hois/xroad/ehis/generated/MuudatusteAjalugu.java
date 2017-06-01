
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for muudatusteAjalugu complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="muudatusteAjalugu"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tegija" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "muudatusteAjalugu", propOrder = {
    "nr",
    "aeg",
    "tegija",
    "liik",
    "oppeasutus"
})
public class MuudatusteAjalugu {

    @XmlElement(required = true, nillable = true)
    protected String nr;
    @XmlElement(required = true, nillable = true)
    protected String aeg;
    @XmlElement(required = true, nillable = true)
    protected String tegija;
    @XmlElement(required = true, nillable = true)
    protected String liik;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutus;

    /**
     * Gets the value of the nr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNr() {
        return nr;
    }

    /**
     * Sets the value of the nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNr(String value) {
        this.nr = value;
    }

    /**
     * Gets the value of the aeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAeg() {
        return aeg;
    }

    /**
     * Sets the value of the aeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAeg(String value) {
        this.aeg = value;
    }

    /**
     * Gets the value of the tegija property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegija() {
        return tegija;
    }

    /**
     * Sets the value of the tegija property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegija(String value) {
        this.tegija = value;
    }

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiik(String value) {
        this.liik = value;
    }

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

}
