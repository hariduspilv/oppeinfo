
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for soovitus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="soovitus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="soovitusLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="soovitusKlf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="muuVaartus" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="255"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="kommentaar" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}innoveKommentaar" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soovitus", propOrder = {
    "soovitusLiik",
    "soovitusKlf",
    "muuVaartus",
    "kommentaar"
})
public class Soovitus {

    @XmlElement(required = true)
    protected String soovitusLiik;
    protected String soovitusKlf;
    protected String muuVaartus;
    protected String kommentaar;

    /**
     * Gets the value of the soovitusLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoovitusLiik() {
        return soovitusLiik;
    }

    /**
     * Sets the value of the soovitusLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoovitusLiik(String value) {
        this.soovitusLiik = value;
    }

    /**
     * Gets the value of the soovitusKlf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoovitusKlf() {
        return soovitusKlf;
    }

    /**
     * Sets the value of the soovitusKlf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoovitusKlf(String value) {
        this.soovitusKlf = value;
    }

    /**
     * Gets the value of the muuVaartus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuuVaartus() {
        return muuVaartus;
    }

    /**
     * Sets the value of the muuVaartus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuuVaartus(String value) {
        this.muuVaartus = value;
    }

    /**
     * Gets the value of the kommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommentaar() {
        return kommentaar;
    }

    /**
     * Sets the value of the kommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommentaar(String value) {
        this.kommentaar = value;
    }

}
