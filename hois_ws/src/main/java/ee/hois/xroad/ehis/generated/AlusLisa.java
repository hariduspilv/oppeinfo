
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for alusLisa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alusLisa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="lisaOppurOppimine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusOppurOppimine"/&gt;
 *         &lt;element name="lisaOppurIsikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusIsikuandmedDetail" minOccurs="0"/&gt;
 *         &lt;element name="lisaOppurFiliaal" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusOppurFiliaal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alusLisa", propOrder = {
    "lisaOppurOppimine",
    "lisaOppurIsikuandmed",
    "lisaOppurFiliaal"
})
public class AlusLisa {

    @XmlElement(required = true)
    protected AlusOppurOppimine lisaOppurOppimine;
    protected AlusIsikuandmedDetail lisaOppurIsikuandmed;
    protected AlusOppurFiliaal lisaOppurFiliaal;

    /**
     * Gets the value of the lisaOppurOppimine property.
     * 
     * @return
     *     possible object is
     *     {@link AlusOppurOppimine }
     *     
     */
    public AlusOppurOppimine getLisaOppurOppimine() {
        return lisaOppurOppimine;
    }

    /**
     * Sets the value of the lisaOppurOppimine property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusOppurOppimine }
     *     
     */
    public void setLisaOppurOppimine(AlusOppurOppimine value) {
        this.lisaOppurOppimine = value;
    }

    /**
     * Gets the value of the lisaOppurIsikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link AlusIsikuandmedDetail }
     *     
     */
    public AlusIsikuandmedDetail getLisaOppurIsikuandmed() {
        return lisaOppurIsikuandmed;
    }

    /**
     * Sets the value of the lisaOppurIsikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusIsikuandmedDetail }
     *     
     */
    public void setLisaOppurIsikuandmed(AlusIsikuandmedDetail value) {
        this.lisaOppurIsikuandmed = value;
    }

    /**
     * Gets the value of the lisaOppurFiliaal property.
     * 
     * @return
     *     possible object is
     *     {@link AlusOppurFiliaal }
     *     
     */
    public AlusOppurFiliaal getLisaOppurFiliaal() {
        return lisaOppurFiliaal;
    }

    /**
     * Sets the value of the lisaOppurFiliaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusOppurFiliaal }
     *     
     */
    public void setLisaOppurFiliaal(AlusOppurFiliaal value) {
        this.lisaOppurFiliaal = value;
    }

}
