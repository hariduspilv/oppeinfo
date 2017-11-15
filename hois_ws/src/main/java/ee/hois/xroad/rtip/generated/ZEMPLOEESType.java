
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Z_EMPLOEESType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Z_EMPLOEESType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="keha" type="{http://rtk-v6.x-road.eu}Z_EMPLOEESRequestType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Z_EMPLOEESType", namespace = "http://rtk-v6.x-road.eu", propOrder = {
    "keha"
})
public class ZEMPLOEESType {

    @XmlElement(required = true)
    protected ZEMPLOEESRequestType keha;

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link ZEMPLOEESRequestType }
     *     
     */
    public ZEMPLOEESRequestType getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZEMPLOEESRequestType }
     *     
     */
    public void setKeha(ZEMPLOEESRequestType value) {
        this.keha = value;
    }

}
