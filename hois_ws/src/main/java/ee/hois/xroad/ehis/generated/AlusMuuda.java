
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for alusMuuda complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alusMuuda"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="muudaOppurIsikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusIsikuandmedDetail" minOccurs="0"/&gt;
 *           &lt;element name="muudaOppurOppimine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusOppurOppimine" minOccurs="0"/&gt;
 *           &lt;element name="muudaOppurFiliaal" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusOppurFiliaal" minOccurs="0"/&gt;
 *           &lt;element name="muudaOppurLopeta" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusOppurLopeta" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alusMuuda", propOrder = {
    "muudaOppurIsikuandmed",
    "muudaOppurOppimine",
    "muudaOppurFiliaal",
    "muudaOppurLopeta"
})
public class AlusMuuda {

    protected AlusIsikuandmedDetail muudaOppurIsikuandmed;
    protected AlusOppurOppimine muudaOppurOppimine;
    protected AlusOppurFiliaal muudaOppurFiliaal;
    protected AlusOppurLopeta muudaOppurLopeta;

    /**
     * Gets the value of the muudaOppurIsikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link AlusIsikuandmedDetail }
     *     
     */
    public AlusIsikuandmedDetail getMuudaOppurIsikuandmed() {
        return muudaOppurIsikuandmed;
    }

    /**
     * Sets the value of the muudaOppurIsikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusIsikuandmedDetail }
     *     
     */
    public void setMuudaOppurIsikuandmed(AlusIsikuandmedDetail value) {
        this.muudaOppurIsikuandmed = value;
    }

    /**
     * Gets the value of the muudaOppurOppimine property.
     * 
     * @return
     *     possible object is
     *     {@link AlusOppurOppimine }
     *     
     */
    public AlusOppurOppimine getMuudaOppurOppimine() {
        return muudaOppurOppimine;
    }

    /**
     * Sets the value of the muudaOppurOppimine property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusOppurOppimine }
     *     
     */
    public void setMuudaOppurOppimine(AlusOppurOppimine value) {
        this.muudaOppurOppimine = value;
    }

    /**
     * Gets the value of the muudaOppurFiliaal property.
     * 
     * @return
     *     possible object is
     *     {@link AlusOppurFiliaal }
     *     
     */
    public AlusOppurFiliaal getMuudaOppurFiliaal() {
        return muudaOppurFiliaal;
    }

    /**
     * Sets the value of the muudaOppurFiliaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusOppurFiliaal }
     *     
     */
    public void setMuudaOppurFiliaal(AlusOppurFiliaal value) {
        this.muudaOppurFiliaal = value;
    }

    /**
     * Gets the value of the muudaOppurLopeta property.
     * 
     * @return
     *     possible object is
     *     {@link AlusOppurLopeta }
     *     
     */
    public AlusOppurLopeta getMuudaOppurLopeta() {
        return muudaOppurLopeta;
    }

    /**
     * Sets the value of the muudaOppurLopeta property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlusOppurLopeta }
     *     
     */
    public void setMuudaOppurLopeta(AlusOppurLopeta value) {
        this.muudaOppurLopeta = value;
    }

}
