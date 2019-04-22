
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR901ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR901ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RR901" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR901ResponseType", propOrder = {
    "rr901"
})
public class RR901ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RR901", required = true)
    protected String rr901;

    /**
     * Gets the value of the rr901 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRR901() {
        return rr901;
    }

    /**
     * Sets the value of the rr901 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRR901(String value) {
        this.rr901 = value;
    }

}
