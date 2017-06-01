
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
 *         &lt;element name="isikud" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tootukassaleKehtivadV2Isikud"/&gt;
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
    "isikud"
})
@XmlRootElement(name = "tootukassaleKehtivadV2Response")
public class TootukassaleKehtivadV2Response {

    @XmlElement(required = true)
    protected TootukassaleKehtivadV2Isikud isikud;

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link TootukassaleKehtivadV2Isikud }
     *     
     */
    public TootukassaleKehtivadV2Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link TootukassaleKehtivadV2Isikud }
     *     
     */
    public void setIsikud(TootukassaleKehtivadV2Isikud value) {
        this.isikud = value;
    }

}
