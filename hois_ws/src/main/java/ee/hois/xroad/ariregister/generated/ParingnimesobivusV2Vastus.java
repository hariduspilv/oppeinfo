
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringnimesobivus_v2_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringnimesobivus_v2_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nimed" type="{http://arireg.x-road.eu/producer/}paringnimesobivus_v2_read"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringnimesobivus_v2_vastus", propOrder = {
    "nimed"
})
public class ParingnimesobivusV2Vastus {

    @XmlElement(required = true)
    protected ParingnimesobivusV2Read nimed;

    /**
     * Gets the value of the nimed property.
     * 
     * @return
     *     possible object is
     *     {@link ParingnimesobivusV2Read }
     *     
     */
    public ParingnimesobivusV2Read getNimed() {
        return nimed;
    }

    /**
     * Sets the value of the nimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingnimesobivusV2Read }
     *     
     */
    public void setNimed(ParingnimesobivusV2Read value) {
        this.nimed = value;
    }

}
