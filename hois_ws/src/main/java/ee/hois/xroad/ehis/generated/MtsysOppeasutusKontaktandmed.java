
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtsysOppeasutusKontaktandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtsysOppeasutusKontaktandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseYldtelefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseEpost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="koduleheAadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtsysOppeasutusKontaktandmed", propOrder = {
    "oppeasutuseYldtelefon",
    "oppeasutuseEpost",
    "koduleheAadress"
})
public class MtsysOppeasutusKontaktandmed {

    @XmlElementRef(name = "oppeasutuseYldtelefon", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oppeasutuseYldtelefon;
    @XmlElementRef(name = "oppeasutuseEpost", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oppeasutuseEpost;
    @XmlElementRef(name = "koduleheAadress", type = JAXBElement.class, required = false)
    protected JAXBElement<String> koduleheAadress;

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
     * Gets the value of the koduleheAadress property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKoduleheAadress() {
        return koduleheAadress;
    }

    /**
     * Sets the value of the koduleheAadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKoduleheAadress(JAXBElement<String> value) {
        this.koduleheAadress = value;
    }

}
