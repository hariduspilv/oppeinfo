
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisValisOAS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisValisOAS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="valisOASKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="valisOASNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisValisOAS", propOrder = {
    "valisOASKood",
    "valisOASNimetus"
})
public class OisValisOAS {

    @XmlElement(required = true)
    protected String valisOASKood;
    protected String valisOASNimetus;

    /**
     * Gets the value of the valisOASKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValisOASKood() {
        return valisOASKood;
    }

    /**
     * Sets the value of the valisOASKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValisOASKood(String value) {
        this.valisOASKood = value;
    }

    /**
     * Gets the value of the valisOASNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValisOASNimetus() {
        return valisOASNimetus;
    }

    /**
     * Sets the value of the valisOASNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValisOASNimetus(String value) {
        this.valisOASNimetus = value;
    }

}
