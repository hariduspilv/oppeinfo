
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for khlDuplikaadiMuutmine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlDuplikaadiMuutmine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klIsikukoodRiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekava" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="duplikaat" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlDuplikaadiValjastamine"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlDuplikaadiMuutmine", propOrder = {
    "isikukood",
    "klIsikukoodRiik",
    "oppekava",
    "duplikaat"
})
public class KhlDuplikaadiMuutmine {

    @XmlElement(required = true)
    protected String isikukood;
    @XmlElement(defaultValue = "EE")
    protected String klIsikukoodRiik;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger oppekava;
    @XmlElement(required = true)
    protected KhlDuplikaadiValjastamine duplikaat;

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
     * Gets the value of the klIsikukoodRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlIsikukoodRiik() {
        return klIsikukoodRiik;
    }

    /**
     * Sets the value of the klIsikukoodRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlIsikukoodRiik(String value) {
        this.klIsikukoodRiik = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekava() {
        return oppekava;
    }

    /**
     * Sets the value of the oppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekava(BigInteger value) {
        this.oppekava = value;
    }

    /**
     * Gets the value of the duplikaat property.
     * 
     * @return
     *     possible object is
     *     {@link KhlDuplikaadiValjastamine }
     *     
     */
    public KhlDuplikaadiValjastamine getDuplikaat() {
        return duplikaat;
    }

    /**
     * Sets the value of the duplikaat property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlDuplikaadiValjastamine }
     *     
     */
    public void setDuplikaat(KhlDuplikaadiValjastamine value) {
        this.duplikaat = value;
    }

}
