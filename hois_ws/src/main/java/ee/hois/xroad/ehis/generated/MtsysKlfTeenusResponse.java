
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="tegevusloaLiigid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tegevusloaLiigid" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaStaatused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekavaStaatused" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaOppetasemed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekavaOppetasemed" minOccurs="0"/&gt;
 *         &lt;element name="soidukiKategooriad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}soidukiKategooriad" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseOmandivormid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppeasutuseOmandivormid" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseLiigid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppeasutuseLiigid" minOccurs="0"/&gt;
 *         &lt;element name="pidajaLiigid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pidajaLiigid" minOccurs="0"/&gt;
 *         &lt;element name="failiTyybid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}failiTyybid" minOccurs="0"/&gt;
 *         &lt;element name="tkkLiigid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkkLiigid" minOccurs="0"/&gt;
 *         &lt;element name="eestiKeeleTasemed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eestiKeeleTasemed" minOccurs="0"/&gt;
 *         &lt;element name="opperyhmad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}opperyhmad" minOccurs="0"/&gt;
 *         &lt;element name="tegevusnaitajaTyybid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tegevusnaitajaTyybid" minOccurs="0"/&gt;
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
    "tegevusloaLiigid",
    "oppekavaStaatused",
    "oppekavaOppetasemed",
    "soidukiKategooriad",
    "oppeasutuseOmandivormid",
    "oppeasutuseLiigid",
    "pidajaLiigid",
    "failiTyybid",
    "tkkLiigid",
    "eestiKeeleTasemed",
    "opperyhmad",
    "tegevusnaitajaTyybid"
})
@XmlRootElement(name = "mtsysKlfTeenusResponse")
public class MtsysKlfTeenusResponse {

    @XmlElementRef(name = "tegevusloaLiigid", type = JAXBElement.class, required = false)
    protected JAXBElement<TegevusloaLiigid> tegevusloaLiigid;
    @XmlElementRef(name = "oppekavaStaatused", type = JAXBElement.class, required = false)
    protected JAXBElement<OppekavaStaatused> oppekavaStaatused;
    @XmlElementRef(name = "oppekavaOppetasemed", type = JAXBElement.class, required = false)
    protected JAXBElement<OppekavaOppetasemed> oppekavaOppetasemed;
    @XmlElementRef(name = "soidukiKategooriad", type = JAXBElement.class, required = false)
    protected JAXBElement<SoidukiKategooriad> soidukiKategooriad;
    @XmlElementRef(name = "oppeasutuseOmandivormid", type = JAXBElement.class, required = false)
    protected JAXBElement<OppeasutuseOmandivormid> oppeasutuseOmandivormid;
    @XmlElementRef(name = "oppeasutuseLiigid", type = JAXBElement.class, required = false)
    protected JAXBElement<OppeasutuseLiigid> oppeasutuseLiigid;
    @XmlElementRef(name = "pidajaLiigid", type = JAXBElement.class, required = false)
    protected JAXBElement<PidajaLiigid> pidajaLiigid;
    @XmlElementRef(name = "failiTyybid", type = JAXBElement.class, required = false)
    protected JAXBElement<FailiTyybid> failiTyybid;
    @XmlElementRef(name = "tkkLiigid", type = JAXBElement.class, required = false)
    protected JAXBElement<TkkLiigid> tkkLiigid;
    @XmlElementRef(name = "eestiKeeleTasemed", type = JAXBElement.class, required = false)
    protected JAXBElement<EestiKeeleTasemed> eestiKeeleTasemed;
    @XmlElementRef(name = "opperyhmad", type = JAXBElement.class, required = false)
    protected JAXBElement<Opperyhmad> opperyhmad;
    @XmlElementRef(name = "tegevusnaitajaTyybid", type = JAXBElement.class, required = false)
    protected JAXBElement<TegevusnaitajaTyybid> tegevusnaitajaTyybid;

    /**
     * Gets the value of the tegevusloaLiigid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TegevusloaLiigid }{@code >}
     *     
     */
    public JAXBElement<TegevusloaLiigid> getTegevusloaLiigid() {
        return tegevusloaLiigid;
    }

    /**
     * Sets the value of the tegevusloaLiigid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TegevusloaLiigid }{@code >}
     *     
     */
    public void setTegevusloaLiigid(JAXBElement<TegevusloaLiigid> value) {
        this.tegevusloaLiigid = value;
    }

    /**
     * Gets the value of the oppekavaStaatused property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link OppekavaStaatused }{@code >}
     *     
     */
    public JAXBElement<OppekavaStaatused> getOppekavaStaatused() {
        return oppekavaStaatused;
    }

    /**
     * Sets the value of the oppekavaStaatused property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link OppekavaStaatused }{@code >}
     *     
     */
    public void setOppekavaStaatused(JAXBElement<OppekavaStaatused> value) {
        this.oppekavaStaatused = value;
    }

