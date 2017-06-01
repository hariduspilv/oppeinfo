
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for alusOppurTugiteenus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alusOppurTugiteenus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klTugiteenus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alusOppurTugiteenus", propOrder = {
    "klTugiteenus",
    "rv"
})
public class AlusOppurTugiteenus {

    @XmlElement(required = true)
    protected String klTugiteenus;
    @XmlElement(required = true)
    protected String rv;

    /**
     * Gets the value of the klTugiteenus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlTugiteenus() {
        return klTugiteenus;
    }

    /**
     * Sets the value of the klTugiteenus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlTugiteenus(String value) {
        this.klTugiteenus = value;
    }

    /**
     * Gets the value of the rv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRv() {
        return rv;
    }

    /**
     * Sets the value of the rv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRv(String value) {
        this.rv = value;
    }

}
