
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringesindus_iseendakohta_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringesindus_iseendakohta_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotjad" type="{http://arireg.x-road.eu/producer/}paringesindus_iseendakohta_ettevotted"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringesindus_iseendakohta_vastus", propOrder = {
    "ettevotjad"
})
public class ParingesindusIseendakohtaVastus {

    @XmlElement(required = true)
    protected ParingesindusIseendakohtaEttevotted ettevotjad;

    /**
     * Gets the value of the ettevotjad property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusIseendakohtaEttevotted }
     *     
     */
    public ParingesindusIseendakohtaEttevotted getEttevotjad() {
        return ettevotjad;
    }

    /**
     * Sets the value of the ettevotjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusIseendakohtaEttevotted }
     *     
     */
    public void setEttevotjad(ParingesindusIseendakohtaEttevotted value) {
        this.ettevotjad = value;
    }

}
