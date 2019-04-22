
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRVMELUKOHTVmEkRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRVMELUKOHTVmEkRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRVMELUKOHTVmEk" type="{http://rr.x-road.eu/producer}RRExtDocumentData2Request"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRVMELUKOHTVmEkRequestType", propOrder = {
    "rrvmelukohtVmEk"
})
public class RRVMELUKOHTVmEkRequestType {

    @XmlElement(name = "RRVMELUKOHTVmEk", required = true)
    protected RRExtDocumentData2Request rrvmelukohtVmEk;

    /**
     * Gets the value of the rrvmelukohtVmEk property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentData2Request }
     *     
     */
    public RRExtDocumentData2Request getRRVMELUKOHTVmEk() {
        return rrvmelukohtVmEk;
    }

    /**
     * Sets the value of the rrvmelukohtVmEk property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentData2Request }
     *     
     */
    public void setRRVMELUKOHTVmEk(RRExtDocumentData2Request value) {
        this.rrvmelukohtVmEk = value;
    }

}
