
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveEemaldaKlient_v1_OUT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveEemaldaKlient_v1_OUT"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kliendid" type="{http://arireg.x-road.eu/producer/}earveEemaldaKlient_v1Response_Kliendid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveEemaldaKlient_v1_OUT", propOrder = {
    "kliendid"
})
public class EarveEemaldaKlientV1OUT {

    @XmlElement(required = true)
    protected EarveEemaldaKlientV1ResponseKliendid kliendid;

    /**
     * Gets the value of the kliendid property.
     * 
     * @return
     *     possible object is
     *     {@link EarveEemaldaKlientV1ResponseKliendid }
     *     
     */
    public EarveEemaldaKlientV1ResponseKliendid getKliendid() {
        return kliendid;
    }

    /**
     * Sets the value of the kliendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EarveEemaldaKlientV1ResponseKliendid }
     *     
     */
    public void setKliendid(EarveEemaldaKlientV1ResponseKliendid value) {
        this.kliendid = value;
    }

}
