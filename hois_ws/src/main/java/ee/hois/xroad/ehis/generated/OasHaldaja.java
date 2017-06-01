
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oasHaldaja complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oasHaldaja"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="haldajaRegistrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haldajaNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oasHaldaja", propOrder = {
    "haldajaRegistrikood",
    "haldajaNimetus"
})
public class OasHaldaja {

    protected String haldajaRegistrikood;
    protected String haldajaNimetus;

    /**
     * Gets the value of the haldajaRegistrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaldajaRegistrikood() {
        return haldajaRegistrikood;
    }

    /**
     * Sets the value of the haldajaRegistrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaldajaRegistrikood(String value) {
        this.haldajaRegistrikood = value;
    }

    /**
     * Gets the value of the haldajaNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaldajaNimetus() {
        return haldajaNimetus;
    }

    /**
     * Sets the value of the haldajaNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaldajaNimetus(String value) {
        this.haldajaNimetus = value;
    }

}
