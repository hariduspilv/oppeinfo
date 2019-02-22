
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for xbrlesita_osanik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlesita_osanik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koodiriik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="synnikp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="osa_suurus" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="osa_valuuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aadress_riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aadress_ehak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aadress_tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aadress_postalcode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlesita_osanik", propOrder = {
    "liik",
    "kood",
    "koodiriik",
    "synnikp",
    "eesnimi",
    "nimi",
    "osaSuurus",
    "osaValuuta",
    "aadressRiik",
    "aadressEhak",
    "aadressTekst",
    "aadressPostalcode"
})
public class XbrlesitaOsanik {

    @XmlElement(required = true)
    protected String liik;
    @XmlElement(required = true, nillable = true)
    protected String kood;
    @XmlElement(required = true, nillable = true)
    protected String koodiriik;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synnikp;
    @XmlElement(required = true, nillable = true)
    protected String eesnimi;
    @XmlElement(required = true)
    protected String nimi;
    @XmlElementRef(name = "osa_suurus", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> osaSuurus;
    @XmlElement(name = "osa_valuuta", required = true, nillable = true)
    protected String osaValuuta;
    @XmlElement(name = "aadress_riik", required = true, nillable = true)
    protected String aadressRiik;
    @XmlElement(name = "aadress_ehak", required = true, nillable = true)
    protected String aadressEhak;
    @XmlElement(name = "aadress_tekst", required = true, nillable = true)
    protected String aadressTekst;
    @XmlElement(name = "aadress_postalcode", required = true, nillable = true)
    protected String aadressPostalcode;

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
     * Gets the value of the kood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKood() {
        return kood;
    }

    /**
     * Sets the value of the kood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKood(String value) {
        this.kood = value;
    }

    /**
     * Gets the value of the koodiriik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoodiriik() {
        return koodiriik;
    }

    /**
     * Sets the value of the koodiriik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoodiriik(String value) {
        this.koodiriik = value;
    }

    /**
     * Gets the value of the synnikp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynnikp() {
        return synnikp;
    }

    /**
     * Sets the value of the synnikp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynnikp(XMLGregorianCalendar value) {
        this.synnikp = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

    /**
     * Gets the value of the osaSuurus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getOsaSuurus() {
        return osaSuurus;
    }

    /**
     * Sets the value of the osaSuurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setOsaSuurus(JAXBElement<BigDecimal> value) {
        this.osaSuurus = value;
    }

    /**
     * Gets the value of the osaValuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOsaValuuta() {
        return osaValuuta;
    }

    /**
     * Sets the value of the osaValuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOsaValuuta(String value) {
        this.osaValuuta = value;
    }

    /**
     * Gets the value of the aadressRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressRiik() {
        return aadressRiik;
    }

    /**
     * Sets the value of the aadressRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressRiik(String value) {
        this.aadressRiik = value;
    }

    /**
     * Gets the value of the aadressEhak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressEhak() {
        return aadressEhak;
    }

    /**
     * Sets the value of the aadressEhak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressEhak(String value) {
        this.aadressEhak = value;
    }

    /**
     * Gets the value of the aadressTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressTekst() {
        return aadressTekst;
    }

    /**
     * Sets the value of the aadressTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressTekst(String value) {
        this.aadressTekst = value;
    }

    /**
     * Gets the value of the aadressPostalcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadressPostalcode() {
        return aadressPostalcode;
    }

    /**
     * Sets the value of the aadressPostalcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadressPostalcode(String value) {
        this.aadressPostalcode = value;
    }

}
