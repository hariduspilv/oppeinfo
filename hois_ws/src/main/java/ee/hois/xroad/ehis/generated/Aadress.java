
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aadress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aadress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="jrkNr" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="adsId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="adsOid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klElukoht" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="maakond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omavalitsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="taisAadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="adsAadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="adsAadressHumanReadable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aadress", propOrder = {
    "jrkNr",
    "adsId",
    "adsOid",
    "klElukoht",
    "maakond",
    "omavalitsus",
    "asula",
    "taisAadress",
    "adsAadress",
    "adsAadressHumanReadable"
})
public class Aadress {

    protected Long jrkNr;
    @XmlElementRef(name = "adsId", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> adsId;
    @XmlElementRef(name = "adsOid", type = JAXBElement.class, required = false)
    protected JAXBElement<String> adsOid;
    @XmlElementRef(name = "klElukoht", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> klElukoht;
    @XmlElementRef(name = "maakond", type = JAXBElement.class, required = false)
    protected JAXBElement<String> maakond;
    @XmlElementRef(name = "omavalitsus", type = JAXBElement.class, required = false)
    protected JAXBElement<String> omavalitsus;
    @XmlElementRef(name = "asula", type = JAXBElement.class, required = false)
    protected JAXBElement<String> asula;
    @XmlElementRef(name = "taisAadress", type = JAXBElement.class, required = false)
    protected JAXBElement<String> taisAadress;
    @XmlElementRef(name = "adsAadress", type = JAXBElement.class, required = false)
    protected JAXBElement<String> adsAadress;
    @XmlElementRef(name = "adsAadressHumanReadable", type = JAXBElement.class, required = false)
    protected JAXBElement<String> adsAadressHumanReadable;

    /**
     * Gets the value of the jrkNr property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getJrkNr() {
        return jrkNr;
    }

    /**
     * Sets the value of the jrkNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setJrkNr(Long value) {
        this.jrkNr = value;
    }

    /**
     * Gets the value of the adsId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getAdsId() {
        return adsId;
    }

    /**
     * Sets the value of the adsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setAdsId(JAXBElement<BigInteger> value) {
        this.adsId = value;
    }

    /**
     * Gets the value of the adsOid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAdsOid() {
        return adsOid;
    }

    /**
     * Sets the value of the adsOid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAdsOid(JAXBElement<String> value) {
        this.adsOid = value;
    }

    /**
     * Gets the value of the klElukoht property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getKlElukoht() {
        return klElukoht;
    }

    /**
     * Sets the value of the klElukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setKlElukoht(JAXBElement<BigInteger> value) {
        this.klElukoht = value;
    }

    /**
     * Gets the value of the maakond property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMaakond() {
        return maakond;
    }

    /**
     * Sets the value of the maakond property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMaakond(JAXBElement<String> value) {
        this.maakond = value;
    }

    /**
     * Gets the value of the omavalitsus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOmavalitsus() {
        return omavalitsus;
    }

    /**
     * Sets the value of the omavalitsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOmavalitsus(JAXBElement<String> value) {
        this.omavalitsus = value;
    }

    /**
     * Gets the value of the asula property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAsula() {
        return asula;
    }

    /**
     * Sets the value of the asula property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAsula(JAXBElement<String> value) {
        this.asula = value;
    }

    /**
     * Gets the value of the taisAadress property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTaisAadress() {
        return taisAadress;
    }

    /**
     * Sets the value of the taisAadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTaisAadress(JAXBElement<String> value) {
        this.taisAadress = value;
    }

    /**
     * Gets the value of the adsAadress property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAdsAadress() {
        return adsAadress;
    }

    /**
     * Sets the value of the adsAadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAdsAadress(JAXBElement<String> value) {
        this.adsAadress = value;
    }

    /**
     * Gets the value of the adsAadressHumanReadable property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAdsAadressHumanReadable() {
        return adsAadressHumanReadable;
    }

    /**
     * Sets the value of the adsAadressHumanReadable property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAdsAadressHumanReadable(JAXBElement<String> value) {
        this.adsAadressHumanReadable = value;
    }

}
