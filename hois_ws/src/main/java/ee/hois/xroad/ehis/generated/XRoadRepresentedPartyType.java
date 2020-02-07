
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XRoadRepresentedPartyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XRoadRepresentedPartyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://x-road.eu/xsd/representation.xsd}partyClass" minOccurs="0"/&gt;
 *         &lt;element ref="{http://x-road.eu/xsd/representation.xsd}partyCode"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XRoadRepresentedPartyType", namespace = "http://x-road.eu/xsd/representation.xsd", propOrder = {
    "partyClass",
    "partyCode"
})
public class XRoadRepresentedPartyType {

    @XmlElement(namespace = "http://x-road.eu/xsd/representation.xsd")
    protected String partyClass;
    @XmlElement(namespace = "http://x-road.eu/xsd/representation.xsd", required = true)
    protected String partyCode;

    /**
     * Gets the value of the partyClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartyClass() {
        return partyClass;
    }

    /**
     * Sets the value of the partyClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartyClass(String value) {
        this.partyClass = value;
    }

    /**
     * Gets the value of the partyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartyCode() {
        return partyCode;
    }

    /**
     * Sets the value of the partyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartyCode(String value) {
        this.partyCode = value;
    }

}
