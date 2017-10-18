
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisOKSpetsialiseerumised complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisOKSpetsialiseerumised"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="okSpetsialiseerumiseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisOKSpetsialiseerumised", propOrder = {
    "okSpetsialiseerumiseKood"
})
public class OisOKSpetsialiseerumised {

    @XmlElement(required = true)
    protected String okSpetsialiseerumiseKood;

    /**
     * Gets the value of the okSpetsialiseerumiseKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOkSpetsialiseerumiseKood() {
        return okSpetsialiseerumiseKood;
    }

    /**
     * Sets the value of the okSpetsialiseerumiseKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOkSpetsialiseerumiseKood(String value) {
        this.okSpetsialiseerumiseKood = value;
    }

}
