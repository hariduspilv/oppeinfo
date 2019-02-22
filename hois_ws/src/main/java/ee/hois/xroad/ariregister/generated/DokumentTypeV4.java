
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dokumentType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dokumentType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/&gt;
 *         &lt;element name="edokumendi_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="digidoc_viit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="failinimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liik_ettevotjaportaal" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="liik_enotar" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dokumentType_v4", propOrder = {
    "id",
    "edokumendiId",
    "digidocViit",
    "failinimi",
    "liikEttevotjaportaal",
    "liikEnotar"
})
public class DokumentTypeV4 {

    @XmlElement(required = true, type = String.class)
    @XmlAttachmentRef
    @XmlSchemaType(name = "anyURI")
    protected DataHandler id;
    @XmlElement(name = "edokumendi_id")
    protected BigInteger edokumendiId;
    @XmlElement(name = "digidoc_viit")
    protected String digidocViit;
    @XmlElement(required = true)
    protected String failinimi;
    @XmlElement(name = "liik_ettevotjaportaal")
    protected Integer liikEttevotjaportaal;
    @XmlElement(name = "liik_enotar")
    protected Integer liikEnotar;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(DataHandler value) {
        this.id = value;
    }

    /**
     * Gets the value of the edokumendiId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEdokumendiId() {
        return edokumendiId;
    }

    /**
     * Sets the value of the edokumendiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEdokumendiId(BigInteger value) {
        this.edokumendiId = value;
    }

    /**
     * Gets the value of the digidocViit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigidocViit() {
        return digidocViit;
    }

    /**
     * Sets the value of the digidocViit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigidocViit(String value) {
        this.digidocViit = value;
    }

    /**
     * Gets the value of the failinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailinimi() {
        return failinimi;
    }

    /**
     * Sets the value of the failinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailinimi(String value) {
        this.failinimi = value;
    }

    /**
     * Gets the value of the liikEttevotjaportaal property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLiikEttevotjaportaal() {
        return liikEttevotjaportaal;
    }

    /**
     * Sets the value of the liikEttevotjaportaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLiikEttevotjaportaal(Integer value) {
        this.liikEttevotjaportaal = value;
    }

    /**
     * Gets the value of the liikEnotar property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLiikEnotar() {
        return liikEnotar;
    }

    /**
     * Sets the value of the liikEnotar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLiikEnotar(Integer value) {
        this.liikEnotar = value;
    }

}
