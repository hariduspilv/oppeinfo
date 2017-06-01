
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtsysTaotlusKontaktandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtsysTaotlusKontaktandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kooliNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omanik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kontaktisik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseYldtelefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseEpost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="koduleht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtsysTaotlusKontaktandmed", propOrder = {
    "kooliNimetus",
    "omanik",
    "kontaktisik",
    "oppeasutuseYldtelefon",
    "oppeasutuseEpost",
    "koduleht"
})
public class MtsysTaotlusKontaktandmed {

    protected String kooliNimetus;
    protected String omanik;
    @XmlElementRef(name = "kontaktisik", type = JAXBElement.class, required = false)
    protected JAXBElement<String> kontaktisik;
    @XmlElementRef(name = "oppeasutuseYldtelefon", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oppeasutuseYldtelefon;
    @XmlElementRef(name = "oppeasutuseEpost", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oppeasutuseEpost;
    @XmlElementRef(name = "koduleht", type = JAXBElement.class, required = false)
    protected JAXBElement<String> koduleht;

    /**
     * Gets the value of the kooliNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliNimetus() {
        return kooliNimetus;
    }

    /**
     * Sets the value of the kooliNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliNimetus(String value) {
        this.kooliNimetus = value;
    }

    /**
     * Gets the value of the omanik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmanik() {
        return omanik;
    }

    /**
     * Sets the value of the omanik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmanik(String value) {
        this.omanik = value;
    }

    /**
     * Gets the value of the kontaktisik property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKontaktisik() {
        return kontaktisik;
    }

    /**
     * Sets the value of the kontaktisik property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKontaktisik(JAXBElement<String> value) {
        this.kontaktisik = value;
    }

    /**
     * Gets the value of the oppeasutuseYldtelefon property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOppeasutuseYldtelefon() {
        return oppeasutuseYldtelefon;
    }

    /**
     * Sets the value of the oppeasutuseYldtelefon property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOppeasutuseYldtelefon(JAXBElement<String> value) {
        this.oppeasutuseYldtelefon = value;
    }

    /**
     * Gets the value of the oppeasutuseEpost property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOppeasutuseEpost() {
        return oppeasutuseEpost;
    }

    /**
     * Sets the value of the oppeasutuseEpost property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOppeasutuseEpost(JAXBElement<String> value) {
        this.oppeasutuseEpost = value;
    }

    /**
     * Gets the value of the koduleht property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKoduleht() {
        return koduleht;
    }

    /**
     * Sets the value of the koduleht property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKoduleht(JAXBElement<String> value) {
        this.koduleht = value;
    }

}
