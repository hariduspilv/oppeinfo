
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
 *         &lt;element name="oasEhisKoodid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oasEhisKoodid"/&gt;
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
    "oasEhisKoodid"
})
@XmlRootElement(name = "tervishoiuametileOppurid")
public class TervishoiuametileOppurid {

    @XmlElement(required = true)
    protected OasEhisKoodid oasEhisKoodid;

    /**
     * Gets the value of the oasEhisKoodid property.
     * 
     * @return
     *     possible object is
     *     {@link OasEhisKoodid }
     *     
     */
    public OasEhisKoodid getOasEhisKoodid() {
        return oasEhisKoodid;
    }

    /**
     * Sets the value of the oasEhisKoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link OasEhisKoodid }
     *     
     */
    public void setOasEhisKoodid(OasEhisKoodid value) {
        this.oasEhisKoodid = value;
    }

}
