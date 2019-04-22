
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTDETAILRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTDETAILRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pohjus" type="{http://rr.x-road.eu/producer}pohjuse_valik4" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRPORTDETAILRequestType", propOrder = {
    "cIsikukood",
    "pohjus"
})
public class RRPORTDETAILRequestType {

    @XmlElement(required = true)
    protected String cIsikukood;
    protected String pohjus;

    /**
     * Gets the value of the cIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIsikukood() {
        return cIsikukood;
    }

    /**
     * Sets the value of the cIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIsikukood(String value) {
        this.cIsikukood = value;
    }

    /**
     * Gets the value of the pohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohjus() {
        return pohjus;
    }

    /**
     * Sets the value of the pohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohjus(String value) {
        this.pohjus = value;
    }

}
