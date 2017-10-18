
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oiskutseSpetsialiseerumine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oiskutseSpetsialiseerumine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kutseSpetsialiseerumineReaId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oiskutseSpetsialiseerumine", propOrder = {
    "kutseSpetsialiseerumineReaId"
})
public class OiskutseSpetsialiseerumine {

    @XmlElement(required = true)
    protected BigInteger kutseSpetsialiseerumineReaId;

    /**
     * Gets the value of the kutseSpetsialiseerumineReaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKutseSpetsialiseerumineReaId() {
        return kutseSpetsialiseerumineReaId;
    }

    /**
     * Sets the value of the kutseSpetsialiseerumineReaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKutseSpetsialiseerumineReaId(BigInteger value) {
        this.kutseSpetsialiseerumineReaId = value;
    }

}
