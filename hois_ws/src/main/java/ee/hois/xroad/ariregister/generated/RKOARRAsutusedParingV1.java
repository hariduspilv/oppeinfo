
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RKOARRAsutusedParing_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RKOARRAsutusedParing_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kuva_kehtetud" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RKOARRAsutusedParing_v1", propOrder = {
    "kuvaKehtetud"
})
public class RKOARRAsutusedParingV1 {

    @XmlElement(name = "kuva_kehtetud")
    protected Boolean kuvaKehtetud;

    /**
     * Gets the value of the kuvaKehtetud property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKuvaKehtetud() {
        return kuvaKehtetud;
    }

    /**
     * Sets the value of the kuvaKehtetud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKuvaKehtetud(Boolean value) {
        this.kuvaKehtetud = value;
    }

}
