
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
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
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="innove" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}iaInnove" minOccurs="0"/&gt;
 *         &lt;element name="ajalugu" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}iaKirjed" minOccurs="0"/&gt;
 *         &lt;element name="eelnevOppimine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}iaOppimised" minOccurs="0"/&gt;
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
    "isikukood",
    "koolId",
    "innove",
    "ajalugu",
    "eelnevOppimine"
})
@XmlRootElement(name = "innoveAjaluguResponse")
public class InnoveAjaluguResponse {

    @XmlElement(required = true)
    protected String isikukood;
    @XmlElement(required = true)
    protected BigInteger koolId;
    protected IaInnove innove;
    protected IaKirjed ajalugu;
    protected IaOppimised eelnevOppimine;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the koolId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKoolId() {
        return koolId;
    }

    /**
     * Sets the value of the koolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKoolId(BigInteger value) {
        this.koolId = value;
    }

    /**
     * Gets the value of the innove property.
     * 
     * @return
     *     possible object is
     *     {@link IaInnove }
     *     
     */
    public IaInnove getInnove() {
        return innove;
    }

    /**
     * Sets the value of the innove property.
     * 
     * @param value
     *     allowed object is
     *     {@link IaInnove }
     *     
     */
    public void setInnove(IaInnove value) {
        this.innove = value;
    }

    /**
     * Gets the value of the ajalugu property.
     * 
     * @return
     *     possible object is
     *     {@link IaKirjed }
     *     
     */
    public IaKirjed getAjalugu() {
        return ajalugu;
    }

    /**
     * Sets the value of the ajalugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link IaKirjed }
     *     
     */
    public void setAjalugu(IaKirjed value) {
        this.ajalugu = value;
    }

    /**
     * Gets the value of the eelnevOppimine property.
     * 
     * @return
     *     possible object is
     *     {@link IaOppimised }
     *     
     */
    public IaOppimised getEelnevOppimine() {
        return eelnevOppimine;
    }

    /**
     * Sets the value of the eelnevOppimine property.
     * 
     * @param value
     *     allowed object is
     *     {@link IaOppimised }
     *     
     */
    public void setEelnevOppimine(IaOppimised value) {
        this.eelnevOppimine = value;
    }

}
