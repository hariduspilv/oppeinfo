
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for arikeelud_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="arikeelud_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}paringarikeelud_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}paringarikeelud_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arikeelud_v1Response", propOrder = {
    "paring",
    "keha"
})
public class ArikeeludV1Response {

    @XmlElement(required = true)
    protected ParingarikeeludParing paring;
    @XmlElement(required = true)
    protected ParingarikeeludVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link ParingarikeeludParing }
     *     
     */
    public ParingarikeeludParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingarikeeludParing }
     *     
     */
    public void setParing(ParingarikeeludParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link ParingarikeeludVastus }
     *     
     */
    public ParingarikeeludVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingarikeeludVastus }
     *     
     */
    public void setKeha(ParingarikeeludVastus value) {
        this.keha = value;
    }

}
