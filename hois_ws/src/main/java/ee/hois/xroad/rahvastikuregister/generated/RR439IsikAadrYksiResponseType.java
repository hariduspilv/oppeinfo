
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR439isikAadrYksiResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR439isikAadrYksiResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="vastus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR439isikAadrYksiResponseType", propOrder = {
    "vastus"
})
public class RR439IsikAadrYksiResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected String vastus;

    /**
     * Gets the value of the vastus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastus() {
        return vastus;
    }

    /**
     * Sets the value of the vastus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastus(String value) {
        this.vastus = value;
    }

}
