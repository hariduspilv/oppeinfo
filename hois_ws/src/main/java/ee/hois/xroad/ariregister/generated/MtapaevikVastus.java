
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtapaevik_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtapaevik_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paevikud" type="{http://arireg.x-road.eu/producer/}mtapaevik_paevikud"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtapaevik_vastus", propOrder = {
    "paevikud"
})
public class MtapaevikVastus {

    @XmlElement(required = true)
    protected MtapaevikPaevikud paevikud;

    /**
     * Gets the value of the paevikud property.
     * 
     * @return
     *     possible object is
     *     {@link MtapaevikPaevikud }
     *     
     */
    public MtapaevikPaevikud getPaevikud() {
        return paevikud;
    }

    /**
     * Sets the value of the paevikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link MtapaevikPaevikud }
     *     
     */
    public void setPaevikud(MtapaevikPaevikud value) {
        this.paevikud = value;
    }

}
