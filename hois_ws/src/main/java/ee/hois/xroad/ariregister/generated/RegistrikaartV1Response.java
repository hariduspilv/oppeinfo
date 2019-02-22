
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registrikaart_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registrikaart_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}registrikaart_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}registrikaart_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrikaart_v1Response", propOrder = {
    "paring",
    "keha"
})
public class RegistrikaartV1Response {

    @XmlElement(required = true)
    protected RegistrikaartParing paring;
    @XmlElement(required = true)
    protected RegistrikaartVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link RegistrikaartParing }
     *     
     */
    public RegistrikaartParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrikaartParing }
     *     
     */
    public void setParing(RegistrikaartParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link RegistrikaartVastus }
     *     
     */
    public RegistrikaartVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrikaartVastus }
     *     
     */
    public void setKeha(RegistrikaartVastus value) {
        this.keha = value;
    }

}
