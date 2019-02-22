
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringesindus_v6_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringesindus_v6_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregister_kasutajanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_parool" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_sessioon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_valjundi_formaat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_koodi_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="keel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringesindus_v6_paring", propOrder = {
    "ariregisterKasutajanimi",
    "ariregisterParool",
    "ariregisterSessioon",
    "ariregisterValjundiFormaat",
    "ariregistriKood",
    "fyysiliseIsikuKood",
    "fyysiliseIsikuKoodiRiik",
    "fyysiliseIsikuEesnimi",
    "fyysiliseIsikuPerenimi",
    "keel"
})
public class ParingesindusV6Paring {

    @XmlElementRef(name = "ariregister_kasutajanimi", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterKasutajanimi;
    @XmlElementRef(name = "ariregister_parool", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterParool;
    @XmlElementRef(name = "ariregister_sessioon", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterSessioon;
    @XmlElementRef(name = "ariregister_valjundi_formaat", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterValjundiFormaat;
    @XmlElement(name = "ariregistri_kood")
    protected Integer ariregistriKood;
    @XmlElement(name = "fyysilise_isiku_kood")
    protected String fyysiliseIsikuKood;
    @XmlElement(name = "fyysilise_isiku_koodi_riik")
    protected String fyysiliseIsikuKoodiRiik;
    @XmlElement(name = "fyysilise_isiku_eesnimi")
    protected String fyysiliseIsikuEesnimi;
    @XmlElement(name = "fyysilise_isiku_perenimi")
    protected String fyysiliseIsikuPerenimi;
    protected String keel;

    /**
     * Gets the value of the ariregisterKasutajanimi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAriregisterKasutajanimi() {
        return ariregisterKasutajanimi;
    }

    /**
     * Sets the value of the ariregisterKasutajanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAriregisterKasutajanimi(JAXBElement<String> value) {
        this.ariregisterKasutajanimi = value;
    }

    /**
     * Gets the value of the ariregisterParool property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAriregisterParool() {
        return ariregisterParool;
    }

    /**
     * Sets the value of the ariregisterParool property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAriregisterParool(JAXBElement<String> value) {
        this.ariregisterParool = value;
    }

    /**
     * Gets the value of the ariregisterSessioon property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAriregisterSessioon() {
        return ariregisterSessioon;
    }

    /**
     * Sets the value of the ariregisterSessioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAriregisterSessioon(JAXBElement<String> value) {
        this.ariregisterSessioon = value;
    }

    /**
     * Gets the value of the ariregisterValjundiFormaat property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAriregisterValjundiFormaat() {
        return ariregisterValjundiFormaat;
    }

    /**
     * Sets the value of the ariregisterValjundiFormaat property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAriregisterValjundiFormaat(JAXBElement<String> value) {
        this.ariregisterValjundiFormaat = value;
    }

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAriregistriKood(Integer value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuKood() {
        return fyysiliseIsikuKood;
    }

    /**
     * Sets the value of the fyysiliseIsikuKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuKood(String value) {
        this.fyysiliseIsikuKood = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuKoodiRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuKoodiRiik() {
        return fyysiliseIsikuKoodiRiik;
    }

    /**
     * Sets the value of the fyysiliseIsikuKoodiRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuKoodiRiik(String value) {
        this.fyysiliseIsikuKoodiRiik = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuEesnimi() {
        return fyysiliseIsikuEesnimi;
    }

    /**
     * Sets the value of the fyysiliseIsikuEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuEesnimi(String value) {
        this.fyysiliseIsikuEesnimi = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuPerenimi() {
        return fyysiliseIsikuPerenimi;
    }

    /**
     * Sets the value of the fyysiliseIsikuPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuPerenimi(String value) {
        this.fyysiliseIsikuPerenimi = value;
    }

    /**
     * Gets the value of the keel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeel() {
        return keel;
    }

    /**
     * Sets the value of the keel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeel(String value) {
        this.keel = value;
    }

}
