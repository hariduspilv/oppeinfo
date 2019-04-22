
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR417RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR417RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Seisuga" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="KOV" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR417RequestType", propOrder = {
    "seisuga",
    "kov"
})
public class RR417RequestType {

    @XmlElement(name = "Seisuga", required = true)
    protected String seisuga;
    @XmlElement(name = "KOV", required = true)
    protected String kov;

    /**
     * Gets the value of the seisuga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeisuga() {
        return seisuga;
    }

    /**
     * Sets the value of the seisuga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeisuga(String value) {
        this.seisuga = value;
    }

    /**
     * Gets the value of the kov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOV() {
        return kov;
    }

    /**
     * Sets the value of the kov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOV(String value) {
        this.kov = value;
    }

}
