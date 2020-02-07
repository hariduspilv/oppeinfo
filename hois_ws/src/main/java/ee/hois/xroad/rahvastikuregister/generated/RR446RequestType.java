
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR446RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR446RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Synniaasta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR446RequestType", propOrder = {
    "synniaasta",
    "sugu"
})
public class RR446RequestType {

    @XmlElement(name = "Synniaasta", required = true)
    protected String synniaasta;
    @XmlElement(name = "Sugu")
    protected String sugu;

    /**
     * Gets the value of the synniaasta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynniaasta() {
        return synniaasta;
    }

    /**
     * Sets the value of the synniaasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynniaasta(String value) {
        this.synniaasta = value;
    }

    /**
     * Gets the value of the sugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSugu() {
        return sugu;
    }

    /**
     * Sets the value of the sugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSugu(String value) {
        this.sugu = value;
    }

}
