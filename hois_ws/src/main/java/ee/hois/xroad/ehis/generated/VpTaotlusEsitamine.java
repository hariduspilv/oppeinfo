
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="taotlejaIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="taotluseId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="kontoOmanikuNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kontonumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="epost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="telefoniNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="toetuseMitteSaamiseKinnitus" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="failiOigsuseKinnitus" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "taotlejaIsikukood",
    "taotluseId",
    "kontoOmanikuNimi",
    "kontonumber",
    "epost",
    "telefoniNumber",
    "toetuseMitteSaamiseKinnitus",
    "failiOigsuseKinnitus"
})
@XmlRootElement(name = "vpTaotlusEsitamine")
public class VpTaotlusEsitamine {

    @XmlElement(required = true)
    protected String taotlejaIsikukood;
    protected long taotluseId;
    @XmlElement(required = true)
    protected String kontoOmanikuNimi;
    @XmlElement(required = true)
    protected String kontonumber;
    @XmlElement(required = true)
    protected String epost;
    @XmlElementRef(name = "telefoniNumber", type = JAXBElement.class, required = false)
    protected JAXBElement<String> telefoniNumber;
    protected boolean toetuseMitteSaamiseKinnitus;
    protected boolean failiOigsuseKinnitus;

    /**
     * Gets the value of the taotlejaIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaotlejaIsikukood() {
        return taotlejaIsikukood;
    }

    /**
     * Sets the value of the taotlejaIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaotlejaIsikukood(String value) {
        this.taotlejaIsikukood = value;
    }

    /**
     * Gets the value of the taotluseId property.
     * 
     */
    public long getTaotluseId() {
        return taotluseId;
    }

    /**
     * Sets the value of the taotluseId property.
     * 
     */
    public void setTaotluseId(long value) {
        this.taotluseId = value;
    }

    /**
     * Gets the value of the kontoOmanikuNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKontoOmanikuNimi() {
        return kontoOmanikuNimi;
    }

    /**
     * Sets the value of the kontoOmanikuNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKontoOmanikuNimi(String value) {
        this.kontoOmanikuNimi = value;
    }

    /**
     * Gets the value of the kontonumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKontonumber() {
        return kontonumber;
    }

    /**
     * Sets the value of the kontonumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKontonumber(String value) {
        this.kontonumber = value;
    }

    /**
     * Gets the value of the epost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEpost() {
        return epost;
    }

    /**
     * Sets the value of the epost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEpost(String value) {
        this.epost = value;
    }

    /**
     * Gets the value of the telefoniNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTelefoniNumber() {
        return telefoniNumber;
    }

    /**
     * Sets the value of the telefoniNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTelefoniNumber(JAXBElement<String> value) {
        this.telefoniNumber = value;
    }

    /**
     * Gets the value of the toetuseMitteSaamiseKinnitus property.
     * 
     */
    public boolean isToetuseMitteSaamiseKinnitus() {
        return toetuseMitteSaamiseKinnitus;
    }

    /**
     * Sets the value of the toetuseMitteSaamiseKinnitus property.
     * 
     */
    public void setToetuseMitteSaamiseKinnitus(boolean value) {
        this.toetuseMitteSaamiseKinnitus = value;
    }

    /**
     * Gets the value of the failiOigsuseKinnitus property.
     * 
     */
    public boolean isFailiOigsuseKinnitus() {
        return failiOigsuseKinnitus;
    }

    /**
     * Sets the value of the failiOigsuseKinnitus property.
     * 
     */
    public void setFailiOigsuseKinnitus(boolean value) {
        this.failiOigsuseKinnitus = value;
    }

}