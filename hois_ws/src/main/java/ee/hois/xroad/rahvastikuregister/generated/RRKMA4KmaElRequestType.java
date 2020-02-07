
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKMA4KmaElRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKMA4KmaElRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRKMA4KmaEl" type="{http://rr.x-road.eu/producer}RRExtDocumentDataRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKMA4KmaElRequestType", propOrder = {
    "rrkma4KmaEl"
})
public class RRKMA4KmaElRequestType {

    @XmlElement(name = "RRKMA4KmaEl", required = true)
    protected RRExtDocumentDataRequest rrkma4KmaEl;

    /**
     * Gets the value of the rrkma4KmaEl property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public RRExtDocumentDataRequest getRRKMA4KmaEl() {
        return rrkma4KmaEl;
    }

    /**
     * Sets the value of the rrkma4KmaEl property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public void setRRKMA4KmaEl(RRExtDocumentDataRequest value) {
        this.rrkma4KmaEl = value;
    }

}
