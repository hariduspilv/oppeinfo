
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xbrlesita_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlesita_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="yldandmed" type="{http://arireg.x-road.eu/producer/}xbrlesita_yldandmed"/&gt;
 *         &lt;element name="sidevahendid" type="{http://arireg.x-road.eu/producer/}xbrlesita_sidevahendid"/&gt;
 *         &lt;element name="osanikud" type="{http://arireg.x-road.eu/producer/}xbrlesita_osanikud"/&gt;
 *         &lt;element name="myygitulu" type="{http://arireg.x-road.eu/producer/}xbrlesita_myygitulu"/&gt;
 *         &lt;element name="manused" type="{http://arireg.x-road.eu/producer/}xbrlesita_dokumendid"/&gt;
 *         &lt;element name="audiitorid" type="{http://arireg.x-road.eu/producer/}xbrlesita_audiitorid" minOccurs="0"/&gt;
 *         &lt;element name="laiend_audiitorid" type="{http://arireg.x-road.eu/producer/}xbrlesita_laiend_audiitorid" minOccurs="0"/&gt;
 *         &lt;element name="xbrlinfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlesita_paring", propOrder = {
    "yldandmed",
    "sidevahendid",
    "osanikud",
    "myygitulu",
    "manused",
    "audiitorid",
    "laiendAudiitorid",
    "xbrlinfo"
})
public class XbrlesitaParing {

    @XmlElement(required = true)
    protected XbrlesitaYldandmed yldandmed;
    @XmlElement(required = true, nillable = true)
    protected XbrlesitaSidevahendid sidevahendid;
    @XmlElement(required = true, nillable = true)
    protected XbrlesitaOsanikud osanikud;
    @XmlElement(required = true, nillable = true)
    protected XbrlesitaMyygitulu myygitulu;
    @XmlElement(required = true)
    protected XbrlesitaDokumendid manused;
    @XmlElementRef(name = "audiitorid", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<XbrlesitaAudiitorid> audiitorid;
    @XmlElementRef(name = "laiend_audiitorid", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<XbrlesitaLaiendAudiitorid> laiendAudiitorid;
    @XmlElement(required = true, nillable = true)
    protected String xbrlinfo;

    /**
     * Gets the value of the yldandmed property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlesitaYldandmed }
     *     
     */
    public XbrlesitaYldandmed getYldandmed() {
        return yldandmed;
    }

    /**
     * Sets the value of the yldandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlesitaYldandmed }
     *     
     */
    public void setYldandmed(XbrlesitaYldandmed value) {
        this.yldandmed = value;
    }

    /**
     * Gets the value of the sidevahendid property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlesitaSidevahendid }
     *     
     */
    public XbrlesitaSidevahendid getSidevahendid() {
        return sidevahendid;
    }

    /**
     * Sets the value of the sidevahendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlesitaSidevahendid }
     *     
     */
    public void setSidevahendid(XbrlesitaSidevahendid value) {
        this.sidevahendid = value;
    }

    /**
     * Gets the value of the osanikud property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlesitaOsanikud }
     *     
     */
    public XbrlesitaOsanikud getOsanikud() {
        return osanikud;
    }

    /**
     * Sets the value of the osanikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlesitaOsanikud }
     *     
     */
    public void setOsanikud(XbrlesitaOsanikud value) {
        this.osanikud = value;
    }

    /**
     * Gets the value of the myygitulu property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlesitaMyygitulu }
     *     
     */
    public XbrlesitaMyygitulu getMyygitulu() {
        return myygitulu;
    }

    /**
     * Sets the value of the myygitulu property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlesitaMyygitulu }
     *     
     */
    public void setMyygitulu(XbrlesitaMyygitulu value) {
        this.myygitulu = value;
    }

    /**
     * Gets the value of the manused property.
     * 
     * @return
     *     possible object is
     *     {@link XbrlesitaDokumendid }
     *     
     */
    public XbrlesitaDokumendid getManused() {
        return manused;
    }

    /**
     * Sets the value of the manused property.
     * 
     * @param value
     *     allowed object is
     *     {@link XbrlesitaDokumendid }
     *     
     */
    public void setManused(XbrlesitaDokumendid value) {
        this.manused = value;
    }

    /**
     * Gets the value of the audiitorid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XbrlesitaAudiitorid }{@code >}
     *     
     */
    public JAXBElement<XbrlesitaAudiitorid> getAudiitorid() {
        return audiitorid;
    }

    /**
     * Sets the value of the audiitorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XbrlesitaAudiitorid }{@code >}
     *     
     */
    public void setAudiitorid(JAXBElement<XbrlesitaAudiitorid> value) {
        this.audiitorid = value;
    }

    /**
     * Gets the value of the laiendAudiitorid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XbrlesitaLaiendAudiitorid }{@code >}
     *     
     */
    public JAXBElement<XbrlesitaLaiendAudiitorid> getLaiendAudiitorid() {
        return laiendAudiitorid;
    }

    /**
     * Sets the value of the laiendAudiitorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XbrlesitaLaiendAudiitorid }{@code >}
     *     
     */
    public void setLaiendAudiitorid(JAXBElement<XbrlesitaLaiendAudiitorid> value) {
        this.laiendAudiitorid = value;
    }

    /**
     * Gets the value of the xbrlinfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXbrlinfo() {
        return xbrlinfo;
    }

    /**
     * Sets the value of the xbrlinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXbrlinfo(String value) {
        this.xbrlinfo = value;
    }

}
