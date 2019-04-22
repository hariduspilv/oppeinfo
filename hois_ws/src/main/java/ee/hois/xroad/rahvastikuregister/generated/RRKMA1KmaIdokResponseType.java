
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKMA1KmaIdokResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKMA1KmaIdokResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRKMA1KmaIdok" type="{http://rr.x-road.eu/producer}RRExtDocumentDataResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKMA1KmaIdokResponseType", propOrder = {
    "rrkma1KmaIdok"
})
public class RRKMA1KmaIdokResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRKMA1KmaIdok", required = true)
    protected RRExtDocumentDataResponse rrkma1KmaIdok;

    /**
     * Gets the value of the rrkma1KmaIdok property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public RRExtDocumentDataResponse getRRKMA1KmaIdok() {
        return rrkma1KmaIdok;
    }

    /**
     * Sets the value of the rrkma1KmaIdok property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataResponse }
     *     
     */
    public void setRRKMA1KmaIdok(RRExtDocumentDataResponse value) {
        this.rrkma1KmaIdok = value;
    }

}
