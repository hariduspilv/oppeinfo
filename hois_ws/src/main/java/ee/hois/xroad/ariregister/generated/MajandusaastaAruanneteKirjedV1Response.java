
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for majandusaastaAruanneteKirjed_v1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="majandusaastaAruanneteKirjed_v1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paring" type="{http://arireg.x-road.eu/producer/}majandusaasta_aruannete_kirjed_paring"/&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}majandusaasta_aruannete_kirjed_vastus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "majandusaastaAruanneteKirjed_v1Response", propOrder = {
    "paring",
    "keha"
})
public class MajandusaastaAruanneteKirjedV1Response {

    @XmlElement(required = true)
    protected MajandusaastaAruanneteKirjedParing paring;
    @XmlElement(required = true)
    protected MajandusaastaAruanneteKirjedVastus keha;

    /**
     * Gets the value of the paring property.
     * 
     * @return
     *     possible object is
     *     {@link MajandusaastaAruanneteKirjedParing }
     *     
     */
    public MajandusaastaAruanneteKirjedParing getParing() {
        return paring;
    }

    /**
     * Sets the value of the paring property.
     * 
     * @param value
     *     allowed object is
     *     {@link MajandusaastaAruanneteKirjedParing }
     *     
     */
    public void setParing(MajandusaastaAruanneteKirjedParing value) {
        this.paring = value;
    }

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link MajandusaastaAruanneteKirjedVastus }
     *     
     */
    public MajandusaastaAruanneteKirjedVastus getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link MajandusaastaAruanneteKirjedVastus }
     *     
     */
    public void setKeha(MajandusaastaAruanneteKirjedVastus value) {
        this.keha = value;
    }

}
