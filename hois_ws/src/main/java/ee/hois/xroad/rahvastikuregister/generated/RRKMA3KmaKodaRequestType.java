
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKMA3KmaKodaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKMA3KmaKodaRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRKMA3KmaKoda" type="{http://rr.x-road.eu/producer}RRExtDocumentDataRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKMA3KmaKodaRequestType", propOrder = {
    "rrkma3KmaKoda"
})
public class RRKMA3KmaKodaRequestType {

    @XmlElement(name = "RRKMA3KmaKoda", required = true)
    protected RRExtDocumentDataRequest rrkma3KmaKoda;

    /**
     * Gets the value of the rrkma3KmaKoda property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public RRExtDocumentDataRequest getRRKMA3KmaKoda() {
        return rrkma3KmaKoda;
    }

    /**
     * Sets the value of the rrkma3KmaKoda property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public void setRRKMA3KmaKoda(RRExtDocumentDataRequest value) {
        this.rrkma3KmaKoda = value;
    }

}
