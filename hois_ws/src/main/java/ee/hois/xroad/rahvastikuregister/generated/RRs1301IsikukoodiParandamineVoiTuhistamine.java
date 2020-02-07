
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="request" type="{http://rr.x-road.eu/producer}RRs1301IsikukoodiParandamineVoiTuhistamineRequestType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "request"
})
@XmlRootElement(name = "RRs1301IsikukoodiParandamineVoiTuhistamine")
public class RRs1301IsikukoodiParandamineVoiTuhistamine {

    @XmlElement(required = true)
    protected RRs1301IsikukoodiParandamineVoiTuhistamineRequestType request;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RRs1301IsikukoodiParandamineVoiTuhistamineRequestType }
     *     
     */
    public RRs1301IsikukoodiParandamineVoiTuhistamineRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRs1301IsikukoodiParandamineVoiTuhistamineRequestType }
     *     
     */
    public void setRequest(RRs1301IsikukoodiParandamineVoiTuhistamineRequestType value) {
        this.request = value;
    }

}
