
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dokument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dokument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dokumentId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="klLiik" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="failiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kommentaar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dokument", propOrder = {
    "dokumentId",
    "klLiik",
    "failiNimi",
    "kommentaar",
    "content"
})
public class Dokument {

    @XmlElementRef(name = "dokumentId", type = JAXBElement.class, required = false)
    protected JAXBElement<Long> dokumentId;
    protected int klLiik;
    @XmlElement(required = true)
    protected String failiNimi;
    @XmlElementRef(name = "kommentaar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> kommentaar;
    @XmlElementRef(name = "content", type = JAXBElement.class, required = false)
    protected JAXBElement<byte[]> content;

    /**
     * Gets the value of the dokumentId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getDokumentId() {
        return dokumentId;
    }

    /**
     * Sets the value of the dokumentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setDokumentId(JAXBElement<Long> value) {
        this.dokumentId = value;
    }

    /**
     * Gets the value of the klLiik property.
     * 
     */
    public int getKlLiik() {
        return klLiik;
    }

    /**
     * Sets the value of the klLiik property.
     * 
     */
    public void setKlLiik(int value) {
        this.klLiik = value;
    }

    /**
     * Gets the value of the failiNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailiNimi() {
        return failiNimi;
    }

    /**
     * Sets the value of the failiNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailiNimi(String value) {
        this.failiNimi = value;
    }

    /**
     * Gets the value of the kommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKommentaar() {
        return kommentaar;
    }

    /**
     * Sets the value of the kommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKommentaar(JAXBElement<String> value) {
        this.kommentaar = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setContent(JAXBElement<byte[]> value) {
        this.content = value;
    }

}
