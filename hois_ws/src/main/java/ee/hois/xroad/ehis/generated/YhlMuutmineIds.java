
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlMuutmineIds complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlMuutmineIds"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppurKoolId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlMuutmineIds", propOrder = {
    "oppurKoolId"
})
public class YhlMuutmineIds {

    @XmlElement(required = true)
    protected String oppurKoolId;

    /**
     * Gets the value of the oppurKoolId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppurKoolId() {
        return oppurKoolId;
    }

    /**
     * Sets the value of the oppurKoolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppurKoolId(String value) {
        this.oppurKoolId = value;
    }

}
