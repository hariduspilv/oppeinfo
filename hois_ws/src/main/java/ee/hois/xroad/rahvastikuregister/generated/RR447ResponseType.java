
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR447ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR447ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cTellimuseNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR447ResponseType", propOrder = {
    "cTellimuseNr"
})
public class RR447ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected String cTellimuseNr;

    /**
     * Gets the value of the cTellimuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCTellimuseNr() {
        return cTellimuseNr;
    }

    /**
     * Sets the value of the cTellimuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCTellimuseNr(String value) {
        this.cTellimuseNr = value;
    }

}
