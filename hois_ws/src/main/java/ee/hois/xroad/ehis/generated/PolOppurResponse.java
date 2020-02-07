
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
 *         &lt;element name="isik" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}polOppurIsikud"/&gt;
 *         &lt;element name="alush" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}polOppurOppetoodAlush"/&gt;
 *         &lt;element name="yldh" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}polOppurOppetoodYldh"/&gt;
 *         &lt;element name="korgk" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}polOppurOppetoodKorgk"/&gt;
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
    "isik",
    "alush",
    "yldh",
    "korgk"
})
@XmlRootElement(name = "polOppurResponse")
public class PolOppurResponse {

    @XmlElement(required = true, nillable = true)
    protected PolOppurIsikud isik;
    @XmlElement(required = true, nillable = true)
    protected PolOppurOppetoodAlush alush;
    @XmlElement(required = true, nillable = true)
    protected PolOppurOppetoodYldh yldh;
    @XmlElement(required = true, nillable = true)
    protected PolOppurOppetoodKorgk korgk;

    /**
     * Gets the value of the isik property.
     * 
     * @return
     *     possible object is
     *     {@link PolOppurIsikud }
     *     
     */
    public PolOppurIsikud getIsik() {
        return isik;
    }

    /**
     * Sets the value of the isik property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolOppurIsikud }
     *     
     */
    public void setIsik(PolOppurIsikud value) {
        this.isik = value;
    }

    /**
     * Gets the value of the alush property.
     * 
     * @return
     *     possible object is
     *     {@link PolOppurOppetoodAlush }
     *     
     */
    public PolOppurOppetoodAlush getAlush() {
        return alush;
    }

    /**
     * Sets the value of the alush property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolOppurOppetoodAlush }
     *     
     */
    public void setAlush(PolOppurOppetoodAlush value) {
        this.alush = value;
    }

    /**
     * Gets the value of the yldh property.
     * 
     * @return
     *     possible object is
     *     {@link PolOppurOppetoodYldh }
     *     
     */
    public PolOppurOppetoodYldh getYldh() {
        return yldh;
    }

    /**
     * Sets the value of the yldh property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolOppurOppetoodYldh }
     *     
     */
    public void setYldh(PolOppurOppetoodYldh value) {
        this.yldh = value;
    }

    /**
     * Gets the value of the korgk property.
     * 
     * @return
     *     possible object is
     *     {@link PolOppurOppetoodKorgk }
     *     
     */
    public PolOppurOppetoodKorgk getKorgk() {
        return korgk;
    }

    /**
     * Sets the value of the korgk property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolOppurOppetoodKorgk }
     *     
     */
    public void setKorgk(PolOppurOppetoodKorgk value) {
        this.korgk = value;
    }

}
