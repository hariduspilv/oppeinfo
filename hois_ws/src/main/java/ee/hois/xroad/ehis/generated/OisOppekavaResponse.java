
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
 *         &lt;element name="infoteated" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisInfoteated"/&gt;
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
    "infoteated"
})
@XmlRootElement(name = "oisOppekavaResponse")
public class OisOppekavaResponse {

    @XmlElement(required = true)
    protected OisInfoteated infoteated;

    /**
     * Gets the value of the infoteated property.
     * 
     * @return
     *     possible object is
     *     {@link OisInfoteated }
     *     
     */
    public OisInfoteated getInfoteated() {
        return infoteated;
    }

    /**
     * Sets the value of the infoteated property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisInfoteated }
     *     
     */
    public void setInfoteated(OisInfoteated value) {
        this.infoteated = value;
    }

}