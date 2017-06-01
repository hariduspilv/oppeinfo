
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
 *         &lt;element name="oppeasutused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tervishoiuametileOppeasutusedList"/&gt;
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
    "oppeasutused"
})
@XmlRootElement(name = "tervishoiuametileOppeasutusedResponse")
public class TervishoiuametileOppeasutusedResponse {

    @XmlElement(required = true)
    protected TervishoiuametileOppeasutusedList oppeasutused;

    /**
     * Gets the value of the oppeasutused property.
     * 
     * @return
     *     possible object is
     *     {@link TervishoiuametileOppeasutusedList }
     *     
     */
    public TervishoiuametileOppeasutusedList getOppeasutused() {
        return oppeasutused;
    }

    /**
     * Sets the value of the oppeasutused property.
     * 
     * @param value
     *     allowed object is
     *     {@link TervishoiuametileOppeasutusedList }
     *     
     */
    public void setOppeasutused(TervishoiuametileOppeasutusedList value) {
        this.oppeasutused = value;
    }

}
