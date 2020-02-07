
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKMA3KmaKodaResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKMA3KmaKodaResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRKMA3KmaKoda" type="{http://rr.x-road.eu/producer}RRExtDocumentDataResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKMA3KmaKodaResponseType", propOrder = {
    "rrkma3KmaKoda"
})
public class RRKMA3KmaKodaResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRKMA3KmaKoda", required = true)
    protected RRExtDocumentDataResponse rrkma3KmaKoda;

    /**
     * Gets the value of the rrkma3KmaKoda property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public RRExtDocumentDataResponse getRRKMA3KmaKoda() {
        return rrkma3KmaKoda;
    }

    /**
     * Sets the value of the rrkma3KmaKoda property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public void setRRKMA3KmaKoda(RRExtDocumentDataResponse value) {
        this.rrkma3KmaKoda = value;
    }

}
