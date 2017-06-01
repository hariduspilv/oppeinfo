
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
 *         &lt;element name="oppimised" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eisOppimised"/&gt;
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
    "oppimised"
})
@XmlRootElement(name = "oppuridResponse")
public class OppuridResponse {

    @XmlElement(required = true)
    protected EisOppimised oppimised;

    /**
     * Gets the value of the oppimised property.
     * 
     * @return
     *     possible object is
     *     {@link EisOppimised }
     *     
     */
    public EisOppimised getOppimised() {
        return oppimised;
    }

    /**
     * Sets the value of the oppimised property.
     * 
     * @param value
     *     allowed object is
     *     {@link EisOppimised }
     *     
     */
    public void setOppimised(EisOppimised value) {
        this.oppimised = value;
    }

}
