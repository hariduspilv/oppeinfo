
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTHOOLDUSOIGUSRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTHOOLDUSOIGUSRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Roll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRPORTHOOLDUSOIGUSRequestType", propOrder = {
    "isikukood",
    "roll",
    "pohjus"
})
public class RRPORTHOOLDUSOIGUSRequestType {

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Roll", required = true)
    protected String roll;
    protected String pohjus;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the roll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoll() {
        return roll;
    }

    /**
     * Sets the value of the roll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoll(String value) {
        this.roll = value;
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
