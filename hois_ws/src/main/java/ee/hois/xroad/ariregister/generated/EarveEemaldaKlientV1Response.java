
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveEemaldaKlient_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveEemaldaKlient_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}earveEemaldaKlient_v1_IN"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}earveEemaldaKlient_v1_OUT"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveEemaldaKlient_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EarveEemaldaKlientV1Response {

    @XmlElement(required = true)
    protected EarveEemaldaKlientV1IN paring;
    @XmlElement(required = true)
    protected EarveEemaldaKlientV1OUT keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link EarveEemaldaKlientV1IN }
     *     
     */
    public EarveEemaldaKlientV1IN getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link EarveEemaldaKlientV1IN }
     *     
     */
    public void setParing(EarveEemaldaKlientV1IN value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EarveEemaldaKlientV1OUT }
     *     
     */
    public EarveEemaldaKlientV1OUT getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EarveEemaldaKlientV1OUT }
     *     
     */
    public void setKeha(EarveEemaldaKlientV1OUT value) {
        this.keha = value;
    }

}