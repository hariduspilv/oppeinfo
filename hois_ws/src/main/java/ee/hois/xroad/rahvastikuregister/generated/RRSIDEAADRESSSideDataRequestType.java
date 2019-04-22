
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRSIDEAADRESSSideDataRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRSIDEAADRESSSideDataRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRSIDEAADRESSSideData" type="{http://rr.x-road.eu/producer}RRExtSideData1Request"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRSIDEAADRESSSideDataRequestType", propOrder = {
    "rrsideaadressSideData"
})
public class RRSIDEAADRESSSideDataRequestType {

    @XmlElement(name = "RRSIDEAADRESSSideData", required = true)
    protected RRExtSideData1Request rrsideaadressSideData;

    /**
     * Gets the value of the rrsideaadressSideData property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtSideData1Request }
     *     
     */
    public RRExtSideData1Request getRRSIDEAADRESSSideData() {
        return rrsideaadressSideData;
    }

    /**
     * Sets the value of the rrsideaadressSideData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtSideData1Request }
     *     
     */
    public void setRRSIDEAADRESSSideData(RRExtSideData1Request value) {
        this.rrsideaadressSideData = value;
    }

}
