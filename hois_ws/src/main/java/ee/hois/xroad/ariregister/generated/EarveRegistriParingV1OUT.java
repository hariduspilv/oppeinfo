
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveRegistriParing_v1_OUT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveRegistriParing_v1_OUT"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="lehekylgi" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="kliendid" type="{http://arireg.x-road.eu/producer/}earveRegistriParing_v1Response_Kliendid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveRegistriParing_v1_OUT", propOrder = {
    "lehekylgi",
    "kliendid"
})
public class EarveRegistriParingV1OUT {

    @XmlElement(required = true)
    protected BigInteger lehekylgi;
    @XmlElement(required = true)
    protected EarveRegistriParingV1ResponseKliendid kliendid;

    /**
     * Gets the value of the lehekylgi property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLehekylgi() {
        return lehekylgi;
    }

    /**
     * Sets the value of the lehekylgi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLehekylgi(BigInteger value) {
        this.lehekylgi = value;
    }

    /**
     * Gets the value of the kliendid property.
     * 
     * @return
     *     possible object is
     *     {@link EarveRegistriParingV1ResponseKliendid }
     *     
     */
    public EarveRegistriParingV1ResponseKliendid getKliendid() {
        return kliendid;
    }

    /**
     * Sets the value of the kliendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EarveRegistriParingV1ResponseKliendid }
     *     
     */
    public void setKliendid(EarveRegistriParingV1ResponseKliendid value) {
        this.kliendid = value;
    }

}
