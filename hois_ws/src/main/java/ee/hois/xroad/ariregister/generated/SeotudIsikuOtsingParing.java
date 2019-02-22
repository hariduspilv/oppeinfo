
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for seotud_isiku_otsing_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="seotud_isiku_otsing_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregister_kasutajanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_parool" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_sessioon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_valjundi_formaat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isik_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isik_perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isik_isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isik_synniaeg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jurisik_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jurisik_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="seose_kehtivus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "seotud_isiku_otsing_paring", propOrder = {
    "ariregisterKasutajanimi",
    "ariregisterParool",
    "ariregisterSessioon",
    "ariregisterValjundiFormaat",
    "isikEesnimi",
    "isikPerenimi",
    "isikIsikukood",
    "isikSynniaeg",
    "jurisikNimi",
    "jurisikKood",
    "seoseKehtivus"
})
public class SeotudIsikuOtsingParing {

    @XmlElementRef(name = "ariregister_kasutajanimi", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterKasutajanimi;
    @XmlElementRef(name = "ariregister_parool", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterParool;
    @XmlElementRef(name = "ariregister_sessioon", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterSessioon;
    @XmlElementRef(name = "ariregister_valjundi_formaat", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterValjundiFormaat;
    @XmlElement(name = "isik_eesnimi")
    protected String isikEesnimi;
    @XmlElement(name = "isik_perenimi")
    protected String isikPerenimi;
    @XmlElement(name = "isik_isikukood")
    protected String isikIsikukood;
    @XmlElement(name = "isik_synniaeg")
    protected String isikSynniaeg;
    @XmlElement(name = "jurisik_nimi")
    protected String jurisikNimi;
    @XmlElement(name = "jurisik_kood")
    protected String jurisikKood;
    @XmlElement(name = "seose_kehtivus")
    protected String seoseKehtivus;

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
     * Gets the value of the isikEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikEesnimi() {
        return isikEesnimi;
    }

    /**
     * Sets the value of the isikEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikEesnimi(String value) {
        this.isikEesnimi = value;
    }

    /**
     * Gets the value of the isikPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikPerenimi() {
        return isikPerenimi;
    }

    /**
     * Sets the value of the isikPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikPerenimi(String value) {
        this.isikPerenimi = value;
    }

    /**
     * Gets the value of the isikIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikIsikukood() {
        return isikIsikukood;
    }

    /**
     * Sets the value of the isikIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikIsikukood(String value) {
        this.isikIsikukood = value;
    }

    /**
     * Gets the value of the isikSynniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikSynniaeg() {
        return isikSynniaeg;
    }

    /**
     * Sets the value of the isikSynniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikSynniaeg(String value) {
        this.isikSynniaeg = value;
    }

    /**
     * Gets the value of the jurisikNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurisikNimi() {
        return jurisikNimi;
    }

    /**
     * Sets the value of the jurisikNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurisikNimi(String value) {
        this.jurisikNimi = value;
    }

    /**
     * Gets the value of the jurisikKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurisikKood() {
        return jurisikKood;
    }

    /**
     * Sets the value of the jurisikKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurisikKood(String value) {
        this.jurisikKood = value;
    }

    /**
     * Gets the value of the seoseKehtivus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeoseKehtivus() {
        return seoseKehtivus;
    }

    /**
     * Sets the value of the seoseKehtivus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeoseKehtivus(String value) {
        this.seoseKehtivus = value;
    }

}
