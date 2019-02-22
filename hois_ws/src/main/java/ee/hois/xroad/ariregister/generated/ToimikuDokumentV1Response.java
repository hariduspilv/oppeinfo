
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for toimikuDokument_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="toimikuDokument_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}toimiku_dokument_v1_Request"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}toimiku_dokument_v1_Vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "toimikuDokument_v1Response", propOrder = {
    "paring",
    "keha"
})
public class ToimikuDokumentV1Response {

    @XmlElement(required = true)
    protected ToimikuDokumentV1Request paring;
    @XmlElement(required = true)
    protected ToimikuDokumentV1Vastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link ToimikuDokumentV1Request }
     *     
     */
    public ToimikuDokumentV1Request getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToimikuDokumentV1Request }
     *     
     */
    public void setParing(ToimikuDokumentV1Request value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link ToimikuDokumentV1Vastus }
     *     
     */
    public ToimikuDokumentV1Vastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToimikuDokumentV1Vastus }
     *     
     */
    public void setKeha(ToimikuDokumentV1Vastus value) {
        this.keha = value;
    }

}
