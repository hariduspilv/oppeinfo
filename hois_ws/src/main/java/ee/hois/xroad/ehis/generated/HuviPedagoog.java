
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for huviPedagoog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="huviPedagoog"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="klKorgeimHaridustase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klOppeKeeled" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}huviKlOppekeeled" minOccurs="0"/&gt;
 *         &lt;element name="klAmetikohad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}huviKlAmetikohad" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaKoodid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}huviOppekavaKoodid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "huviPedagoog", propOrder = {
    "isikukood",
    "klKorgeimHaridustase",
    "klOppeKeeled",
    "klAmetikohad",
    "oppekavaKoodid"
})
public class HuviPedagoog {

    @XmlElement(required = true)
    protected String isikukood;
    protected String klKorgeimHaridustase;
    protected HuviKlOppekeeled klOppeKeeled;
    protected HuviKlAmetikohad klAmetikohad;
    @XmlElement(required = true)
    protected HuviOppekavaKoodid oppekavaKoodid;

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
     * Gets the value of the klKorgeimHaridustase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKorgeimHaridustase() {
        return klKorgeimHaridustase;
    }

    /**
     * Sets the value of the klKorgeimHaridustase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKorgeimHaridustase(String value) {
        this.klKorgeimHaridustase = value;
    }

    /**
     * Gets the value of the klOppeKeeled property.
     * 
     * @return
     *     possible object is
     *     {@link HuviKlOppekeeled }
     *     
     */
    public HuviKlOppekeeled getKlOppeKeeled() {
        return klOppeKeeled;
    }

    /**
     * Sets the value of the klOppeKeeled property.
     * 
     * @param value
     *     allowed object is
     *     {@link HuviKlOppekeeled }
     *     
     */
    public void setKlOppeKeeled(HuviKlOppekeeled value) {
        this.klOppeKeeled = value;
    }

    /**
     * Gets the value of the klAmetikohad property.
     * 
     * @return
     *     possible object is
     *     {@link HuviKlAmetikohad }
     *     
     */
    public HuviKlAmetikohad getKlAmetikohad() {
        return klAmetikohad;
    }

    /**
     * Sets the value of the klAmetikohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link HuviKlAmetikohad }
     *     
     */
    public void setKlAmetikohad(HuviKlAmetikohad value) {
        this.klAmetikohad = value;
    }

    /**
     * Gets the value of the oppekavaKoodid property.
     * 
     * @return
     *     possible object is
     *     {@link HuviOppekavaKoodid }
     *     
     */
    public HuviOppekavaKoodid getOppekavaKoodid() {
        return oppekavaKoodid;
    }

    /**
     * Sets the value of the oppekavaKoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link HuviOppekavaKoodid }
     *     
     */
    public void setOppekavaKoodid(HuviOppekavaKoodid value) {
        this.oppekavaKoodid = value;
    }

}
