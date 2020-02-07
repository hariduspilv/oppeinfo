
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kmaEhisAkadPuhkus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kmaEhisAkadPuhkus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="algusKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="loppKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kmaEhisAkadPuhkus", propOrder = {
    "algusKp",
    "loppKp",
    "pohjus"
})
public class KmaEhisAkadPuhkus {

    @XmlElement(required = true)
    protected String algusKp;
    protected String loppKp;
    @XmlElement(required = true)
    protected String pohjus;

    /**
     * Gets the value of the algusKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgusKp() {
        return algusKp;
    }

    /**
     * Sets the value of the algusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgusKp(String value) {
        this.algusKp = value;
    }

    /**
     * Gets the value of the loppKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoppKp() {
        return loppKp;
    }

    /**
     * Sets the value of the loppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoppKp(String value) {
        this.loppKp = value;
    }

    /**
     * Gets the value of the pohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohjus() {
        return pohjus;
    }

    /**
     * Sets the value of the pohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohjus(String value) {
        this.pohjus = value;
    }

}
