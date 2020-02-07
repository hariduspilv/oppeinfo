
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKooseluResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKooseluResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRExtDocumentDataKooselu" type="{http://rr.x-road.eu/producer}RRExtDocumentDataKooseluResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKooseluResponseType", propOrder = {
    "rrExtDocumentDataKooselu"
})
public class RRKooseluResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRExtDocumentDataKooselu", required = true)
    protected RRExtDocumentDataKooseluResponse rrExtDocumentDataKooselu;

    /**
     * Gets the value of the rrExtDocumentDataKooselu property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataKooseluResponse }
     *     
     */
    public RRExtDocumentDataKooseluResponse getRRExtDocumentDataKooselu() {
        return rrExtDocumentDataKooselu;
    }

    /**
     * Sets the value of the rrExtDocumentDataKooselu property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataKooseluResponse }
     *     
     */
    public void setRRExtDocumentDataKooselu(RRExtDocumentDataKooseluResponse value) {
        this.rrExtDocumentDataKooselu = value;
    }

}
