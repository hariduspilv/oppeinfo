
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR76AndmIKRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR76AndmIKRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="picIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR76AndmIKRequestType", propOrder = {
    "picIsikukood"
})
public class RR76AndmIKRequestType {

    @XmlElement(required = true)
    protected String picIsikukood;

    /**
     * Gets the value of the picIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicIsikukood() {
        return picIsikukood;
    }

    /**
     * Sets the value of the picIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicIsikukood(String value) {
        this.picIsikukood = value;
    }

}
