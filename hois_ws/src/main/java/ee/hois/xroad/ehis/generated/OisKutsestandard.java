
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisKutsestandard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisKutsestandard"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="standardReaId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="kutseSpetsialiseerumised" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oiskutseSpetsialiseerumine" minOccurs="0"/&gt;
 *         &lt;element name="osakutsed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisOsakutse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oisKutsestandard", propOrder = {
    "standardReaId",
    "kutseSpetsialiseerumised",
    "osakutsed"
})
public class OisKutsestandard {

    @XmlElement(required = true)
    protected BigInteger standardReaId;
    protected OiskutseSpetsialiseerumine kutseSpetsialiseerumised;
    protected OisOsakutse osakutsed;

    /**
     * Gets the value of the standardReaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStandardReaId() {
        return standardReaId;
    }

    /**
     * Sets the value of the standardReaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStandardReaId(BigInteger value) {
        this.standardReaId = value;
    }

    /**
     * Gets the value of the kutseSpetsialiseerumised property.
     * 
     * @return
     *     possible object is
     *     {@link OiskutseSpetsialiseerumine }
     *     
     */
    public OiskutseSpetsialiseerumine getKutseSpetsialiseerumised() {
        return kutseSpetsialiseerumised;
    }

    /**
     * Sets the value of the kutseSpetsialiseerumised property.
     * 
     * @param value
     *     allowed object is
     *     {@link OiskutseSpetsialiseerumine }
     *     
     */
    public void setKutseSpetsialiseerumised(OiskutseSpetsialiseerumine value) {
        this.kutseSpetsialiseerumised = value;
    }

    /**
     * Gets the value of the osakutsed property.
     * 
     * @return
     *     possible object is
     *     {@link OisOsakutse }
     *     
     */
    public OisOsakutse getOsakutsed() {
        return osakutsed;
    }

    /**
     * Sets the value of the osakutsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisOsakutse }
     *     
     */
    public void setOsakutsed(OisOsakutse value) {
        this.osakutsed = value;
    }

}
