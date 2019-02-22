
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kyRegistriosaMuutused_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kyRegistriosaMuutused_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}ky_registriosamuut_v1_Query"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}ky_registriosamuut_v1_Vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kyRegistriosaMuutused_v1Response", propOrder = {
    "paring",
    "keha"
})
public class KyRegistriosaMuutusedV1Response {

    @XmlElement(required = true)
    protected KyRegistriosamuutV1Query paring;
    @XmlElement(required = true)
    protected KyRegistriosamuutV1Vastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link KyRegistriosamuutV1Query }
     *     
     */
    public KyRegistriosamuutV1Query getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link KyRegistriosamuutV1Query }
     *     
     */
    public void setParing(KyRegistriosamuutV1Query value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link KyRegistriosamuutV1Vastus }
     *     
     */
    public KyRegistriosamuutV1Vastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link KyRegistriosamuutV1Vastus }
     *     
     */
    public void setKeha(KyRegistriosamuutV1Vastus value) {
        this.keha = value;
    }

}
