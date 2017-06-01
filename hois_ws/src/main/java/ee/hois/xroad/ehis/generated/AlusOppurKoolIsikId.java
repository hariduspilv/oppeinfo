
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for alusOppurKoolIsikId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alusOppurKoolIsikId"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="koolIsikId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alusOppurKoolIsikId", propOrder = {
    "koolIsikId"
})
public class AlusOppurKoolIsikId {

    @XmlElement(required = true)
    protected String koolIsikId;

    /**
     * Gets the value of the koolIsikId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoolIsikId() {
        return koolIsikId;
    }

    /**
     * Sets the value of the koolIsikId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoolIsikId(String value) {
        this.koolIsikId = value;
    }

}
