
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esindusIseendaKohta_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esindusIseendaKohta_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}paringesindus_iseendakohta_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}paringesindus_iseendakohta_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esindusIseendaKohta_v1Response", propOrder = {
    "paring",
    "keha"
})
public class EsindusIseendaKohtaV1Response {

    @XmlElement(required = true)
    protected ParingesindusIseendakohtaParing paring;
    @XmlElement(required = true)
    protected ParingesindusIseendakohtaVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusIseendakohtaParing }
     *     
     */
    public ParingesindusIseendakohtaParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusIseendakohtaParing }
     *     
     */
    public void setParing(ParingesindusIseendakohtaParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusIseendakohtaVastus }
     *     
     */
    public ParingesindusIseendakohtaVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusIseendakohtaVastus }
     *     
     */
    public void setKeha(ParingesindusIseendakohtaVastus value) {
        this.keha = value;
    }

}
