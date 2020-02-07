
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR421KMOElKodElukohaRegistreerimineRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR421KMOElKodElukohaRegistreerimineRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RR421KMOElKodElukohaRegistreerimine" type="{http://rr.x-road.eu/producer}KMOIsikuTuvastamine"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR421KMOElKodElukohaRegistreerimineRequestType", propOrder = {
    "rr421KMOElKodElukohaRegistreerimine"
})
public class RR421KMOElKodElukohaRegistreerimineRequestType {

    @XmlElement(name = "RR421KMOElKodElukohaRegistreerimine", required = true)
    protected KMOIsikuTuvastamine rr421KMOElKodElukohaRegistreerimine;

    /**
     * Gets the value of the rr421KMOElKodElukohaRegistreerimine property.
     * 
     * @return
     *     possible object is
     *     {@link KMOIsikuTuvastamine }
     *     
     */
    public KMOIsikuTuvastamine getRR421KMOElKodElukohaRegistreerimine() {
        return rr421KMOElKodElukohaRegistreerimine;
    }

    /**
     * Sets the value of the rr421KMOElKodElukohaRegistreerimine property.
     * 
     * @param value
     *     allowed object is
     *     {@link KMOIsikuTuvastamine }
     *     
     */
    public void setRR421KMOElKodElukohaRegistreerimine(KMOIsikuTuvastamine value) {
        this.rr421KMOElKodElukohaRegistreerimine = value;
    }

}
