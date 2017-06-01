
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eylOpetamineAmetikoht complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eylOpetamineAmetikoht"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ametikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eylOpetamineAmetikoht", propOrder = {
    "ametikoht"
})
public class EylOpetamineAmetikoht {

    @XmlElement(required = true, nillable = true)
    protected String ametikoht;

    /**
     * Gets the value of the ametikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetikoht() {
        return ametikoht;
    }

    /**
     * Sets the value of the ametikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetikoht(String value) {
        this.ametikoht = value;
    }

}
