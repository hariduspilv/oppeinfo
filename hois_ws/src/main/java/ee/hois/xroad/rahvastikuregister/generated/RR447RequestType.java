
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR447RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR447RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AlgusKP" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LoppKP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR447RequestType", propOrder = {
    "algusKP",
    "loppKP"
})
public class RR447RequestType {

    @XmlElement(name = "AlgusKP", required = true)
    protected String algusKP;
    @XmlElement(name = "LoppKP")
    protected String loppKP;

    /**
     * Gets the value of the algusKP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgusKP() {
        return algusKP;
    }

    /**
     * Sets the value of the algusKP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgusKP(String value) {
        this.algusKP = value;
    }

    /**
     * Gets the value of the loppKP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoppKP() {
        return loppKP;
    }

    /**
     * Sets the value of the loppKP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoppKP(String value) {
        this.loppKP = value;
    }

}