
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
 *         &lt;element name="oppurid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tervishoiuametileOppuridType"/&gt;
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
    "oppurid"
})
@XmlRootElement(name = "tervishoiuametileOppuridResponse")
public class TervishoiuametileOppuridResponse {

    @XmlElement(required = true)
    protected TervishoiuametileOppuridType oppurid;

    /**
     * Gets the value of the oppurid property.
     * 
     * @return
     *     possible object is
     *     {@link TervishoiuametileOppuridType }
     *     
     */
    public TervishoiuametileOppuridType getOppurid() {
        return oppurid;
    }

    /**
     * Sets the value of the oppurid property.
     * 
     * @param value
     *     allowed object is
     *     {@link TervishoiuametileOppuridType }
     *     
     */
    public void setOppurid(TervishoiuametileOppuridType value) {
        this.oppurid = value;
    }

}
