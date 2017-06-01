
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tunnistus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tunnistus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppur" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="lopetas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tunnistuseNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsusAeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kohustuslikudHinded" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tunnistusHindedKohustuslik"/&gt;
 *         &lt;element name="valikHinded" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tunnistusHindedValik"/&gt;
 *         &lt;element name="eksamiHinded" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tunnistusHindedEksam"/&gt;
 *         &lt;element name="kaitumine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tunnistus", propOrder = {
    "oppur",
    "isikukood",
    "lopetas",
    "tunnistuseNr",
    "otsusAeg",
    "oppekava",
    "kohustuslikudHinded",
    "valikHinded",
    "eksamiHinded",
    "kaitumine"
})
public class Tunnistus {

    @XmlElement(required = true, nillable = true)
    protected String oppur;
    @XmlElement(required = true, nillable = true)
    protected String isikukood;
    @XmlElement(required = true, nillable = true)
    protected String lopetas;
    @XmlElement(required = true, nillable = true)
    protected String tunnistuseNr;
    @XmlElement(required = true, nillable = true)
    protected String otsusAeg;
    @XmlElement(required = true, nillable = true)
    protected String oppekava;
    @XmlElement(required = true)
    protected TunnistusHindedKohustuslik kohustuslikudHinded;
    @XmlElement(required = true)
    protected TunnistusHindedValik valikHinded;
    @XmlElement(required = true)
    protected TunnistusHindedEksam eksamiHinded;
    @XmlElement(required = true, nillable = true)
    protected String kaitumine;

    /**
     * Gets the value of the oppur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppur() {
        return oppur;
    }

    /**
     * Sets the value of the oppur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppur(String value) {
        this.oppur = value;
    }

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
     * Gets the value of the lopetas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLopetas() {
        return lopetas;
    }

    /**
     * Sets the value of the lopetas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLopetas(String value) {
        this.lopetas = value;
    }

    /**
     * Gets the value of the tunnistuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTunnistuseNr() {
        return tunnistuseNr;
    }

    /**
     * Sets the value of the tunnistuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTunnistuseNr(String value) {
        this.tunnistuseNr = value;
    }

    /**
     * Gets the value of the otsusAeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsusAeg() {
        return otsusAeg;
    }

    /**
     * Sets the value of the otsusAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsusAeg(String value) {
        this.otsusAeg = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekava(String value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the kohustuslikudHinded property.
     * 
     * @return
     *     possible object is
     *     {@link TunnistusHindedKohustuslik }
     *     
     */
    public TunnistusHindedKohustuslik getKohustuslikudHinded() {
        return kohustuslikudHinded;
    }

    /**
     * Sets the value of the kohustuslikudHinded property.
     * 
     * @param value
     *     allowed object is
     *     {@link TunnistusHindedKohustuslik }
     *     
     */
    public void setKohustuslikudHinded(TunnistusHindedKohustuslik value) {
        this.kohustuslikudHinded = value;
    }

    /**
     * Gets the value of the valikHinded property.
     * 
     * @return
     *     possible object is
     *     {@link TunnistusHindedValik }
     *     
     */
    public TunnistusHindedValik getValikHinded() {
        return valikHinded;
    }

    /**
     * Sets the value of the valikHinded property.
     * 
     * @param value
     *     allowed object is
     *     {@link TunnistusHindedValik }
     *     
     */
    public void setValikHinded(TunnistusHindedValik value) {
        this.valikHinded = value;
    }

    /**
     * Gets the value of the eksamiHinded property.
     * 
     * @return
     *     possible object is
     *     {@link TunnistusHindedEksam }
     *     
     */
    public TunnistusHindedEksam getEksamiHinded() {
        return eksamiHinded;
    }

    /**
     * Sets the value of the eksamiHinded property.
     * 
     * @param value
     *     allowed object is
     *     {@link TunnistusHindedEksam }
     *     
     */
    public void setEksamiHinded(TunnistusHindedEksam value) {
        this.eksamiHinded = value;
    }

    /**
     * Gets the value of the kaitumine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKaitumine() {
        return kaitumine;
    }

    /**
     * Sets the value of the kaitumine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKaitumine(String value) {
        this.kaitumine = value;
    }

}
