
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
 *         &lt;element name="otsused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}otsused"/&gt;
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
    "otsused"
})
@XmlRootElement(name = "innoveOtsused")
public class InnoveOtsused {

    @XmlElement(required = true)
    protected Otsused otsused;

    /**
     * Gets the value of the otsused property.
     * 
     * @return
     *     possible object is
     *     {@link Otsused }
     *     
     */
    public Otsused getOtsused() {
        return otsused;
    }

    /**
     * Sets the value of the otsused property.
     * 
     * @param value
     *     allowed object is
     *     {@link Otsused }
     *     
     */
    public void setOtsused(Otsused value) {
        this.otsused = value;
    }

}
