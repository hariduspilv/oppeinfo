
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRVMELUKOHTVmEkResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRVMELUKOHTVmEkResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRVMELUKOHTVmEk" type="{http://rr.x-road.eu/producer}RRExtDocumentData2Response"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRVMELUKOHTVmEkResponseType", propOrder = {
    "rrvmelukohtVmEk"
})
public class RRVMELUKOHTVmEkResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRVMELUKOHTVmEk", required = true)
    protected RRExtDocumentData2Response rrvmelukohtVmEk;

    /**
     * Gets the value of the rrvmelukohtVmEk property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentData2Response }
     *     
     */
    public RRExtDocumentData2Response getRRVMELUKOHTVmEk() {
        return rrvmelukohtVmEk;
    }

    /**
     * Sets the value of the rrvmelukohtVmEk property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentData2Response }
     *     
     */
    public void setRRVMELUKOHTVmEk(RRExtDocumentData2Response value) {
        this.rrvmelukohtVmEk = value;
    }

}
