
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
 *         &lt;element name="oppeasutused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}saisEhisOppeasutused"/&gt;
 *         &lt;element name="teade" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "oppeasutused",
    "teade"
})
@XmlRootElement(name = "saisEhisOppeasutusResponse")
public class SaisEhisOppeasutusResponse {

    @XmlElement(required = true, nillable = true)
    protected SaisEhisOppeasutused oppeasutused;
    @XmlElement(required = true, nillable = true)
    protected String teade;

    /**
     * Gets the value of the oppeasutused property.
     * 
     * @return
     *     possible object is
     *     {@link SaisEhisOppeasutused }
     *     
     */
    public SaisEhisOppeasutused getOppeasutused() {
        return oppeasutused;
    }

    /**
     * Sets the value of the oppeasutused property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaisEhisOppeasutused }
     *     
     */
    public void setOppeasutused(SaisEhisOppeasutused value) {
        this.oppeasutused = value;
    }

    /**
     * Gets the value of the teade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeade() {
        return teade;
    }

    /**
     * Sets the value of the teade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeade(String value) {
        this.teade = value;
    }

}