    /**
     * Gets the value of the oppekavaOppetasemed property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link OppekavaOppetasemed }{@code >}
     *     
     */
    public JAXBElement<OppekavaOppetasemed> getOppekavaOppetasemed() {
        return oppekavaOppetasemed;
    }

    /**
     * Sets the value of the oppekavaOppetasemed property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link OppekavaOppetasemed }{@code >}
     *     
     */
    public void setOppekavaOppetasemed(JAXBElement<OppekavaOppetasemed> value) {
        this.oppekavaOppetasemed = value;
    }

    /**
     * Gets the value of the soidukiKategooriad property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SoidukiKategooriad }{@code >}
     *     
     */
    public JAXBElement<SoidukiKategooriad> getSoidukiKategooriad() {
        return soidukiKategooriad;
    }

    /**
     * Sets the value of the soidukiKategooriad property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SoidukiKategooriad }{@code >}
     *     
     */
    public void setSoidukiKategooriad(JAXBElement<SoidukiKategooriad> value) {
        this.soidukiKategooriad = value;
    }

    /**
     * Gets the value of the oppeasutuseOmandivormid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link OppeasutuseOmandivormid }{@code >}
     *     
     */
    public JAXBElement<OppeasutuseOmandivormid> getOppeasutuseOmandivormid() {
        return oppeasutuseOmandivormid;
    }

    /**
     * Sets the value of the oppeasutuseOmandivormid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link OppeasutuseOmandivormid }{@code >}
     *     
     */
    public void setOppeasutuseOmandivormid(JAXBElement<OppeasutuseOmandivormid> value) {
        this.oppeasutuseOmandivormid = value;
    }

    /**
     * Gets the value of the oppeasutuseLiigid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link OppeasutuseLiigid }{@code >}
     *     
     */
    public JAXBElement<OppeasutuseLiigid> getOppeasutuseLiigid() {
        return oppeasutuseLiigid;
    }

    /**
     * Sets the value of the oppeasutuseLiigid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link OppeasutuseLiigid }{@code >}
     *     
     */
    public void setOppeasutuseLiigid(JAXBElement<OppeasutuseLiigid> value) {
        this.oppeasutuseLiigid = value;
    }

    /**
     * Gets the value of the pidajaLiigid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PidajaLiigid }{@code >}
     *     
     */
    public JAXBElement<PidajaLiigid> getPidajaLiigid() {
        return pidajaLiigid;
    }

    /**
     * Sets the value of the pidajaLiigid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PidajaLiigid }{@code >}
     *     
     */
    public void setPidajaLiigid(JAXBElement<PidajaLiigid> value) {
        this.pidajaLiigid = value;
    }

    /**
     * Gets the value of the failiTyybid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link FailiTyybid }{@code >}
     *     
     */
    public JAXBElement<FailiTyybid> getFailiTyybid() {
        return failiTyybid;
    }

    /**
     * Sets the value of the failiTyybid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link FailiTyybid }{@code >}
     *     
     */
    public void setFailiTyybid(JAXBElement<FailiTyybid> value) {
        this.failiTyybid = value;
    }

    /**
     * Gets the value of the tkkLiigid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TkkLiigid }{@code >}
     *     
     */
    public JAXBElement<TkkLiigid> getTkkLiigid() {
        return tkkLiigid;
    }

    /**
     * Sets the value of the tkkLiigid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TkkLiigid }{@code >}
     *     
     */
    public void setTkkLiigid(JAXBElement<TkkLiigid> value) {
        this.tkkLiigid = value;
    }

    /**
     * Gets the value of the eestiKeeleTasemed property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EestiKeeleTasemed }{@code >}
     *     
     */
    public JAXBElement<EestiKeeleTasemed> getEestiKeeleTasemed() {
        return eestiKeeleTasemed;
    }

    /**
     * Sets the value of the eestiKeeleTasemed property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EestiKeeleTasemed }{@code >}
     *     
     */
    public void setEestiKeeleTasemed(JAXBElement<EestiKeeleTasemed> value) {
        this.eestiKeeleTasemed = value;
    }

    /**
     * Gets the value of the opperyhmad property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Opperyhmad }{@code >}
     *     
     */
    public JAXBElement<Opperyhmad> getOpperyhmad() {
        return opperyhmad;
    }

    /**
     * Sets the value of the opperyhmad property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Opperyhmad }{@code >}
     *     
     */
    public void setOpperyhmad(JAXBElement<Opperyhmad> value) {
        this.opperyhmad = value;
    }

    /**
     * Gets the value of the tegevusnaitajaTyybid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TegevusnaitajaTyybid }{@code >}
     *     
     */
    public JAXBElement<TegevusnaitajaTyybid> getTegevusnaitajaTyybid() {
        return tegevusnaitajaTyybid;
    }

    /**
     * Sets the value of the tegevusnaitajaTyybid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TegevusnaitajaTyybid }{@code >}
     *     
     */
    public void setTegevusnaitajaTyybid(JAXBElement<TegevusnaitajaTyybid> value) {
        this.tegevusnaitajaTyybid = value;
    }

}
