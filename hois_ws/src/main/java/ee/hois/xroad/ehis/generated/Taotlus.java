
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for taotlus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="taotlus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="laagriNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kohtadeArvLaagris" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kehtibAlates" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kehtibKuni" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="klTkkLiik" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klEkTase" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klSoidukiKategooria" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppetasemed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekavaOppetasemed" minOccurs="0"/&gt;
 *         &lt;element name="opperyhmad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}opperyhmad" minOccurs="0"/&gt;
 *         &lt;element name="aadressid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}aadressid" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "taotlus", propOrder = {
    "id",
    "nimetus",
    "laagriNimetus",
    "kohtadeArvLaagris",
    "kehtibAlates",
    "kehtibKuni",
    "klTkkLiik",
    "klEkTase",
    "klSoidukiKategooria",
    "lisainfo",
    "oppetasemed",
    "opperyhmad",
    "aadressid"
})
public class Taotlus {

    protected BigInteger id;
    protected String nimetus;
    protected String laagriNimetus;
    @XmlElementRef(name = "kohtadeArvLaagris", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> kohtadeArvLaagris;
    @XmlElementRef(name = "kehtibAlates", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> kehtibAlates;
    @XmlElementRef(name = "kehtibKuni", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> kehtibKuni;
    @XmlElementRef(name = "klTkkLiik", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> klTkkLiik;
    @XmlElementRef(name = "klEkTase", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> klEkTase;
    @XmlElementRef(name = "klSoidukiKategooria", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> klSoidukiKategooria;
    protected String lisainfo;
    protected OppekavaOppetasemed oppetasemed;
    protected Opperyhmad opperyhmad;
    protected Aadressid aadressid;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the laagriNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaagriNimetus() {
        return laagriNimetus;
    }

    /**
     * Sets the value of the laagriNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaagriNimetus(String value) {
        this.laagriNimetus = value;
    }

    /**
     * Gets the value of the kohtadeArvLaagris property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getKohtadeArvLaagris() {
        return kohtadeArvLaagris;
    }

    /**
     * Sets the value of the kohtadeArvLaagris property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setKohtadeArvLaagris(JAXBElement<BigInteger> value) {
        this.kohtadeArvLaagris = value;
    }

    /**
     * Gets the value of the kehtibAlates property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getKehtibAlates() {
        return kehtibAlates;
    }

    /**
     * Sets the value of the kehtibAlates property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setKehtibAlates(JAXBElement<XMLGregorianCalendar> value) {
        this.kehtibAlates = value;
    }

    /**
     * Gets the value of the kehtibKuni property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getKehtibKuni() {
        return kehtibKuni;
    }

    /**
     * Sets the value of the kehtibKuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setKehtibKuni(JAXBElement<XMLGregorianCalendar> value) {
        this.kehtibKuni = value;
    }

    /**
     * Gets the value of the klTkkLiik property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getKlTkkLiik() {
        return klTkkLiik;
    }

    /**
     * Sets the value of the klTkkLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setKlTkkLiik(JAXBElement<BigInteger> value) {
        this.klTkkLiik = value;
    }

    /**
     * Gets the value of the klEkTase property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getKlEkTase() {
        return klEkTase;
    }

    /**
     * Sets the value of the klEkTase property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setKlEkTase(JAXBElement<BigInteger> value) {
        this.klEkTase = value;
    }

    /**
     * Gets the value of the klSoidukiKategooria property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getKlSoidukiKategooria() {
        return klSoidukiKategooria;
    }

    /**
     * Sets the value of the klSoidukiKategooria property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setKlSoidukiKategooria(JAXBElement<BigInteger> value) {
        this.klSoidukiKategooria = value;
    }

    /**
     * Gets the value of the lisainfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo() {
        return lisainfo;
    }

    /**
     * Sets the value of the lisainfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo(String value) {
        this.lisainfo = value;
    }

    /**
     * Gets the value of the oppetasemed property.
     * 
     * @return
     *     possible object is
     *     {@link OppekavaOppetasemed }
     *     
     */
    public OppekavaOppetasemed getOppetasemed() {
        return oppetasemed;
    }

    /**
     * Sets the value of the oppetasemed property.
     * 
     * @param value
     *     allowed object is
     *     {@link OppekavaOppetasemed }
     *     
     */
    public void setOppetasemed(OppekavaOppetasemed value) {
        this.oppetasemed = value;
    }

    /**
     * Gets the value of the opperyhmad property.
     * 
     * @return
     *     possible object is
     *     {@link Opperyhmad }
     *     
     */
    public Opperyhmad getOpperyhmad() {
        return opperyhmad;
    }

    /**
     * Sets the value of the opperyhmad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Opperyhmad }
     *     
     */
    public void setOpperyhmad(Opperyhmad value) {
        this.opperyhmad = value;
    }

    /**
     * Gets the value of the aadressid property.
     * 
     * @return
     *     possible object is
     *     {@link Aadressid }
     *     
     */
    public Aadressid getAadressid() {
        return aadressid;
    }

    /**
     * Sets the value of the aadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Aadressid }
     *     
     */
    public void setAadressid(Aadressid value) {
        this.aadressid = value;
    }

}
