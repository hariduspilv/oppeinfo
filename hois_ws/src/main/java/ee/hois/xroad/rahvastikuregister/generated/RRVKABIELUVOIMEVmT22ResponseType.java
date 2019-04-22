
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRVK_ABIELUVOIMEVmT22ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRVK_ABIELUVOIMEVmT22ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRVK_ABIELUVOIMEVmT22Vm" type="{http://rr.x-road.eu/producer}RRExtDocumentDataT22Response"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRVK_ABIELUVOIMEVmT22ResponseType", propOrder = {
    "rrvkabieluvoimeVmT22Vm"
})
public class RRVKABIELUVOIMEVmT22ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRVK_ABIELUVOIMEVmT22Vm", required = true)
    protected RRExtDocumentDataT22Response rrvkabieluvoimeVmT22Vm;

    /**
     * Gets the value of the rrvkabieluvoimeVmT22Vm property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataT22Response }
     *     
     */
    public RRExtDocumentDataT22Response getRRVKABIELUVOIMEVmT22Vm() {
        return rrvkabieluvoimeVmT22Vm;
    }

    /**
     * Sets the value of the rrvkabieluvoimeVmT22Vm property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataT22Response }
     *     
     */
    public void setRRVKABIELUVOIMEVmT22Vm(RRExtDocumentDataT22Response value) {
        this.rrvkabieluvoimeVmT22Vm = value;
    }

}
