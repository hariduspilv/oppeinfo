
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for napTasumiseLisamine_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="napTasumiseLisamine_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}NapTasumiseLisamineQuery"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}NapTasumiseLisamineResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "napTasumiseLisamine_v1Response", propOrder = {
    "paring",
    "keha"
})
public class NapTasumiseLisamineV1Response {

    @XmlElement(required = true)
    protected NapTasumiseLisamineQuery paring;
    @XmlElement(required = true)
    protected NapTasumiseLisamineResponse keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link NapTasumiseLisamineQuery }
     *     
     */
    public NapTasumiseLisamineQuery getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link NapTasumiseLisamineQuery }
     *     
     */
    public void setParing(NapTasumiseLisamineQuery value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link NapTasumiseLisamineResponse }
     *     
     */
    public NapTasumiseLisamineResponse getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link NapTasumiseLisamineResponse }
     *     
     */
    public void setKeha(NapTasumiseLisamineResponse value) {
        this.keha = value;
    }

}
