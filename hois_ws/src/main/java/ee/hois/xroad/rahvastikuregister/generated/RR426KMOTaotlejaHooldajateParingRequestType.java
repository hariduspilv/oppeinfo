
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR426KMOTaotlejaHooldajateParingRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR426KMOTaotlejaHooldajateParingRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RR426KMOTaotlejaHooldajateParing" type="{http://rr.x-road.eu/producer}KMOIsikuTuvastamine"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR426KMOTaotlejaHooldajateParingRequestType", propOrder = {
    "rr426KMOTaotlejaHooldajateParing"
})
public class RR426KMOTaotlejaHooldajateParingRequestType {

    @XmlElement(name = "RR426KMOTaotlejaHooldajateParing", required = true)
    protected KMOIsikuTuvastamine rr426KMOTaotlejaHooldajateParing;

    /**
     * Gets the value of the rr426KMOTaotlejaHooldajateParing property.
     * 
     * @return
     *     possible object is
     *     {@link KMOIsikuTuvastamine }
     *     
     */
    public KMOIsikuTuvastamine getRR426KMOTaotlejaHooldajateParing() {
        return rr426KMOTaotlejaHooldajateParing;
    }

    /**
     * Sets the value of the rr426KMOTaotlejaHooldajateParing property.
     * 
     * @param value
     *     allowed object is
     *     {@link KMOIsikuTuvastamine }
     *     
     */
    public void setRR426KMOTaotlejaHooldajateParing(KMOIsikuTuvastamine value) {
        this.rr426KMOTaotlejaHooldajateParing = value;
    }

}
