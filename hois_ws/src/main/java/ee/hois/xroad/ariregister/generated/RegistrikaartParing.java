
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registrikaart_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registrikaart_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregister_kasutajanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_parool" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="liik" type="{http://arireg.x-road.eu/producer/}x_regkaardi_liik" minOccurs="0"/&gt;
 *         &lt;element name="kehtivus" type="{http://arireg.x-road.eu/producer/}x_regkaardi_kehtivus" minOccurs="0"/&gt;
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
@XmlType(name = "registrikaart_paring", propOrder = {
    "ariregisterKasutajanimi",
    "ariregisterParool",
    "ariregistriKood",
    "liik",
    "kehtivus",
    "keel"
})
public class RegistrikaartParing {

    @XmlElementRef(name = "ariregister_kasutajanimi", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterKasutajanimi;
    @XmlElementRef(name = "ariregister_parool", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterParool;
    @XmlElement(name = "ariregistri_kood")
    protected int ariregistriKood;
    @XmlSchemaType(name = "string")
    protected XRegkaardiLiik liik;
    @XmlSchemaType(name = "string")
    protected XRegkaardiKehtivus kehtivus;
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
     * Gets the value of the ariregistriKood property.
     * 
     */
    public int getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     */
    public void setAriregistriKood(int value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link XRegkaardiLiik }
     *     
     */
    public XRegkaardiLiik getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link XRegkaardiLiik }
     *     
     */
    public void setLiik(XRegkaardiLiik value) {
        this.liik = value;
    }

    /**
     * Gets the value of the kehtivus property.
     * 
     * @return
     *     possible object is
     *     {@link XRegkaardiKehtivus }
     *     
     */
    public XRegkaardiKehtivus getKehtivus() {
        return kehtivus;
    }

    /**
     * Sets the value of the kehtivus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XRegkaardiKehtivus }
     *     
     */
    public void setKehtivus(XRegkaardiKehtivus value) {
        this.kehtivus = value;
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
