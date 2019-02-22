
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveRegistriParing_v1_IN complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveRegistriParing_v1_IN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tagasta_nimed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="tulemuste_lk" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="registrikoodid" type="{http://arireg.x-road.eu/producer/}earveRegistriParing_v1_Registrikoodid" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveRegistriParing_v1_IN", propOrder = {
    "tagastaNimed",
    "tulemusteLk",
    "registrikoodid"
})
public class EarveRegistriParingV1IN {

    @XmlElement(name = "tagasta_nimed")
    protected Boolean tagastaNimed;
    @XmlElement(name = "tulemuste_lk", required = true)
    protected BigInteger tulemusteLk;
    protected EarveRegistriParingV1Registrikoodid registrikoodid;

    /**
     * Gets the value of the tagastaNimed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTagastaNimed() {
        return tagastaNimed;
    }

    /**
     * Sets the value of the tagastaNimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTagastaNimed(Boolean value) {
        this.tagastaNimed = value;
    }

    /**
     * Gets the value of the tulemusteLk property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTulemusteLk() {
        return tulemusteLk;
    }

    /**
     * Sets the value of the tulemusteLk property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTulemusteLk(BigInteger value) {
        this.tulemusteLk = value;
    }

    /**
     * Gets the value of the registrikoodid property.
     * 
     * @return
     *     possible object is
     *     {@link EarveRegistriParingV1Registrikoodid }
     *     
     */
    public EarveRegistriParingV1Registrikoodid getRegistrikoodid() {
        return registrikoodid;
    }

    /**
     * Sets the value of the registrikoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EarveRegistriParingV1Registrikoodid }
     *     
     */
    public void setRegistrikoodid(EarveRegistriParingV1Registrikoodid value) {
        this.registrikoodid = value;
    }

}
