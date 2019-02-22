
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_muudatus_kandeelement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus_kandeelement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kandeelemendi_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kandeelemendi_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kandeelemendi_jrk" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kandeelemendi_kehtivus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tyhistatava_kande_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tyhistatava_kande_nr" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatus_kandeelement", propOrder = {
    "kandeelemendiId",
    "kandeelemendiLiik",
    "kandeelemendiJrk",
    "kandeelemendiKehtivus",
    "tyhistatavaKandeId",
    "tyhistatavaKandeNr"
})
public class EttevotjaMuudatusKandeelement {

    @XmlElement(name = "kandeelemendi_id")
    protected Integer kandeelemendiId;
    @XmlElement(name = "kandeelemendi_liik")
    protected String kandeelemendiLiik;
    @XmlElement(name = "kandeelemendi_jrk")
    protected Integer kandeelemendiJrk;
    @XmlElement(name = "kandeelemendi_kehtivus")
    protected String kandeelemendiKehtivus;
    @XmlElement(name = "tyhistatava_kande_id")
    protected String tyhistatavaKandeId;
    @XmlElement(name = "tyhistatava_kande_nr")
    protected Integer tyhistatavaKandeNr;

    /**
     * Gets the value of the kandeelemendiId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandeelemendiId() {
        return kandeelemendiId;
    }

    /**
     * Sets the value of the kandeelemendiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandeelemendiId(Integer value) {
        this.kandeelemendiId = value;
    }

    /**
     * Gets the value of the kandeelemendiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeelemendiLiik() {
        return kandeelemendiLiik;
    }

    /**
     * Sets the value of the kandeelemendiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeelemendiLiik(String value) {
        this.kandeelemendiLiik = value;
    }

    /**
     * Gets the value of the kandeelemendiJrk property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandeelemendiJrk() {
        return kandeelemendiJrk;
    }

    /**
     * Sets the value of the kandeelemendiJrk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandeelemendiJrk(Integer value) {
        this.kandeelemendiJrk = value;
    }

    /**
     * Gets the value of the kandeelemendiKehtivus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeelemendiKehtivus() {
        return kandeelemendiKehtivus;
    }

    /**
     * Sets the value of the kandeelemendiKehtivus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeelemendiKehtivus(String value) {
        this.kandeelemendiKehtivus = value;
    }

    /**
     * Gets the value of the tyhistatavaKandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyhistatavaKandeId() {
        return tyhistatavaKandeId;
    }

    /**
     * Sets the value of the tyhistatavaKandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyhistatavaKandeId(String value) {
        this.tyhistatavaKandeId = value;
    }

    /**
     * Gets the value of the tyhistatavaKandeNr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTyhistatavaKandeNr() {
        return tyhistatavaKandeNr;
    }

    /**
     * Sets the value of the tyhistatavaKandeNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTyhistatavaKandeNr(Integer value) {
        this.tyhistatavaKandeNr = value;
    }

}
