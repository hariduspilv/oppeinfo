
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRVK_ABIELUVOIMEVmT22RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRVK_ABIELUVOIMEVmT22RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRVK_ABIELUVOIMEVmT22" type="{http://rr.x-road.eu/producer}RRExtDocumentDataT22Request"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRVK_ABIELUVOIMEVmT22RequestType", propOrder = {
    "rrvkabieluvoimeVmT22"
})
public class RRVKABIELUVOIMEVmT22RequestType {

    @XmlElement(name = "RRVK_ABIELUVOIMEVmT22", required = true)
    protected RRExtDocumentDataT22Request rrvkabieluvoimeVmT22;

    /**
     * Gets the value of the rrvkabieluvoimeVmT22 property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataT22Request }
     *     
     */
    public RRExtDocumentDataT22Request getRRVKABIELUVOIMEVmT22() {
        return rrvkabieluvoimeVmT22;
    }

    /**
     * Sets the value of the rrvkabieluvoimeVmT22 property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataT22Request }
     *     
     */
    public void setRRVKABIELUVOIMEVmT22(RRExtDocumentDataT22Request value) {
        this.rrvkabieluvoimeVmT22 = value;
    }

}
