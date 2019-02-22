
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for toimiku_dokument_v1_Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="toimiku_dokument_v1_Request"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregister_kasutajanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_parool" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="dokumendi_tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "toimiku_dokument_v1_Request", propOrder = {
    "ariregisterKasutajanimi",
    "ariregisterParool",
    "dokumendiId",
    "dokumendiTyyp"
})
public class ToimikuDokumentV1Request {

    @XmlElementRef(name = "ariregister_kasutajanimi", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterKasutajanimi;
    @XmlElementRef(name = "ariregister_parool", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterParool;
    @XmlElement(name = "dokumendi_id", required = true)
    protected BigInteger dokumendiId;
    @XmlElement(name = "dokumendi_tyyp", required = true)
    protected String dokumendiTyyp;

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
     * Gets the value of the dokumendiId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDokumendiId() {
        return dokumendiId;
    }

    /**
     * Sets the value of the dokumendiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDokumendiId(BigInteger value) {
        this.dokumendiId = value;
    }

    /**
     * Gets the value of the dokumendiTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiTyyp() {
        return dokumendiTyyp;
    }

    /**
     * Sets the value of the dokumendiTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiTyyp(String value) {
        this.dokumendiTyyp = value;
    }

}
