
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ametikohad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eisAmetikohadV2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ametikohad"
})
@XmlRootElement(name = "pedagoogAmetikohtV2Response")
public class PedagoogAmetikohtV2Response {

    @XmlElement(required = true)
    protected EisAmetikohadV2 ametikohad;

    /**
     * Gets the value of the ametikohad property.
     * 
     * @return
     *     possible object is
     *     {@link EisAmetikohadV2 }
     *     
     */
    public EisAmetikohadV2 getAmetikohad() {
        return ametikohad;
    }

    /**
     * Sets the value of the ametikohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link EisAmetikohadV2 }
     *     
     */
    public void setAmetikohad(EisAmetikohadV2 value) {
        this.ametikohad = value;
    }

}
