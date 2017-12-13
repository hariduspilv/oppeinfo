
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Kutsetunnistuse taotluse lisamise vastus
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="vigadearv" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="vead" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}veadType" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "kutsetunnistuseLisamineVastus")
public class KutsetunnistuseLisamineVastus {

    @XmlElement(required = true)
    protected BigInteger vigadearv;
    protected VeadType vead;

    /**
     * Gets the value of the vigadearv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVigadearv() {
        return vigadearv;
    }

    /**
     * Sets the value of the vigadearv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVigadearv(BigInteger value) {
        this.vigadearv = value;
    }

    /**
     * Gets the value of the vead property.
     * 
     * @return
     *     possible object is
     *     {@link VeadType }
     *     
     */
    public VeadType getVead() {
        return vead;
    }

    /**
     * Sets the value of the vead property.
     * 
     * @param value
     *     allowed object is
     *     {@link VeadType }
     *     
     */
    public void setVead(VeadType value) {
        this.vead = value;
    }

}
