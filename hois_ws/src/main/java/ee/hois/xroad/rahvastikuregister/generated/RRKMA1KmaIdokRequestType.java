
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKMA1KmaIdokRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKMA1KmaIdokRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRKMA1KmaIdok" type="{http://rr.x-road.eu/producer}RRExtDocumentDataRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKMA1KmaIdokRequestType", propOrder = {
    "rrkma1KmaIdok"
})
public class RRKMA1KmaIdokRequestType {

    @XmlElement(name = "RRKMA1KmaIdok", required = true)
    protected RRExtDocumentDataRequest rrkma1KmaIdok;

    /**
     * Gets the value of the rrkma1KmaIdok property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public RRExtDocumentDataRequest getRRKMA1KmaIdok() {
        return rrkma1KmaIdok;
    }

    /**
     * Sets the value of the rrkma1KmaIdok property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataRequest }
     *     
     */
    public void setRRKMA1KmaIdok(RRExtDocumentDataRequest value) {
        this.rrkma1KmaIdok = value;
    }

}
