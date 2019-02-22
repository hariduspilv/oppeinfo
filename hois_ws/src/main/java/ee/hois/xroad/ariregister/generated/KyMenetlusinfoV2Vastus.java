
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ky_menetlusinfo_v2_Vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ky_menetlusinfo_v2_Vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="staatus_detailid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ky_menetlusinfo_v2_Vastus", propOrder = {
    "staatus",
    "staatusDetailid"
})
public class KyMenetlusinfoV2Vastus {

    @XmlElement(required = true)
    protected BigInteger staatus;
    @XmlElement(name = "staatus_detailid", required = true)
    protected String staatusDetailid;

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStaatus(BigInteger value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the staatusDetailid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatusDetailid() {
        return staatusDetailid;
    }

    /**
     * Sets the value of the staatusDetailid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatusDetailid(String value) {
        this.staatusDetailid = value;
    }

}
