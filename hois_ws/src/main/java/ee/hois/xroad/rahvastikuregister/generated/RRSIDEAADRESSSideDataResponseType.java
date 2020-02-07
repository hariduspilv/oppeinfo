
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRSIDEAADRESSSideDataResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRSIDEAADRESSSideDataResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRSIDEAADRESSSideData" type="{http://rr.x-road.eu/producer}RRExtSideData1Response"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRSIDEAADRESSSideDataResponseType", propOrder = {
    "rrsideaadressSideData"
})
public class RRSIDEAADRESSSideDataResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRSIDEAADRESSSideData", required = true)
    protected RRExtSideData1Response rrsideaadressSideData;

    /**
     * Gets the value of the rrsideaadressSideData property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtSideData1Response }
     *     
     */
    public RRExtSideData1Response getRRSIDEAADRESSSideData() {
        return rrsideaadressSideData;
    }

    /**
     * Sets the value of the rrsideaadressSideData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtSideData1Response }
     *     
     */
    public void setRRSIDEAADRESSSideData(RRExtSideData1Response value) {
        this.rrsideaadressSideData = value;
    }

}
