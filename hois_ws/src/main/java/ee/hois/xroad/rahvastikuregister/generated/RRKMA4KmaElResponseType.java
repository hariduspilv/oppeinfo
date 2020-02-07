
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKMA4KmaElResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKMA4KmaElResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRKMA4KmaEl" type="{http://rr.x-road.eu/producer}RRExtDocumentDataResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKMA4KmaElResponseType", propOrder = {
    "rrkma4KmaEl"
})
public class RRKMA4KmaElResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRKMA4KmaEl", required = true)
    protected RRExtDocumentDataResponse rrkma4KmaEl;

    /**
     * Gets the value of the rrkma4KmaEl property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public RRExtDocumentDataResponse getRRKMA4KmaEl() {
        return rrkma4KmaEl;
    }

    /**
     * Sets the value of the rrkma4KmaEl property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public void setRRKMA4KmaEl(RRExtDocumentDataResponse value) {
        this.rrkma4KmaEl = value;
    }

}
