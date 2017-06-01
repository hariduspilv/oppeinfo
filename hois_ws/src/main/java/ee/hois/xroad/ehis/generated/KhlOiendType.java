
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for khlOiendType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlOiendType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oiendiNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lisaleht1Nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisaleht2Nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisaleht3Nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisaleht4Nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlOiendType", propOrder = {
    "oiendiNr",
    "lisaleht1Nr",
    "lisaleht2Nr",
    "lisaleht3Nr",
    "lisaleht4Nr"
})
public class KhlOiendType {

    @XmlElement(required = true)
    protected String oiendiNr;
    protected String lisaleht1Nr;
    protected String lisaleht2Nr;
    protected String lisaleht3Nr;
    protected String lisaleht4Nr;

    /**
     * Gets the value of the oiendiNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiendiNr() {
        return oiendiNr;
    }

    /**
     * Sets the value of the oiendiNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiendiNr(String value) {
        this.oiendiNr = value;
    }

    /**
     * Gets the value of the lisaleht1Nr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisaleht1Nr() {
        return lisaleht1Nr;
    }

    /**
     * Sets the value of the lisaleht1Nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisaleht1Nr(String value) {
        this.lisaleht1Nr = value;
    }

    /**
     * Gets the value of the lisaleht2Nr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisaleht2Nr() {
        return lisaleht2Nr;
    }

    /**
     * Sets the value of the lisaleht2Nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisaleht2Nr(String value) {
        this.lisaleht2Nr = value;
    }

    /**
     * Gets the value of the lisaleht3Nr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisaleht3Nr() {
        return lisaleht3Nr;
    }

    /**
     * Sets the value of the lisaleht3Nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisaleht3Nr(String value) {
        this.lisaleht3Nr = value;
    }

    /**
     * Gets the value of the lisaleht4Nr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisaleht4Nr() {
        return lisaleht4Nr;
    }

    /**
     * Sets the value of the lisaleht4Nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisaleht4Nr(String value) {
        this.lisaleht4Nr = value;
    }

}
