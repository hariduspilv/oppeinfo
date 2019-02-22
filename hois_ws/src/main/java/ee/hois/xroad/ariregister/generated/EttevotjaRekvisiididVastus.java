
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_rekvisiidid_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_rekvisiidid_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotjad" type="{http://arireg.x-road.eu/producer/}rekvisiidid_ettevotted"/&gt;
 *         &lt;element name="leitud_ettevotjate_arv" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_rekvisiidid_vastus", propOrder = {
    "ettevotjad",
    "leitudEttevotjateArv"
})
public class EttevotjaRekvisiididVastus {

    @XmlElement(required = true)
    protected RekvisiididEttevotted ettevotjad;
    @XmlElement(name = "leitud_ettevotjate_arv", required = true)
    protected BigInteger leitudEttevotjateArv;

    /**
     * Gets the value of the ettevotjad property.
     * 
     * @return
     *     possible object is
     *     {@link RekvisiididEttevotted }
     *     
     */
    public RekvisiididEttevotted getEttevotjad() {
        return ettevotjad;
    }

    /**
     * Sets the value of the ettevotjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link RekvisiididEttevotted }
     *     
     */
    public void setEttevotjad(RekvisiididEttevotted value) {
        this.ettevotjad = value;
    }

    /**
     * Gets the value of the leitudEttevotjateArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLeitudEttevotjateArv() {
        return leitudEttevotjateArv;
    }

    /**
     * Sets the value of the leitudEttevotjateArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLeitudEttevotjateArv(BigInteger value) {
        this.leitudEttevotjateArv = value;
    }

}
