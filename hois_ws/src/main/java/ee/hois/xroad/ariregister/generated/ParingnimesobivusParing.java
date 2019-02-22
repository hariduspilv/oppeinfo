
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringnimesobivus_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringnimesobivus_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tapsusklass" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="otsi_euroopa_kaubamarke" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
@XmlType(name = "paringnimesobivus_paring", propOrder = {
    "nimi",
    "tapsusklass",
    "otsiEuroopaKaubamarke",
    "keel"
})
public class ParingnimesobivusParing {

    @XmlElement(required = true)
    protected String nimi;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer tapsusklass;
    @XmlElementRef(name = "otsi_euroopa_kaubamarke", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> otsiEuroopaKaubamarke;
    @XmlElementRef(name = "keel", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> keel;

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
     * Gets the value of the tapsusklass property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTapsusklass() {
        return tapsusklass;
    }

    /**
     * Sets the value of the tapsusklass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTapsusklass(Integer value) {
        this.tapsusklass = value;
    }

    /**
     * Gets the value of the otsiEuroopaKaubamarke property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getOtsiEuroopaKaubamarke() {
        return otsiEuroopaKaubamarke;
    }

    /**
     * Sets the value of the otsiEuroopaKaubamarke property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setOtsiEuroopaKaubamarke(JAXBElement<Boolean> value) {
        this.otsiEuroopaKaubamarke = value;
    }

    /**
     * Gets the value of the keel property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKeel() {
        return keel;
    }

    /**
     * Sets the value of the keel property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKeel(JAXBElement<String> value) {
        this.keel = value;
    }

}
