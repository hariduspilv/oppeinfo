
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveLisaKlient_v1_OUT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveLisaKlient_v1_OUT"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kliendid" type="{http://arireg.x-road.eu/producer/}earveLisaKlient_v1Response_Kliendid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveLisaKlient_v1_OUT", propOrder = {
    "kliendid"
})
public class EarveLisaKlientV1OUT {

    @XmlElement(required = true)
    protected EarveLisaKlientV1ResponseKliendid kliendid;

    /**
     * Gets the value of the kliendid property.
     * 
     * @return
     *     possible object is
     *     {@link EarveLisaKlientV1ResponseKliendid }
     *     
     */
    public EarveLisaKlientV1ResponseKliendid getKliendid() {
        return kliendid;
    }

    /**
     * Sets the value of the kliendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EarveLisaKlientV1ResponseKliendid }
     *     
     */
    public void setKliendid(EarveLisaKlientV1ResponseKliendid value) {
        this.kliendid = value;
    }

}
