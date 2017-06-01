
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for alusIsikuandmedDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alusIsikuandmedDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klEmakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="onHoolealune" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="adsAdrId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alusIsikuandmedDetail", propOrder = {
    "klEmakeel",
    "onHoolealune",
    "adsAdrId"
})
public class AlusIsikuandmedDetail {

    @XmlElement(required = true)
    protected String klEmakeel;
    protected String onHoolealune;
    protected BigInteger adsAdrId;

    /**
     * Gets the value of the klEmakeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEmakeel() {
        return klEmakeel;
    }

    /**
     * Sets the value of the klEmakeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEmakeel(String value) {
        this.klEmakeel = value;
    }

    /**
     * Gets the value of the onHoolealune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnHoolealune() {
        return onHoolealune;
    }

    /**
     * Sets the value of the onHoolealune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnHoolealune(String value) {
        this.onHoolealune = value;
    }

    /**
     * Gets the value of the adsAdrId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAdsAdrId() {
        return adsAdrId;
    }

    /**
     * Sets the value of the adsAdrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAdsAdrId(BigInteger value) {
        this.adsAdrId = value;
    }

}
