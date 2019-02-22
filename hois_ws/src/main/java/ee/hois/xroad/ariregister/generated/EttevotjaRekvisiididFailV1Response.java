
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotjaRekvisiididFail_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotjaRekvisiididFail_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}ettevotja_rekvisiidid_fail_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}ettevotja_rekvisiidid_fail_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotjaRekvisiididFail_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EttevotjaRekvisiididFailV1Response {

    @XmlElement(required = true)
    protected EttevotjaRekvisiididFailParing paring;
    @XmlElement(required = true)
    protected EttevotjaRekvisiididFailVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaRekvisiididFailParing }
     *     
     */
    public EttevotjaRekvisiididFailParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaRekvisiididFailParing }
     *     
     */
    public void setParing(EttevotjaRekvisiididFailParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaRekvisiididFailVastus }
     *     
     */
    public EttevotjaRekvisiididFailVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaRekvisiididFailVastus }
     *     
     */
    public void setKeha(EttevotjaRekvisiididFailVastus value) {
        this.keha = value;
    }

}
