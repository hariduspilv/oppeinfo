
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tkV2AkadPuhkus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tkV2AkadPuhkus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="algusKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eeldatavLoppKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegelikLoppKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tkV2AkadPuhkus", propOrder = {
    "algusKp",
    "pohjus",
    "eeldatavLoppKp",
    "tegelikLoppKp"
})
public class TkV2AkadPuhkus {

    @XmlElement(required = true)
    protected String algusKp;
    @XmlElement(required = true)
    protected String pohjus;
    protected String eeldatavLoppKp;
    protected String tegelikLoppKp;

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

    /**
     * Gets the value of the eeldatavLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEeldatavLoppKp() {
        return eeldatavLoppKp;
    }

    /**
     * Sets the value of the eeldatavLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEeldatavLoppKp(String value) {
        this.eeldatavLoppKp = value;
    }

    /**
     * Gets the value of the tegelikLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegelikLoppKp() {
        return tegelikLoppKp;
    }

    /**
     * Sets the value of the tegelikLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegelikLoppKp(String value) {
        this.tegelikLoppKp = value;
    }

}
