
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringarikeelud_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringarikeelud_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="arikeelud" type="{http://arireg.x-road.eu/producer/}paringarikeelud_keelud"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringarikeelud_vastus", propOrder = {
    "arikeelud"
})
public class ParingarikeeludVastus {

    @XmlElement(required = true)
    protected ParingarikeeludKeelud arikeelud;

    /**
     * Gets the value of the arikeelud property.
     * 
     * @return
     *     possible object is
     *     {@link ParingarikeeludKeelud }
     *     
     */
    public ParingarikeeludKeelud getArikeelud() {
        return arikeelud;
    }

    /**
     * Sets the value of the arikeelud property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingarikeeludKeelud }
     *     
     */
    public void setArikeelud(ParingarikeeludKeelud value) {
        this.arikeelud = value;
    }

}
