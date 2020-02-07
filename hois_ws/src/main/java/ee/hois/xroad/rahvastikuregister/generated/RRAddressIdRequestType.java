
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRAddressIdRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRAddressIdRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ADS_OID" type="{http://rr.x-road.eu/producer}AdsOidCode"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRAddressIdRequestType", propOrder = {
    "adsoid"
})
public class RRAddressIdRequestType {

    @XmlElement(name = "ADS_OID", required = true)
    protected String adsoid;

    /**
     * Gets the value of the adsoid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADSOID() {
        return adsoid;
    }

    /**
     * Sets the value of the adsoid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADSOID(String value) {
        this.adsoid = value;
    }

}
