
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pohjusetaPuudumised complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pohjusetaPuudumised"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veerand" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="pohjusetaPuudumisteProtsent" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pohjusetaPuudumised", propOrder = {
    "veerand",
    "pohjusetaPuudumisteProtsent"
})
public class PohjusetaPuudumised {

    @XmlElement(required = true, nillable = true)
    protected BigInteger veerand;
    @XmlElement(required = true, type = Float.class, nillable = true)
    protected Float pohjusetaPuudumisteProtsent;

    /**
     * Gets the value of the veerand property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeerand() {
        return veerand;
    }

    /**
     * Sets the value of the veerand property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeerand(BigInteger value) {
        this.veerand = value;
    }

    /**
     * Gets the value of the pohjusetaPuudumisteProtsent property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPohjusetaPuudumisteProtsent() {
        return pohjusetaPuudumisteProtsent;
    }

    /**
     * Sets the value of the pohjusetaPuudumisteProtsent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPohjusetaPuudumisteProtsent(Float value) {
        this.pohjusetaPuudumisteProtsent = value;
    }

}
