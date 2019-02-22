
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotjaDokumentideLoetelu_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotjaDokumentideLoetelu_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="keha" type="{http://arireg.x-road.eu/producer/}ettevotja_dokumentide_loetelu_paring_v2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotjaDokumentideLoetelu_v1", propOrder = {
    "keha"
})
public class EttevotjaDokumentideLoeteluV1 {

    @XmlElement(required = true)
    protected EttevotjaDokumentideLoeteluParingV2 keha;

    /**
     * Gets the value of the keha property.
     * 
     * @return
     *     possible object is
     *     {@link EttevotjaDokumentideLoeteluParingV2 }
     *     
     */
    public EttevotjaDokumentideLoeteluParingV2 getKeha() {
        return keha;
    }

    /**
     * Sets the value of the keha property.
     * 
     * @param value
     *     allowed object is
     *     {@link EttevotjaDokumentideLoeteluParingV2 }
     *     
     */
    public void setKeha(EttevotjaDokumentideLoeteluParingV2 value) {
        this.keha = value;
    }

}
