
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sooritakanne_kapital_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sooritakanne_kapital_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="suurus" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="valuuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sooritakanne_kapital_v4", propOrder = {
    "suurus",
    "valuuta"
})
public class SooritakanneKapitalV4 {

    @XmlElement(required = true)
    protected BigDecimal suurus;
    @XmlElement(required = true)
    protected String valuuta;

    /**
     * Gets the value of the suurus property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSuurus() {
        return suurus;
    }

    /**
     * Sets the value of the suurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSuurus(BigDecimal value) {
        this.suurus = value;
    }

    /**
     * Gets the value of the valuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuuta() {
        return valuuta;
    }

    /**
     * Sets the value of the valuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuuta(String value) {
        this.valuuta = value;
    }

}
